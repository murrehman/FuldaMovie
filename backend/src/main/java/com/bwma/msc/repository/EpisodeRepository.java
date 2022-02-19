package com.bwma.msc.repository;

import com.bwma.msc.entity.Episode;
import com.bwma.msc.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode,Integer> {

    Collection<Episode> findEpisodeBySeason(Season season);

    Episode findEpisodeBySeasonAndEpisodeNumber(Season season, Integer episodeNumber);
}
