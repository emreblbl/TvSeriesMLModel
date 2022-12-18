
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static utils.Constant.API_KEY;


public class App {

    private static final CSVwriter csvWriter = new CSVwriter();
    private static final CSVreader csvReader = new CSVreader();
    public static void main(String[] args) throws Exception {
        CSVreader csVreader = new CSVreader();
//        csVreader.read();
//        List<TvSeries> tvSeriesList =csvReader.read(FileSystems.getDefault().getPath("/Users/bulbul/Data-Mining/file.csv"));
//        System.out.println(tvSeriesList.get(0));
//        JSONArray tv_series_array = new JSONArray();
//        JSONArray context = new JSONArray();
//        try{
//        for(int i=1;i<2200;i=i+200){
//            context.putAll(createAdvanceRequest(i));
//        }
//        csvWriter.writer(context,"file.csv");
//        for (int a= 0; a<context.length();a++){
//            String id = context.getJSONObject(a).get("id").toString();
//            JSONObject tv_series = createRequestByTitle(id);
//            tv_series_array.put(tv_series);
//            }
//        csvWriter.writer(tv_series_array,"output.csv");
//        java.lang.System.exit(0);
//    }catch (IOException e){
//            e.printStackTrace();
//        }
//        FetchingData fetchingData = new FetchingData();
//        fetchingData.fetchingData("tt10278918");

    }
    public static JSONArray createAdvanceRequest(int start) throws IOException {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60,TimeUnit.SECONDS);
        builder.writeTimeout(60,TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        Request generalTvSeriesInfos = new Request.Builder()
                .url("https://imdb-api.com/en/API/advancedSearch/"+API_KEY+"/?title_type=tv_series&release_date=1990-01-01,&countries=tr&sort=year,&count=200&start="+start)
                .method("GET", null)
                .build();
        Response response = client.newCall(generalTvSeriesInfos).execute();
        String result =response.body().string();
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = new JSONArray(jsonObject.get("results").toString());
        return jsonArray;
    }
    public static JSONObject createRequestByTitle(String id) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request generalTvSeriesInfos = new Request.Builder()
                .url("https://imdb-api.com/en/API/Title/"+API_KEY+"/"+id+"/Ratings,Wikipedia,")
                .method("GET", null)
                .build();
        Response response = client.newCall(generalTvSeriesInfos).execute();
        String result =response.body().string();
        JSONObject jsonObject = new JSONObject(result);
        return jsonObject;

    }
}