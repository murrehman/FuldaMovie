package com.bwma.msc.controller;

import com.bwma.msc.entity.Content;
import com.bwma.msc.service.IndexService.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {

    private IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("/trending")
    List<Content> getTrendingMovies(){
        return indexService.getTrending();
    }

    @GetMapping("/upcoming")
    List<Content> getUpcomingMovies(){
        return indexService.getUpcoming();
    }

    @GetMapping("/now_playing")
    List<Content> getNowPlayingMovies(){
        return indexService.getNowPlaying();
    }

    @GetMapping("/most_popular")
    List<Content> getMostPopular(@RequestParam(name="page") Integer page){
        return indexService.getMostPopular(page);
    }

    @GetMapping("/most_voted")
    List<Content> getMostVoted(@RequestParam(name="page") Integer page){
        return indexService.getMostVoted(page);
    }
}
