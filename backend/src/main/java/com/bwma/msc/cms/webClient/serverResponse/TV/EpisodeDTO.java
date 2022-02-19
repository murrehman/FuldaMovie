package com.bwma.msc.cms.webClient.serverResponse.TV;

import com.bwma.msc.entity.Episode;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EpisodeDTO {
    private Date air_date;
    private Integer episode_number;
    private String name;
    private String overview;
    private Integer id;
    private String production_code;
    private Integer season_number;
    private BigDecimal vote_average;
    private Integer vote_count;

    public Episode getEpisode(){
        Episode episode = new Episode();

        episode.setAirDate(air_date);
        episode.setEpisodeNumber(episode_number);
        episode.setName(name);
        episode.setOverview(overview);
        episode.setId(id);
        episode.setProductionCode(production_code);
        episode.setSeasonNumber(season_number);
        episode.setVoteAverage(vote_average);
        episode.setVoteCount(vote_count);

        return episode;
    }


}
