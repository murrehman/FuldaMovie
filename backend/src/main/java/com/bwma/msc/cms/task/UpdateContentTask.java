package com.bwma.msc.cms.task;

import com.bwma.msc.cms.thread.movie.AddLatestMovieRunnable;
import com.bwma.msc.cms.thread.movie.UpdateMovieRunnable;
import com.bwma.msc.cms.thread.tv.AddLatestTVRunnable;
import com.bwma.msc.cms.thread.tv.UpdateTVRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateContentTask {
    private static final Logger logger = LoggerFactory.getLogger( UpdateContentTask.class );

    private final UpdateMovieRunnable updateMovieRunnable;
    private final UpdateTVRunnable updateTVRunnable;
    private final AddLatestMovieRunnable addLatestMovieRunnable;
    private final AddLatestTVRunnable addLatestTVRunnable;

    @Autowired
    public UpdateContentTask(UpdateMovieRunnable updateMovieRunnable, UpdateTVRunnable updateTVRunnable, AddLatestMovieRunnable addLatestMovieRunnable, AddLatestTVRunnable addLatestTVRunnable) {
        this.updateMovieRunnable = updateMovieRunnable;
        this.updateTVRunnable = updateTVRunnable;
        this.addLatestMovieRunnable = addLatestMovieRunnable;
        this.addLatestTVRunnable = addLatestTVRunnable;
    }

    /*The expression that describes "Once a Day at midnight"
     is "0 0 0 * * ?".
     This exactly means, from left to right:
     The second 0, the minute 0, of the our 0, every day, on every month,
     and the day of the week doesn't matter.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateContent(){
        new Thread(updateMovieRunnable).start();
        new Thread(updateTVRunnable).start();
    }

    @Scheduled(cron="* */5 * * * *")
    public void addContent(){
        new Thread(addLatestMovieRunnable).start();
        new Thread(addLatestTVRunnable).start();
    }
}
