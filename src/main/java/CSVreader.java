import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import webScraping.TvSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVreader {


    public List<TvSeries> read(Path path){
        List<TvSeries> tvSeriesDTOList  = new ArrayList<>();
        try{

            // Read CSV file for each row, insantiate and collect in tvSeriesDTOList
            BufferedReader reader = Files.newBufferedReader(path);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
            TvSeries tvSeries ;
            for(CSVRecord record : records){
                tvSeriesDTOList.add(checkParameters(record));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tvSeriesDTOList;
    }
    private TvSeries checkParameters(CSVRecord record){
        TvSeries tvSeries = new TvSeries();
        if(record.get("id") !=null || !record.get("id").isEmpty()){
            String id = record.get("id");
        }
        if(record.get("starList")!=null || !record.get("starList").isEmpty()|| !record.get("starList").equals("null")){
            tvSeries.setCastList(record.get("starList"));
        }
        if(record.get("imDbRatingVotes")!=null || !record.get("imDbRatingVotes").equals("null")){
            tvSeries.setImDbRatingVotes(record.get("imDbRatingVotes"));
        }
        if(record.get("genres") !=null || !record.get("genres").isEmpty() || !record.get("genres").equals("null")){
            tvSeries.setGenre(record.get("genres"));
        }
        if(record.get("runtimeStr")!=null || !record.get("runtimeStr").isEmpty() || !record.get("runtimeStr").equals("null")){
            tvSeries.setRunTime(record.get("runtimeStr"));
        }
        if(record.get("imDbRating")!=null || !record.get("imDbRating").isEmpty() || !record.get("imDbRating").equals("null")){
            tvSeries.setImDbRating(record.get("imDbRating"));
        }
        if(record.get("title")!=null || !record.get("title").isEmpty() || !record.get("title").equals("null")){
            tvSeries.setTitle(record.get("title"));
        }
        if(record.get("stars")!=null || !record.get("stars").isEmpty() || !record.get("stars").equals("null")){
            tvSeries.setStars(record.get("stars"));
        }
        if(record.get("description")!=null || !record.get("description").isEmpty() || !record.get("description").equals("null")){
            tvSeries.setReleaseDateAndEndDate(record.get("description"));
        }
        return tvSeries;

    }
}
