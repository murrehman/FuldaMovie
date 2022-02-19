package com.bwma.msc.service.entityService;

import com.bwma.msc.entity.Country;
import com.bwma.msc.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository repository;

    @Autowired
    public CountryService(CountryRepository repository){
        this.repository = repository;
    }

    //Create
    //Update
    public void createOrUpdate(Country Country){
        repository.save(Country);
    }

    public void createOrUpdateAll(List<Country> countryList){
        repository.saveAll(countryList);
    }

    //Read
    public Country findById(String iso_3166_1){
        Optional<Country> Country = repository.findById(iso_3166_1);

        return Country.orElse(null);
    }

    //Delete
    public void deleteById (String iso_3166_1){
        repository.deleteById(iso_3166_1);
    }
}
