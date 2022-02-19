package com.bwma.msc.cms.thread.tv;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.Episode;
import com.bwma.msc.entity.Season;
import com.bwma.msc.entity.WatchProvider;
import com.bwma.msc.service.SearchService.SearchService;
import com.bwma.msc.service.entityService.ContentService;
import com.bwma.msc.service.entityService.EpisodeService;
import com.bwma.msc.service.entityService.SeasonService;
import com.bwma.msc.service.entityService.WatchProviderService;
import com.bwma.msc.cms.thread.tools.Buffer;
import com.bwma.msc.cms.thread.tools.Consumer.ContentConsumer;
import com.bwma.msc.cms.thread.tools.Consumer.SeasonConsumer;
import com.bwma.msc.cms.thread.tools.Consumer.WatchProviderConsumer;
import com.bwma.msc.cms.thread.tools.Producer.ContentProducer;
import com.bwma.msc.cms.thread.tools.Producer.SeasonProducer;
import com.bwma.msc.cms.thread.tools.Producer.WatchProviderProducer;
import com.bwma.msc.cms.webClient.content.TVWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

@Component
public class TVThread extends Thread{
    private static final Logger logger = LoggerFactory.getLogger( TVThread.class );

    protected final TVWebClient tvWebClient;
    private final ContentService contentService;
    private final SearchService searchService;
    private final SeasonService seasonService;
    private final EpisodeService episodeService;
    private final WatchProviderService watchProviderService;

    private final Buffer<Content> tvBuffer = new Buffer<>();
    private final Buffer<WatchProvider> watchProviderBuffer = new Buffer<>();
    private final Buffer<Entry<Season,List<Episode>>> seasonEpisodesBuffer = new Buffer<>();

    @Autowired
    public TVThread(TVWebClient tvWebClient, ContentService contentService, SearchService searchService, SeasonService seasonService, EpisodeService episodeService, WatchProviderService watchProviderService) {
        this.tvWebClient = tvWebClient;
        this.contentService = contentService;
        this.searchService = searchService;
        this.seasonService = seasonService;
        this.episodeService = episodeService;
        this.watchProviderService = watchProviderService;
    }

    @Override
    public void run(){
        try{
            logger.info("TV Fetching Step 1: Discovering IDs");
            Set<Integer> tvIDs = tvWebClient.discoverIDs();

            logger.info("TV Fetching Step 2: Fetching TV Details");
            step2(tvIDs);

            logger.info("TV Fetching Step 3: Fetching TV Watch Providers");
            step3(tvIDs);

            logger.info("TV Fetching Step 4: Fetching TV Seasons with its Episodes");
            step4(tvIDs);



        } catch (InterruptedException e) {
            logger.error("Error on threading system");
            logger.error(e.getMessage());
        }
    }

    protected void step4(Collection<Integer> tvIDs) throws InterruptedException {
        SeasonProducer seasonProducer = new SeasonProducer(seasonEpisodesBuffer, tvIDs,searchService,tvWebClient,"Season Producer");
        SeasonConsumer seasonConsumer = new SeasonConsumer(seasonEpisodesBuffer,seasonService, episodeService, "Season Consumer");

        seasonProducer.start();
        seasonConsumer.start();
    }

    protected void step3(Collection<Integer> tvIDs) {
        WatchProviderProducer watchProviderProducer = new WatchProviderProducer(watchProviderBuffer, tvIDs,tvWebClient, searchService, "TV WP Producer");
        WatchProviderConsumer watchProviderConsumer = new WatchProviderConsumer(watchProviderBuffer,watchProviderService,"TV WP consumer");

        watchProviderProducer.start();
        watchProviderConsumer.start();
    }

    protected void step2(Collection<Integer> tvIDs) throws InterruptedException {
        ContentProducer tvProducer = new ContentProducer(tvBuffer, tvIDs, tvWebClient, "TV Producer");
        ContentConsumer tvConsumer = new ContentConsumer(tvBuffer,contentService, "TV Consumer");

        tvProducer.start();
        tvConsumer.start();

        tvProducer.join();
        tvConsumer.join();
    }
}
