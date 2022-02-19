package com.bwma.msc.cms.webClient.serverResponse.TV;

import com.bwma.msc.entity.Episode;
import com.bwma.msc.entity.Season;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SeasonDTO {
    private Date air_date;
    private Integer id;
    private String name;
    private String overview;
    private String poster_path;
    private Integer season_number;
    private EpisodeDTO[] episodes;

    public Season getSeason(){
        Season season = new Season();

        season.setAirDate(air_date);
        season.setId(id);
        season.setName(name);
        season.setOverview(overview);
        season.setPosterPath(poster_path);
        season.setSeasonNumber(season_number);
        season.setEpisodeCount(episodes.length);

        return season;
    }

    public List<Episode> getSeasonEpisodes(){
        List<Episode> episodeList = new ArrayList<>();
        Season season = this.getSeason();

        for (EpisodeDTO episodeDTO : episodes) {
            Episode episode = episodeDTO.getEpisode();
            episode.setSeason(season);
            episodeList.add(episode);
        }

        return episodeList;
    }
}


