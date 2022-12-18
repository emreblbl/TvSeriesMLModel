import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;

public class CSVwriter {
    public void writer(JSONArray context,String fileName) throws IOException {
        File file = new File(fileName);

        // Step 5: Produce a comma delimited text from
        // the JSONArray of JSONObjects
        // and write the string to the newly created CSV
        // file

        String csvString = CDL.toString(context);
        FileUtils.writeStringToFile(file, csvString);
    }
}
