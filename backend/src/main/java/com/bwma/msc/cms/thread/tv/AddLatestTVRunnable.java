package com.bwma.msc.cms.thread.tv;

import com.bwma.msc.service.SearchService.SearchService;
import com.bwma.msc.service.entityService.ContentService;
import com.bwma.msc.service.entityService.EpisodeService;
import com.bwma.msc.service.entityService.SeasonService;
import com.bwma.msc.service.entityService.WatchProviderService;
import com.bwma.msc.cms.webClient.content.TVWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AddLatestTVRunnable extends TVThread implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger( AddLatestTVRunnable.class );

    public AddLatestTVRunnable(TVWebClient tvWebClient, ContentService contentService, SearchService searchService, SeasonService seasonService, EpisodeService episodeService, WatchProviderService watchProviderService) {
        super(tvWebClient, contentService, searchService, seasonService, episodeService, watchProviderService);
    }

    @Override
    public void run() {
        try{
            logger.info("Getting latest added tv id");
            Collection<Integer> tvIDs = tvWebClient.getLatestAdded();

            logger.info("Saving latest added tv details");
            super.step2(tvIDs);

            logger.info("Getting latest added tv watch providers");
            super.step3(tvIDs);

            logger.info("Getting latest added tv seasons and episodes");
            super.step4(tvIDs);

        } catch (InterruptedException e) {
            logger.error("Error on threading system");
            logger.error(e.getMessage());
        }
    }
}
