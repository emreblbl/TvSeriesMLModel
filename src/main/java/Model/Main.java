package Model;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String trainFileName="/Users/bulbul/Data-Mining/src/main/resources/furkan_data_set_without_index/traindata_with_episodes_normalized-1.csv";
        String testFileName="/Users/bulbul/Data-Mining/src/main/resources/furkan_data_set_without_index/testdata_normalized.csv";
        Model model=new Model();

        List<DataSetIterator> allData=model.readDataFromCsv(trainFileName, testFileName);
        DataSetIterator iterator_train=allData.get(0);
        DataSetIterator iterator_test=allData.get(1);

        MultiLayerNetwork multiLayerNetwork=model.createModel(iterator_train);

        model.evalutionModel(multiLayerNetwork,iterator_test);

    }


}
