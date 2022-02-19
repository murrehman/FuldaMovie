package com.bwma.msc.service.entityService;

import com.bwma.msc.entity.Episode;
import com.bwma.msc.entity.Season;
import com.bwma.msc.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EpisodeService {
    private final EpisodeRepository repository;

    @Autowired
    public EpisodeService(EpisodeRepository repository) {
        this.repository = repository;
    }


    //Create
    //Update
    public void createOrUpdate(Episode Episode) {
        repository.save(Episode);
    }

    public void createOrUpdate(Collection<Episode> episodeSet) {
        repository.saveAll(episodeSet);
    }

    //Read
    public Episode findById(Integer id) {
        Optional<Episode> Episode = repository.findById(id);

        return Episode.orElse(null);
    }

    public Collection<Episode> findBySeason(Season season) {
        return repository.findEpisodeBySeason(season);
    }

    public Episode findBySeasonAndEpisodeNumber(Season season, Integer episodeNumber) {
        return repository.findEpisodeBySeasonAndEpisodeNumber(season, episodeNumber);
    }
}
