package com.bwma.msc.cms.webClient.serverResponse.TV;

import com.bwma.msc.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TVDTO {
    private Integer id;
    private BigDecimal popularity;
    private Integer vote_count;
    private Date first_air_date;
    private String name;
    private BigDecimal vote_average;
    private String poster_path;
    private String overview;
    private String status;
    private ContentType contentType = ContentType.tv;
    private Boolean in_production;
    private Date last_air_date;
    private Integer number_of_episodes;
    private Integer number_of_seasons;
    private String original_language;
    private Genre[] genres;
    private Company[] production_companies;
    private Country[] production_countries;


    public Content parseToContent(){
        Content content = new Content();

        content.setId(id);
        content.setPopularity(popularity);
        content.setVoteCount(vote_count);
        content.setReleaseDate(first_air_date);
        content.setTitle(name);
        content.setVoteAverage(vote_average);
        content.setPosterPath(poster_path);
        content.setOverview(overview);
        content.setStatus(status);
        content.setContentType(contentType);
        content.setInProduction(in_production);
        content.setLastAirDate(last_air_date);
        content.setNumberOfEpisodes(number_of_episodes);
        content.setNumberOfSeasons(number_of_seasons);

        Language language = new Language(original_language);
        content.setOriginalLanguage(language);

        content.setGenre(Arrays.asList(genres));
        content.setCompanies(Arrays.asList(production_companies));
        content.setCountries(Arrays.asList(production_countries));


        return content;
    }
}
