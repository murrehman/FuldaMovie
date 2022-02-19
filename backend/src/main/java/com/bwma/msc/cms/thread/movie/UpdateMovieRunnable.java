package com.bwma.msc.cms.thread.movie;

import com.bwma.msc.service.SearchService.SearchService;
import com.bwma.msc.service.entityService.ContentService;
import com.bwma.msc.service.entityService.WatchProviderService;
import com.bwma.msc.cms.webClient.content.MovieWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UpdateMovieRunnable extends MovieThread implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger( UpdateMovieRunnable.class );

    @Autowired
    public UpdateMovieRunnable(MovieWebClient movieWebClient, ContentService contentService, SearchService searchService, WatchProviderService watchProviderService) {
        super(movieWebClient, contentService, searchService, watchProviderService);
    }

    @Override
    public void run() {
        try {
            logger.info("Movie Fetching Step 1: Discovering IDs");
            Collection<Integer> movieIDList = movieWebClient.discoverChanges();

            logger.info("Movie Fetching Step 2: Fetching And Saving Movies");
            super.step2(movieIDList);

            logger.info("Movie Fetching Step 3: Fetching Watch Providers");
            super.step3(movieIDList);
        } catch (InterruptedException e) {
            logger.error("Error on threading system");
            logger.error(e.getMessage());
        }
    }
}
