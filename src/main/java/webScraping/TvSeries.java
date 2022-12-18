package webScraping;

import lombok.Data;

import java.util.List;


@Data
public class TvSeries {
    private String id;
    private String title;
    private String yearOfProduction;
    private String releaseDate;
    private String runTime;
    private String publishOrganization;
    private String createdBy;
    private String season;
    private String episodes;
    private String castList;
    private String ImDbRatingVotes;
    private String imDbRating;
    private String stars;
    private String releaseDateAndEndDate;
    private String scriptWriters;
    private String starring;
    private String genre;
    private String releaseEndDate;
}
