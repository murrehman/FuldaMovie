package com.bwma.msc.cms.webClient.content;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.Country;
import com.bwma.msc.entity.WatchProvider;
import com.bwma.msc.exception.InvalidIDException;
import com.bwma.msc.cms.webClient.GenreWebClient;
import com.bwma.msc.cms.webClient.serverResponse.Result;
import com.bwma.msc.cms.webClient.serverResponse.ResultsPage;
import com.bwma.msc.cms.webClient.serverResponse.Movie.MovieDTO;
import com.bwma.msc.cms.webClient.serverResponse.WatchProvider.ProviderWrapper;
import com.bwma.msc.cms.webClient.serverResponse.WatchProvider.ProvidersPage;
import com.bwma.msc.cms.webClient.serverResponse.WatchProvider.WatchProviderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class MovieWebClient implements ContentWebClient{
    private static final int MAX_PAGES = 500;

    @Value("${tmdb.api.baseUrl}")
    private String baseUrl;
    @Value("${tmdb.api.secret}")
    private String apiKey;

    private WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger(GenreWebClient.class);

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(this.baseUrl)
                .build();
    }

    // Discover - Task Step 1
    public Set<Integer> discoverIDs() {
        EnumSet<MovieFetchCriteria> fetchCriterias = EnumSet.allOf(MovieFetchCriteria.class);
        Set<Integer> IDBuffer = new HashSet<>();

        for (MovieFetchCriteria criteria : fetchCriterias) {
            List<Integer> IDList = this.discoverIDs(criteria);

            IDBuffer.addAll(IDList);
        }

        return IDBuffer;
    }

    private List<Integer> discoverIDs(MovieFetchCriteria fetchCriteria) {
        List<Integer> criteriaBuffer = new ArrayList<>();

        for (int i = 1; i <= MAX_PAGES; i++) {
            List<Integer> IDList = this.discoverIDs(fetchCriteria, i);

            criteriaBuffer.addAll(IDList);
        }

        return criteriaBuffer;
    }

    private List<Integer> discoverIDs(MovieFetchCriteria fetchCriteria, Integer MoviePage) {
        logger.info("Discovering Movies -> Sort by: <" + fetchCriteria + "> Page: <" + MoviePage + ">");

        Mono<ResultsPage> resultsPageMono = this.fetchDiscoverMovies(fetchCriteria, MoviePage);

        List<Integer> IDList = this.parseResultsPageToIDList(resultsPageMono);

        return IDList;
    }

    // Get Movie Details - Task Step 2 - Searching
    // Error control is added because the method can be called due to a user interaction.
    public Content getByID(Integer id) throws InvalidIDException {
        if (id == null || id < 0) {
            throw new InvalidIDException("ID must be positive");
        } else {
            try {
                logger.info("Fetching Movie Details: <" + id + ">");

                Mono<MovieDTO> monoMovie = this.fetchMovieDetails(id);

                return this.monoToContentParser(monoMovie);
            } catch (WebClientResponseException exception) {
                return null;
            }
        }
    }

    // Watch providers - Task Step 3
    public WatchProvider getWatchProviders(Content movie) {
        Mono<ProvidersPage> monoProviders = this.fetchWatchProviders(movie.getId());

        ProvidersPage wrapper = monoProviders.block();

        WatchProvider provider = monoToWatchProviderParser(monoProviders);

        if(provider != null){
            provider.setContent(movie);
            provider.setCountry(new Country("DE", "Germany"));
        }


        return provider;
    }

    public List<Integer> discoverChanges(){
        Mono<ResultsPage> resultsPageMono = this.fetchMovieChanges();

        return parseResultsPageToIDList(resultsPageMono);
    }

    public List<Integer> getLatestAdded(){
        List<Integer> idList = new LinkedList<>();

        Mono<Result> idMono = this.fetchLatestAdded();
        Integer id = parseResultToID(idMono);
        idList.add(id);
        return idList;
    }

    // Fetchers
    private Mono<ResultsPage> fetchDiscoverMovies(MovieFetchCriteria fetchCriteria, Integer MoviePage) {
        String uri = "/discover/movie?api_key=" + this.apiKey + "&sort_by=" + fetchCriteria.getCriteria() + "&page=" + MoviePage;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(ResultsPage.class);
    }

    private Mono<MovieDTO> fetchMovieDetails(Integer movie_id) {
        String uri = "/movie/" + movie_id + "?api_key=" + this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(MovieDTO.class);
    }

    private Mono<ProvidersPage> fetchWatchProviders(Integer movie_id) {
        String uri = "/movie/" + movie_id + "/watch/providers?api_key=" + this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(ProvidersPage.class);
    }

    private Mono<ResultsPage> fetchMovieChanges(){
        String uri = "/movie/changes?api_key"+this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(ResultsPage.class);
    }

    private Mono<Result> fetchLatestAdded() {
        String uri = "/movie/latest?api_key=" + this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(Result.class);
    }

    // Parsers
    private List<Integer> parseResultsPageToIDList(Mono<ResultsPage> ResultsPageMono) {
        ResultsPage resultsPage = ResultsPageMono.block();
        List<Result> resultList = Arrays.asList(resultsPage.getResults());
        List<Integer> idList = new ArrayList<>();

        resultList.forEach((result) -> {
            idList.add(result.getId());
        });

        return idList;
    }

    private WatchProvider monoToWatchProviderParser(Mono<ProvidersPage> providersPageMono) {
        ProvidersPage providersPage = providersPageMono.block();
        ProviderWrapper results = providersPage.getResults();
        WatchProviderDTO DE_provider = results.getDe();

        return DE_provider.parseToWatchProvider();
    }

    private Content monoToContentParser(Mono<MovieDTO> monoMovie) {
        MovieDTO movieDTO = monoMovie.block();

        return movieDTO.parseToContent();
    }

    private Integer parseResultToID(Mono<Result> idMono) {
        Result result = idMono.block();

        return result.getId();
    }
}
