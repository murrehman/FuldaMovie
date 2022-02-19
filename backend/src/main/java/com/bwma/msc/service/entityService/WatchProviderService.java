package com.bwma.msc.service.entityService;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.WatchProvider;
import com.bwma.msc.repository.WatchProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WatchProviderService {
    private final WatchProviderRepository repository;

    @Autowired
    public WatchProviderService(WatchProviderRepository repository){
        this.repository = repository;
    }

    //Create
    //Update
    public void createOrUpdate(WatchProvider WatchProvider){
        repository.save(WatchProvider);
    }

    public void createOrUpdateAll(Collection<WatchProvider> watchProviderList){
        repository.saveAll(watchProviderList);
    }

    //Read
    public WatchProvider findById(String link){
        Optional<WatchProvider> WatchProvider = repository.findById(link);

        return WatchProvider.orElse(null);
    }

    public WatchProvider findByContent(Content content){
        return repository.getWatchProviderByContent(content);
    }

    //Delete
    public void deleteById (String link){
        repository.deleteById(link);
    }

}
