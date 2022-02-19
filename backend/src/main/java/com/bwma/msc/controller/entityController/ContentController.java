package com.bwma.msc.controller.entityController;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.Episode;
import com.bwma.msc.entity.Season;
import com.bwma.msc.entity.WatchProvider;
import com.bwma.msc.exception.ContentNotFoundException;
import com.bwma.msc.exception.SeasonNotFoundException;
import com.bwma.msc.service.SearchService.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final SearchService searchService;


    @Autowired
    public ContentController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("{id}")
    Content getContentByID(@PathVariable Integer id){
        Content content = searchService.searchContentById(id);
        checkContentIsNotNull(id, content);

        return content;
    }

    @GetMapping("{id}/providers")
    WatchProvider getWatchProvidersByContent(@PathVariable Integer id){
        Content content = searchService.searchContentById(id);
        checkContentIsNotNull(id, content);

        WatchProvider watchProvider = searchService.searchWatchProviderByContent(content);
        checkWatchProviderIsNotNull(id,watchProvider);

        return watchProvider;
    }

    @GetMapping("{id}/season")
    Collection<Season> getSeasonListByID(@PathVariable Integer id){
        Content tv = searchService.searchContentById(id);
        checkContentIsNotNull(id,tv);

        Collection<Season> seasonCollection = searchService.searchSeasonListByTv(tv);
        checkCollectionIsNotNull(id,seasonCollection);

        return seasonCollection;
    }

    @GetMapping("{id}/season/{seasonNumber}")
    Season getSeasonByIdAndSeasonNumber(@PathVariable Integer id,@PathVariable Integer seasonNumber){
        Content tv = searchService.searchContentById(id);
        checkContentIsNotNull(id,tv);

        Season season = searchService.searchSeasonByTvAndSeasonNumber(tv, seasonNumber);
        checkSeasonIsNotNull(id,seasonNumber,season);

        return season;
    }

    @GetMapping("{id}/season/{seasonNumber}/episode")
    Collection<Episode> getEpisodeListBySeason(@PathVariable Integer id,@PathVariable Integer seasonNumber){
        Content tv = searchService.searchContentById(id);
        checkContentIsNotNull(id,tv);

        Season season = searchService.searchSeasonByTvAndSeasonNumber(tv,seasonNumber);
        checkSeasonIsNotNull(id, seasonNumber, season);

        Collection<Episode> episodesCollection = searchService.searchEpisodeListBySeason(season);
        checkCollectionIsNotNull(id,episodesCollection);

        return episodesCollection;
    }

    @GetMapping("{id}/season/{seasonNumber}/episode/{episodeNumber}")
    Episode getEpisodeBySeasonAndEpisodeNumber(@PathVariable Integer id,@PathVariable Integer seasonNumber, @PathVariable Integer episodeNumber){
        Content tv = searchService.searchContentById(id);
        checkContentIsNotNull(id,tv);

        Season season = searchService.searchSeasonByTvAndSeasonNumber(tv,seasonNumber);
        checkSeasonIsNotNull(id, seasonNumber, season);

        return searchService.searchEpisodeBySeasonAndEpisodeNumber(season,episodeNumber);
    }

    @GetMapping("/search/{query}")
    List<Content> searchContentByTitle(@PathVariable String query){
        return searchService.searchContentByTitle(query);
    }



    private void checkContentIsNotNull(Integer id, Content content) {
        if(content == null){
            throw new ContentNotFoundException("Content with id "+id+" was not found");
        }
    }

    private void checkWatchProviderIsNotNull(Integer id, WatchProvider watchProvider) {
        if(watchProvider == null){
            throw new ContentNotFoundException("Watch providers for "+id+" were not found");
        }
    }

    private void checkCollectionIsNotNull(Integer id, Collection<?> collection) {
        if(collection == null || collection.isEmpty()){
            throw new ContentNotFoundException("data for "+id+" was not found");
        }
    }

    private void checkSeasonIsNotNull(Integer id, Integer seasonNumber, Season season) {
        if(season == null){
            throw  new SeasonNotFoundException("Season "+ seasonNumber +" for tv "+ id +" was not found" );
        }
    }
}
