package com.bwma.msc.cms.thread.tools.Consumer;

import com.bwma.msc.entity.WatchProvider;
import com.bwma.msc.service.entityService.WatchProviderService;
import com.bwma.msc.cms.thread.tools.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WatchProviderConsumer extends Thread{
    private static final Logger logger = LoggerFactory.getLogger( WatchProviderConsumer.class );

    private final Buffer<WatchProvider> watchProviderBuffer;
    private final WatchProviderService watchProviderService;
    private final String consumerName;

    public WatchProviderConsumer(Buffer<WatchProvider> watchProviderBuffer, WatchProviderService watchProviderService, String consumerName) {
        this.watchProviderBuffer = watchProviderBuffer;
        this.watchProviderService = watchProviderService;
        this.consumerName = consumerName;
    }

    @Override
    public void run() {
        while (!exitProcess()){
            try{
                WatchProvider watchProvider = watchProviderBuffer.dequeElement();

                logger.info("Saving watch provider <"+watchProvider.getLink()+">");
                this.watchProviderService.createOrUpdate(watchProvider);
            } catch (InterruptedException e) {
                logger.error("Error on Threading system");
                logger.error(e.getMessage());
            } catch (Exception e){
                logger.error("Error while saving watch provider");
                logger.error(e.getMessage());
            }
        }

        logger.info(this.consumerName + " Ended");
    }

    private boolean exitProcess(){
        return watchProviderBuffer.isProducerEnded() && watchProviderBuffer.isEmpty();
    }
}
