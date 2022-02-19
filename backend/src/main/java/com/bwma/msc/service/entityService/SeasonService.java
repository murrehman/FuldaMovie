package com.bwma.msc.service.entityService;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.Season;
import com.bwma.msc.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SeasonService {
    private final SeasonRepository repository;

    @Autowired
    public SeasonService(SeasonRepository repository){
        this.repository = repository;
    }

    //Create
    //Update
    public void createOrUpdate(Season Season){
        repository.save(Season);
    }

    //Read
    public Season findById(Integer id){
        Optional<Season> Season = repository.findById(id);

        return Season.orElse(null);
    }

    public Season findSeasonByTvAndSeasonNumber(Content tv, Integer seasonNumber){
        return repository.getSeasonByTvAndSeasonNumber(tv,seasonNumber);
    }

    public Collection<Season> findSeasonListByTV(Content tv){
        return repository.getSeasonByTv(tv);
    }

    //Delete
    public void deleteById (Integer id){
        repository.deleteById(id);
    }


}
