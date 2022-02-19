package com.bwma.msc.service.entityService;

import com.bwma.msc.entity.Provider;
import com.bwma.msc.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {
    private final ProviderRepository repository;

    @Autowired
    public ProviderService(ProviderRepository repository){
        this.repository = repository;
    }

    //Create
    //Update
    public void createOrUpdate(Provider Provider){
        repository.save(Provider);
    }

    //Read
    public List<Provider> findAll(){
        return repository.findAll();
    }

    public Provider findById(Integer provider_id){
        Optional<Provider> Provider = repository.findById(provider_id);

        return Provider.orElse(null);
    }

    //Delete
    public void deleteById (Integer provider_id){
        repository.deleteById(provider_id);
    }
}
