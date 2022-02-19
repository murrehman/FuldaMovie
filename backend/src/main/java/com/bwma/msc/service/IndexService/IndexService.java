package com.bwma.msc.service.IndexService;

import com.bwma.msc.entity.Content;
import com.bwma.msc.repository.ContentRepository;
import com.bwma.msc.service.SearchService.SearchService;
import com.bwma.msc.cms.webClient.IndexWebClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService {

    private final Integer PAGE_SIZE = 9;

    private final IndexWebClient indexWebClient;
    private final SearchService searchService;
    private final ContentRepository contentRepository;

    public IndexService(IndexWebClient indexWebClient, SearchService searchService, ContentRepository contentRepository) {
        this.indexWebClient = indexWebClient;
        this.searchService = searchService;
        this.contentRepository = contentRepository;
    }

    public List<Content> getTrending(){
        List<Integer> trendingIdList = indexWebClient.getTrending();

        return parseIdListToContentList(trendingIdList);
    }

    public List<Content> getUpcoming(){
        List<Integer> trendingIdList = indexWebClient.getUpcoming();

        return parseIdListToContentList(trendingIdList);
    }

    public List<Content> getNowPlaying(){
        List<Integer> trendingIdList = indexWebClient.getNowPlaying();

        return parseIdListToContentList(trendingIdList);
    }

    public List<Content> getMostPopular(Integer page){
        //Pages are 0 indexed
        Pageable pageRequest = PageRequest.of(page-1,PAGE_SIZE);

        return contentRepository.getContentByOrderByPopularityDesc(pageRequest);
    }

    public List<Content> getMostVoted(Integer page){
        Pageable pageRequest = PageRequest.of(page-1, PAGE_SIZE);

        return contentRepository.getContentByOrderByVoteCountDesc(pageRequest);
    }

    private List<Content> parseIdListToContentList(List<Integer> idList){
        List<Content> MovieList = new ArrayList<>();

        for (Integer id : idList) {
            Content movie = searchService.searchContentById(id);

            MovieList.add(movie);
        }

        return  MovieList;
    }

}
