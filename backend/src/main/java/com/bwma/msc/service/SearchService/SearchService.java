package com.bwma.msc.service.SearchService;


import com.bwma.msc.entity.*;
import com.bwma.msc.exception.InvalidIDException;
import com.bwma.msc.cms.webClient.content.MovieWebClient;
import com.bwma.msc.cms.webClient.content.TVWebClient;
import com.bwma.msc.service.entityService.ContentService;
import com.bwma.msc.service.entityService.EpisodeService;
import com.bwma.msc.service.entityService.SeasonService;
import com.bwma.msc.service.entityService.WatchProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.Theme;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

@Service
public class SearchService {

    private final ContentService contentService;
    private final WatchProviderService watchProviderService;
    private final SeasonService seasonService;
    private final EpisodeService episodeService;
    private final MovieWebClient movieWebClient;
    private final TVWebClient tvWebClient;


    @Autowired
    public SearchService(ContentService contentService, WatchProviderService watchProviderService, SeasonService seasonService, EpisodeService episodeService, MovieWebClient movieWebClient, TVWebClient tvWebClient) {
        this.contentService = contentService;
        this.watchProviderService = watchProviderService;
        this.seasonService = seasonService;
        this.episodeService = episodeService;
        this.movieWebClient = movieWebClient;
        this.tvWebClient = tvWebClient;
    }


    public Content searchContentById(Integer id) {
        Optional<Content> query = contentService.findById(id);
        Content content;
        if (query.isPresent()) {
            content = query.get();
        } else {
            try {
                // Search ID as a movie
                content = movieWebClient.getByID(id);

                // In case its not a film, seach as tv
                if (content == null) {
                    content = tvWebClient.getByID(id);
                }

                // After searching as both film and tv
                // if it exists, save it,
                // fetch watch providers
                // and if its tv also seasons and episodes
                if (content != null) {
                    fetchAndSaveData(content);
                }
            } catch (InvalidIDException error) {
                content = null;
            }
        }

        return content;
    }

    public WatchProvider searchWatchProviderByContent(Content content){
        return watchProviderService.findByContent(content);
    }

    public Collection<Season> searchSeasonListByTv(Content tv) {
        if (tv.getContentType() == ContentType.tv) {
            return seasonService.findSeasonListByTV(tv);
        } else {
            throw new InvalidIDException("ID provided does not correspond with a tv show");
        }

    }

    public Season searchSeasonByTvAndSeasonNumber(Content tv, Integer seasonNumber) {
        if (tv.getContentType() == ContentType.tv) {
            return seasonService.findSeasonByTvAndSeasonNumber(tv, seasonNumber);
        } else {
            throw new InvalidIDException("ID provided does not correspond with a tv show");
        }
    }

    public Collection<Episode> searchEpisodeListBySeason(Season season){
        return episodeService.findBySeason(season);
    }

    public Episode searchEpisodeBySeasonAndEpisodeNumber(Season season, Integer episodeNumber){
        return episodeService.findBySeasonAndEpisodeNumber(season, episodeNumber);
    }


    public List<Content> searchContentByTitle(String title){
        return contentService.findContentByTitle(title);
    }




    private void fetchAndSaveData(Content content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Save content itself
                contentService.createOrUpdate(content);

                // Save Watch providers
                WatchProvider watchProvider = fetchWP(content);
                watchProviderService.createOrUpdate(watchProvider);

                // If its a tv save seasons and episodes
                if (content.getContentType() == ContentType.tv){
                    Map<Season, List<Episode>> seasonEpisodesMap = tvWebClient.getSeasonsAndEpisodes(content);
                    saveSeasonEpisodes(seasonEpisodesMap);
                }
            }
        }).start();
    }

    private WatchProvider fetchWP(Content content) {
        if(content.getContentType() == ContentType.movie){
            return movieWebClient.getWatchProviders(content);
        }else {
            return tvWebClient.getWatchProviders(content);
        }
    }

    private void saveSeasonEpisodes(Map<Season, List<Episode>> seasonEpisodesMap) {
        for (Entry<Season, List<Episode>> entry : seasonEpisodesMap.entrySet()) {
            seasonService.createOrUpdate(entry.getKey());

            for (Episode episode : entry.getValue()) {
                episodeService.createOrUpdate(episode);
            }

        }
    }
}
