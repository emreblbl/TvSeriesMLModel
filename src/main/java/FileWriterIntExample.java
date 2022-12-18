import org.json.JSONArray;

import javax.json.Json;
import javax.json.JsonWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;

public class FileWriterIntExample {
    public void fileWriter(JSONArray context) throws Exception {
        JsonWriter writer = null;
        writer =  Json.createWriter(new FileOutputStream("file.csv"));
        writer.close();

    }
}
