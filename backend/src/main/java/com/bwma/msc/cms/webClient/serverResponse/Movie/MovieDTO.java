package com.bwma.msc.cms.webClient.serverResponse.Movie;

import com.bwma.msc.entity.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDTO {
    private int id;
    private BigDecimal popularity;
    private Integer vote_count;
    private Date release_date;
    private String title;
    private BigDecimal vote_average;
    private String poster_path;
    private boolean adult;
    private String overview;
    private final ContentType contentType = ContentType.movie;
    private String original_language;
    private Genre[] genres;
    private String imdb_id;
    private Company[] production_companies;
    private Country[] production_countries;
    private Long revenue;
    private Integer budget;
    private Integer runtime;
    private String status;

    public Content parseToContent(){
        Content content = new Content();

        content.setId(id);
        content.setPopularity(popularity);
        content.setVoteCount(vote_count);
        content.setReleaseDate(release_date);
        content.setTitle(title);
        content.setVoteAverage(vote_average);
        content.setPosterPath(poster_path);
        content.setAdult(adult);
        content.setOverview(overview);
        content.setContentType(contentType);

        Language language = new Language(original_language);
        content.setOriginalLanguage(language);

        content.setGenre(Arrays.asList(genres));
        content.setImdbId(imdb_id);
        content.setCompanies(Arrays.asList(production_companies));
        content.setCountries(Arrays.asList(production_countries));
        content.setRevenue(revenue);
        content.setBudget(budget);
        content.setRuntime(runtime);
        content.setStatus(status);

        return content;
    }
}
