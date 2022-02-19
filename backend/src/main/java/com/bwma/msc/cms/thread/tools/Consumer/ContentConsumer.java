package com.bwma.msc.cms.thread.tools.Consumer;

import com.bwma.msc.entity.Content;
import com.bwma.msc.service.entityService.ContentService;
import com.bwma.msc.cms.thread.tools.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentConsumer extends Thread{
    private static final Logger logger = LoggerFactory.getLogger( ContentConsumer.class );

    private final Buffer<Content> contentBuffer;
    private final ContentService contentService;
    private final String consumerName;

    public ContentConsumer(Buffer<Content> contentBuffer, ContentService contentService, String consumerName) {
        this.contentBuffer = contentBuffer;
        this.contentService = contentService;
        this.consumerName = consumerName;
    }

    @Override
    public void run(){
        while (!exitProcess()){
            try{
                Content content = this.contentBuffer.dequeElement();

                logger.info("Saving content <"+content.getId()+">");
                this.contentService.createOrUpdate(content);

            } catch (InterruptedException e) {
                logger.error("Error on Threading system");
                logger.error(e.getMessage());
            } catch (Exception e){
                logger.error("Error while saving content");
                logger.error(e.getMessage());
            }
        }

        logger.info(this.consumerName + " Ended");
    }

    private boolean exitProcess(){
        return contentBuffer.isProducerEnded() && contentBuffer.isEmpty();
    }
}
