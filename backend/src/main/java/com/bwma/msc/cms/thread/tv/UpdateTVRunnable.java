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
public class UpdateTVRunnable extends TVThread implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger( UpdateTVRunnable.class );

    public UpdateTVRunnable(TVWebClient tvWebClient, ContentService contentService, SearchService searchService, SeasonService seasonService, EpisodeService episodeService, WatchProviderService watchProviderService) {
        super(tvWebClient, contentService, searchService, seasonService, episodeService, watchProviderService);
    }

    @Override
    public void run() {
        try{
            logger.info("Updating TV: getting IDs");
            Collection<Integer> tvIDs = tvWebClient.discoverChanges();

            logger.info("Updating TV: getting details");
            super.step2(tvIDs);

            logger.info("Updating: Fetching TV Watch Providers");
            super.step3(tvIDs);

            logger.info("TV Fetching Step 4: Fetching TV Seasons with its Episodes");
            super.step4(tvIDs);

        } catch (InterruptedException e) {
            logger.error("Error on threading system");
            logger.error(e.getMessage());
        }
    }
}
