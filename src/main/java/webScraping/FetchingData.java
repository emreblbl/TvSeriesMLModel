package webScraping;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchingData {
    //Define the search term
    String searchQuery = "https://www.imdb.com/title/tt0068646/?ref_=adv_li_tt";
    private final WebClient client = new WebClient();
    private List<TvSeries> tvSeriesList;
    public FetchingData(){

        // set up default configuration
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setDownloadImages(false);

        // initialize dataObject
        tvSeriesList = new ArrayList<>();
    }


    public void fetchingData(String id) throws IOException {
        //initilazing tvSeries object
        TvSeries tvSeries = new TvSeries();
        tvSeries.setId(id);

        // Set up the URL with the search term and the send the request
        String imdbWebsiteUrl = "https://www.imdb.com/title/"+id+"/?ref_=adv_li_tt";
        String seasonsUrl = "https://www.imdb.com/title/"+id+"?ref_=tt_ov_epl";
        HtmlPage imdbPage = client.getPage(imdbWebsiteUrl);

        HtmlElement ratingElement = (HtmlElement) imdbPage.getFirstByXPath("//span[@class='sc-7ab21ed2-1 jGRxWM']");
        if(ratingElement !=null){
            tvSeries.setStarring(ratingElement.getTextContent());

        }
        List<HtmlElement> castingsElement =  imdbPage.getByXPath("//a[@class='sc-bfec09a1-1 gfeYgX'][@data-testid='title-cast-item__actor']");
        List<String> castList = new ArrayList<>();
        if(!castList.isEmpty()){
            for (HtmlElement e : castingsElement) {
                castList.add(e.getTextContent());
            }
            //TODO add castList to tvSeries object
            //tvSeries.setCastList(castList);
        }

        HtmlElement title = imdbPage.getFirstByXPath("//h1[@data-testid='hero-title-block__title']");
        if(title !=null){
            tvSeries.setTitle(title.getTextContent());
        }

        HtmlElement episode = imdbPage.getFirstByXPath("//a[@class='ipc-title-link-wrapper']//h3[@class='ipc-title__text']//span[@class='ipc-title__subtext']");
        tvSeries.setEpisodes(episode.getTextContent());
        HtmlElement yearOfProduction = imdbPage.getFirstByXPath("//span[@class='sc-8c396aa2-2 itZqyK']");
        String tempReleaseDateAndYearOfProduction = yearOfProduction.getTextContent().substring(0,yearOfProduction.getTextContent().length()-2);
        tvSeries.setYearOfProduction(tempReleaseDateAndYearOfProduction);
        tvSeries.setReleaseDate(tempReleaseDateAndYearOfProduction);
        HtmlElement seasonElement = imdbPage.getFirstByXPath("//a[@class='ipc-button ipc-button--single-padding ipc-button--center-align-content ipc-button--default-height ipc-button--core-base ipc-button--theme-base ipc-button--on-accent2 ipc-text-button'][2]");
        tvSeries.setSeason(seasonElement.getTextContent());
        HtmlElement createdByElement = imdbPage.getFirstByXPath("//ul[@class='ipc-inline-list ipc-inline-list--show-dividers ipc-inline-list--inline ipc-metadata-list-item__list-content baseAlt']" +
                "//a[@class='ipc-metadata-list-item__list-content-item ipc-metadata-list-item__list-content-item--link']");
        tvSeries.setCreatedBy(createdByElement.getTextContent());
        tvSeries.setScriptWriters(createdByElement.getTextContent());
        HtmlElement publishOrganizationElement = imdbPage.getFirstByXPath("//a[@class='ipc-metadata-list-item__list-content-item ipc-metadata-list-item__list-content-item--link'][@aria-disabled='false']");
        tvSeries.setPublishOrganization(publishOrganizationElement.getTextContent());
        HtmlElement runTimeElement = imdbPage.getFirstByXPath("//li[@data-testid='title-techspec_runtime']");
        if(runTimeElement !=null){
            String[] tempRuntimeElement = runTimeElement.getTextContent().split("e",2);
            tvSeries.setRunTime(tempRuntimeElement[1]);

        }else{
            tvSeries.setRunTime(null);
        }

//        HtmlPage seasonsPage = client.getPage(seasonsUrl);
//        HtmlElement seasonsElement = imdbPage.getFirstByXPath("//div[@data-testid='episodes-browse-episodes']//div[last()]");
//        tvSeries.setSeason(seasonsElement.getTextContent());


        System.out.println("---FINISHEDDD---");

    }
}
