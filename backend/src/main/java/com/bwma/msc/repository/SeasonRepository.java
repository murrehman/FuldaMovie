package com.bwma.msc.repository;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {

    Collection<Season> getSeasonByTv(Content tv);

    Season getSeasonByTvAndSeasonNumber(Content tv, Integer seasonNumber);
}
