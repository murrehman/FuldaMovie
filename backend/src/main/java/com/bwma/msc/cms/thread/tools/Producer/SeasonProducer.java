package com.bwma.msc.cms.thread.tools.Producer;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.Episode;
import com.bwma.msc.entity.Season;
import com.bwma.msc.service.SearchService.SearchService;
import com.bwma.msc.cms.thread.tools.Buffer;
import com.bwma.msc.cms.webClient.content.TVWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SeasonProducer extends Thread{
    private static final Logger logger = LoggerFactory.getLogger( SeasonProducer.class );

    private final Buffer<Entry<Season,List<Episode>>> seasonEpisodesBuffer;
    private final Collection<Integer> idList;
    private final SearchService searchService;
    private final TVWebClient tvWebClient;
    private final String producerName;

    public SeasonProducer(Buffer<Entry<Season,List<Episode>>> seasonEpisodesBuffer, Collection<Integer> idList, SearchService searchService, TVWebClient tvWebClient, String producerName) {
        this.seasonEpisodesBuffer = seasonEpisodesBuffer;
        this.idList = idList;
        this.searchService = searchService;
        this.tvWebClient = tvWebClient;
        this.producerName = producerName;
    }


    @Override
    public void run() {
        for (Integer id : idList) {
            try{
                Content tv = this.searchService.searchContentById(id);
                Map<Season,List<Episode>> seasonEpisodesMap = this.tvWebClient.getSeasonsAndEpisodes(tv);

               seasonEpisodesMap.entrySet().forEach(entry ->{
                   seasonEpisodesBuffer.enqueueElement(entry);
               });

            }catch (Exception e){
                logger.error("There's no seasons for <"+id+">");
            }
        }

        logger.info(this.producerName+" Ended");
        this.seasonEpisodesBuffer.setProducerEnded(true);
    }
}
