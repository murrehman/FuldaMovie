package com.bwma.msc.cms.thread.tools.Consumer;

import com.bwma.msc.entity.Episode;
import com.bwma.msc.entity.Season;
import com.bwma.msc.service.entityService.EpisodeService;
import com.bwma.msc.service.entityService.SeasonService;
import com.bwma.msc.cms.thread.tools.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map.Entry;

public class SeasonConsumer extends Thread{
    private static final Logger logger = LoggerFactory.getLogger( SeasonConsumer.class );

    private final Buffer<Entry<Season, List<Episode>>> seasonEpisodesBuffer;
    private final SeasonService seasonService;
    private final EpisodeService episodeService;
    private final String consumerName;


    public SeasonConsumer(Buffer<Entry<Season, List<Episode>>> seasonEpisodesBuffer, SeasonService seasonService, EpisodeService episodeService, String consumerName) {
        this.seasonEpisodesBuffer = seasonEpisodesBuffer;
        this.seasonService = seasonService;
        this.episodeService = episodeService;
        this.consumerName = consumerName;
    }

    @Override
    public void run() {
        while (!exitProcess()){
            try{
                Entry<Season,List<Episode>> entry = this.seasonEpisodesBuffer.dequeElement();

                Season season = entry.getKey();
                List<Episode> episodeList = entry.getValue();

                logger.info("Saving Season <"+season.getId()+">");
                this.seasonService.createOrUpdate(season);

                episodeList.forEach(episode ->{
                    logger.info("Saving Episode <"+episode.getId()+">");
                    this.episodeService.createOrUpdate(episode);
                });

            } catch (InterruptedException e) {
                logger.error("Error on Threading system");
                logger.error(e.getCause().toString());
            } catch (Exception e){
                logger.error("Error while saving season or episode");
                logger.error(e.getCause().toString());
            }
        }

        logger.info(this.consumerName + " Ended");
    }

    private boolean exitProcess(){
        return seasonEpisodesBuffer.isProducerEnded() && seasonEpisodesBuffer.isEmpty();
    }
}
