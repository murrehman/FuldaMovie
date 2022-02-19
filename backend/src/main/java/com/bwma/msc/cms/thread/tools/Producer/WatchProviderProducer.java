package com.bwma.msc.cms.thread.tools.Producer;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.WatchProvider;
import com.bwma.msc.service.SearchService.SearchService;
import com.bwma.msc.cms.thread.tools.Buffer;
import com.bwma.msc.cms.webClient.content.ContentWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class WatchProviderProducer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(WatchProviderProducer.class);

    private final Buffer<WatchProvider> watchProviderBuffer;
    private final Collection<Integer> idList;
    private final ContentWebClient contentWebClient;
    private final SearchService searchService;
    private final String producerName;


    public WatchProviderProducer(Buffer<WatchProvider> watchProviderBuffer, Collection<Integer> idList, ContentWebClient contentWebClient, SearchService searchService, String producerName) {
        this.watchProviderBuffer = watchProviderBuffer;
        this.idList = idList;
        this.contentWebClient = contentWebClient;
        this.searchService = searchService;
        this.producerName = producerName;
    }

    @Override
    public void run() {
        for (Integer id : idList) {
            try {
                Content content = searchService.searchContentById(id);

                WatchProvider watchProvider = this.contentWebClient.getWatchProviders(content);

                this.watchProviderBuffer.enqueueElement(watchProvider);


            } catch (Exception e) {
                logger.error("There's no german providers for <" + id + ">");

            }
        }

        logger.info(this.producerName + " Ended");
        this.watchProviderBuffer.setProducerEnded(true);
    }
}
