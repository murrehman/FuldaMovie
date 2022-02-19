package com.bwma.msc.cms.task;

import com.bwma.msc.cms.thread.movie.MovieThread;
import com.bwma.msc.cms.thread.ShortFetchingThread;
import com.bwma.msc.cms.thread.tv.TVThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OnColdStartTask {
    private static final Logger logger = LoggerFactory.getLogger(OnColdStartTask.class);

    @Value("${msc.app.coldStart}")
    private Boolean coldStart;

    private final ShortFetchingThread shortFetchingThread;
    private final MovieThread movieThread;
    private final TVThread tvThread;

    @Autowired
    public OnColdStartTask(ShortFetchingThread shortFetchingThread, MovieThread movieThread, @Qualifier("TVThread") TVThread tvThread) {
        this.shortFetchingThread = shortFetchingThread;
        this.movieThread = movieThread;
        this.tvThread = tvThread;
    }

    @PostConstruct
    public void init() {
            if (coldStart) {
                shortFetchingThread.start();
                movieThread.start();
                tvThread.start();
            }else{
                logger.info("Skipping Cold Start Task");
            }
    }
}
