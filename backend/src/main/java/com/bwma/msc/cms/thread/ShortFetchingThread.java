package com.bwma.msc.cms.thread;

import com.bwma.msc.entity.Country;
import com.bwma.msc.entity.Genre;
import com.bwma.msc.entity.Language;
import com.bwma.msc.service.entityService.CountryService;
import com.bwma.msc.service.entityService.GenreService;
import com.bwma.msc.service.entityService.LanguageService;
import com.bwma.msc.cms.webClient.CountryWebClient;
import com.bwma.msc.cms.webClient.GenreWebClient;
import com.bwma.msc.cms.webClient.LanguageWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShortFetchingThread extends Thread{
    private static final Logger logger = LoggerFactory.getLogger( ShortFetchingThread.class );

    private final GenreWebClient genreWebClient;
    private final GenreService genreService;

    private final CountryWebClient countryWebClient;
    private final CountryService countryService;

    private final LanguageWebClient languageWebClient;
    private final LanguageService languageService;

    @Autowired
    public ShortFetchingThread(GenreWebClient genreWebClient, GenreService genreService, CountryWebClient countryWebClient, CountryService countryService, LanguageWebClient languageWebClient, LanguageService languageService) {
        this.genreWebClient = genreWebClient;
        this.genreService = genreService;
        this.countryWebClient = countryWebClient;
        this.countryService = countryService;
        this.languageWebClient = languageWebClient;
        this.languageService = languageService;
    }

    @Override
    public void run() {
        fetchAndSaveGenres();
        fetchAndSaveCountries();
        fetchAndSaveLanguages();
    }

    private void fetchAndSaveLanguages() {
        logger.info("Fetching languages");
        List<Language> languageList = this.languageWebClient.getLanguageList();

        logger.info("Saving languages");
        this.languageService.createOrUpdateAll(languageList);
    }

    private void fetchAndSaveCountries() {
        logger.info("Fetching countries");
        List<Country> countryList = this.countryWebClient.getCountryList();

        logger.info("Saving countries");
        this.countryService.createOrUpdateAll(countryList);
    }

    private void fetchAndSaveGenres() {
        logger.info("Fetching genres");
        List<Genre> movieGenreList = this.genreWebClient.getMovieGenreList();
        List<Genre> tvGenreList = this.genreWebClient.getTVGenreList();

        logger.info("Saving movie genres");
        movieGenreList.forEach(genre -> {
            logger.info("Saving genre <"+genre.getId()+">");
            this.genreService.createOrUpdate(genre);
        });

        logger.info("Saving tv genres");
        tvGenreList.forEach(genre -> {
            logger.info("Saving genre <"+genre.getId()+">");
            this.genreService.createOrUpdate(genre);
        });
    }
}
