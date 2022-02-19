package com.bwma.msc.cms.thread.movie;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.WatchProvider;
import com.bwma.msc.service.SearchService.SearchService;
import com.bwma.msc.service.entityService.ContentService;
import com.bwma.msc.service.entityService.WatchProviderService;
import com.bwma.msc.cms.thread.tools.Buffer;
import com.bwma.msc.cms.thread.tools.Consumer.ContentConsumer;
import com.bwma.msc.cms.thread.tools.Consumer.WatchProviderConsumer;
import com.bwma.msc.cms.thread.tools.Producer.ContentProducer;
import com.bwma.msc.cms.thread.tools.Producer.WatchProviderProducer;
import com.bwma.msc.cms.webClient.content.MovieWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Set;

@Component
public class MovieThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(MovieThread.class);

    protected final MovieWebClient movieWebClient;
    private final ContentService contentService;
    private final SearchService searchService;
    private final WatchProviderService watchProviderService;

    private final Buffer<Content> moviesBuffer = new Buffer<>();
    private final Buffer<WatchProvider> watchProviderBuffer = new Buffer<>();

    @Autowired
    public MovieThread(MovieWebClient movieWebClient, ContentService contentService, SearchService searchService, WatchProviderService watchProviderService) {
        this.movieWebClient = movieWebClient;
        this.contentService = contentService;
        this.searchService = searchService;
        this.watchProviderService = watchProviderService;
    }

    @Override
    public void run(){
        try {
            logger.info("Movie Fetching Step 1: Discovering IDs");
            Set<Integer> movieIDSet = movieWebClient.discoverIDs();

            logger.info("Movie Fetching Step 2: Fetching And Saving Movies");
            step2(movieIDSet);

            logger.info("Movie Fetching Step 3: Fetching Watch Providers");
            step3(movieIDSet);
        } catch (InterruptedException e) {
            logger.error("Error on threading system");
            logger.error(e.getMessage());
        }
    }

    protected void step2(Collection<Integer> movieIDSet) throws InterruptedException {
        ContentProducer movieProducer = new ContentProducer(moviesBuffer, movieIDSet, movieWebClient, "Movie Producer");
        ContentConsumer movieConsumer = new ContentConsumer(moviesBuffer,contentService, "Movie Consumer");

        movieProducer.start();
        movieConsumer.start();

        movieProducer.join();
        movieConsumer.join();
    }

    protected void step3(Collection<Integer> movieIDSet) {
        WatchProviderProducer watchProviderProducer = new WatchProviderProducer(watchProviderBuffer, movieIDSet,movieWebClient, searchService, "Movie WP Producer");
        WatchProviderConsumer watchProviderConsumer = new WatchProviderConsumer(watchProviderBuffer,watchProviderService,"Movie WP consumer");

        watchProviderProducer.start();
        watchProviderConsumer.start();
    }

}
