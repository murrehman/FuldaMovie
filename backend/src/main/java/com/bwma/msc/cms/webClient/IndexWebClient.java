package com.bwma.msc.cms.webClient;

import com.bwma.msc.cms.webClient.serverResponse.Result;
import com.bwma.msc.cms.webClient.serverResponse.ResultsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class IndexWebClient {
    @Value("${tmdb.api.baseUrl}")
    private String baseUrl;
    @Value("${tmdb.api.secret}")
    private String apiKey;

    private WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(IndexWebClient.class);
    private Result[] idWrappers;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(this.baseUrl)
                .build();
    }


    public List<Integer> getTrending() {
        logger.info("Getting Trendy Movies");

        Mono<ResultsPage> monoMovies = this.fetchTrendingMovies();

        return this.monoToIDListParser(monoMovies);
    }

    public List<Integer> getUpcoming() {
        logger.info("Getting Upcoming Movies");

        Mono<ResultsPage> monoMovies = this.fetchUpcomingMovies();

        return this.monoToIDListParser(monoMovies);
    }

    public List<Integer> getNowPlaying() {
        logger.info("Getting Movies in Theaters");

        Mono<ResultsPage> monoMovies = this.fetchNowPlayingMovies();

        return this.monoToIDListParser(monoMovies);
    }


    private Mono<ResultsPage> fetchTrendingMovies() {
        String uri = "/trending/movie/day?api_key=" + this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(ResultsPage.class);
    }

    private Mono<ResultsPage> fetchUpcomingMovies() {
        String uri = "/movie/upcoming?api_key=" + this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(ResultsPage.class);
    }

    private Mono<ResultsPage> fetchNowPlayingMovies() {
        String uri = "/movie/now_playing?api_key=" + this.apiKey+"&region=de";

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(ResultsPage.class);
    }

    private List<Integer> monoToIDListParser(Mono<ResultsPage> monoMovies) {
        ResultsPage trendingPage = monoMovies.block();

        Result[] idWrappers = trendingPage.getResults();

        List<Integer> idList = new ArrayList<>();

        for (Result idWrapper : idWrappers) {
            idList.add(idWrapper.getId());
        }

        return idList;
    }
}
