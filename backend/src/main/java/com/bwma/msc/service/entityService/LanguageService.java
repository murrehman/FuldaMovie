package com.bwma.msc.service.entityService;

import com.bwma.msc.entity.Genre;
import com.bwma.msc.entity.Language;
import com.bwma.msc.repository.GenreRepository;
import com.bwma.msc.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {
    private final LanguageRepository repository;

    @Autowired
    public LanguageService(LanguageRepository repository) {
        this.repository = repository;
    }

    //Create
    //Update
    public void createOrUpdate(Language language) {
        repository.save(language);
    }

    public void createOrUpdateAll(List<Language> languageList) {
        repository.saveAll(languageList);
    }

    //Read
    public List<Language> findAll() {
        return repository.findAll();
    }

    public Language findById(String iso_639_1) {
        Optional<Language> language = repository.findById(iso_639_1);

        return language.orElse(null);
    }

    //Delete
    public void deleteById(String iso_639_1) {
        repository.deleteById(iso_639_1);
    }


}
