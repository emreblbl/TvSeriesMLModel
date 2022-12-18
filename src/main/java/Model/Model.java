package Model;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {

    public Model() {
    }

    public List<DataSetIterator> readDataFromCsv(String trainFileName,String testFileName){
        List<DataSetIterator> allData=new ArrayList<>();
        try {
            RecordReader trainReader = new CSVRecordReader(0, ',');
            trainReader.initialize(new FileSplit(new File(trainFileName)));
            int labelIndex = 4; // class -1
            int numClasses = 5;
            int batchSizeTrain = 100; //batch size for each epoch
            int batchSizeTest = 20;

            DataSetIterator iterator_train = new RecordReaderDataSetIterator(trainReader, batchSizeTrain, labelIndex, numClasses);

            RecordReader testReader = new CSVRecordReader(0, ',');
            testReader.initialize(new FileSplit(new File(testFileName)));

            DataSetIterator iterator_test = new RecordReaderDataSetIterator(testReader, batchSizeTest, labelIndex, numClasses);
            allData.add(0,iterator_train);
            allData.add(1,iterator_test);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allData;
    }


    public MultiLayerNetwork createModel(DataSetIterator iterator_train){
        final int numInputs = 26;  // With cluster data : 5 , normally : 4
        int outputNum = 5; // number of classification
        long seed = 123; //shuffling value for dataset
        double learningRate = 0.001;
        int epoch = 50; // how many round model run

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .activation(Activation.RELU) //  rectified linear unit , activation function
                .weightInit(WeightInit.UNIFORM)
                .updater(new Adam(learningRate))
                .list()
                .layer(new DenseLayer.Builder()
                        .nIn(numInputs)
                        .nOut(1024)
                        .build())
                .layer(new DenseLayer.Builder()
                        .nIn(1024)
                        .nOut(512)
                        .build())
                .layer(new DenseLayer.Builder()
                        .nIn(512)
                        .nOut(256)
                        .build())
                .layer(new DenseLayer.Builder()
                        .nIn(512)
                        .nOut(256)
                        .dropOut(0.7)
                        .build())
                .layer(new DenseLayer.Builder()
                        .nIn(256)
                        .nOut(64)
                        .dropOut(0.5)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR) // calculate the error between the actual outputs and the desired outputs.
                        .nIn(64)
                        .activation(Activation.SOFTMAX)
                        .nOut(outputNum)
                        .build())

                .build();



        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(50));

        for (int i = 0; i < epoch; i++) {
            while (iterator_train.hasNext()) {
                DataSet dataSets = iterator_train.next();
                dataSets.shuffle();
                model.fit(dataSets);
            }
            iterator_train.reset();
        }
        return model;
    }

    public void evalutionModel(MultiLayerNetwork model ,DataSetIterator iterator_test){
        Evaluation eval = new Evaluation(5); // create an evaluation object with 5 possible classes
        int counter = 1;
        while (iterator_test.hasNext()) {
            DataSet t = iterator_test.next();
            if (counter < 14) {
                for (int i = 0; i < 20; i++) {
                    DataSet a = t.get(i);
                    INDArray features = a.getFeatures();
                    INDArray labels = a.getLabels();
                    INDArray predicted = model.output(features, false);//get the networks prediction
                    eval.eval(labels, predicted);//check the prediction against the true class

                }
            } else {
                for (int i = 0; i < 15; i++) {
                    DataSet a = t.get(i);
                    INDArray features = a.getFeatures();
                    INDArray labels = a.getLabels();
                    INDArray predicted = model.output(features, false);
                    eval.eval(labels, predicted);
                }
            }
            counter += 1;
        }
        System.out.println(eval.stats());
    }
}
