package com.bwma.msc.cms.thread.tools.Producer;

import com.bwma.msc.entity.Content;
import com.bwma.msc.cms.thread.tools.Buffer;
import com.bwma.msc.cms.webClient.content.ContentWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ContentProducer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger( ContentProducer.class );

    private final Buffer<Content> moviesBuffer;
    private final Collection<Integer> idList;

    private final ContentWebClient contentWebClient;
    private final String producerName;

    public ContentProducer(Buffer<Content> moviesBuffer, Collection<Integer> idList, ContentWebClient contentWebClient, String producerName) {
        this.moviesBuffer = moviesBuffer;
        this.idList = idList;
        this.contentWebClient = contentWebClient;
        this.producerName = producerName;
    }

    @Override
    public void run() {
        for (Integer id : idList) {
            try{
                Content movie = this.contentWebClient.getByID(id);
                this.moviesBuffer.enqueueElement(movie);
            }catch (Exception e){
                logger.error("Error on producing content <"+id+">");
                logger.error(e.getMessage());
            }
        }

        logger.info(this.producerName+" Ended");
        this.moviesBuffer.setProducerEnded(true);
    }


}
