package com.bwma.msc.cms.webClient.content;

import com.bwma.msc.entity.*;
import com.bwma.msc.exception.InvalidIDException;
import com.bwma.msc.cms.webClient.GenreWebClient;
import com.bwma.msc.cms.webClient.serverResponse.Result;
import com.bwma.msc.cms.webClient.serverResponse.ResultsPage;
import com.bwma.msc.cms.webClient.serverResponse.TV.SeasonDTO;
import com.bwma.msc.cms.webClient.serverResponse.TV.TVDTO;
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
public class TVWebClient implements ContentWebClient{
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
        EnumSet<TVFetchCriteria> fetchCriterias = EnumSet.allOf(TVFetchCriteria.class);
        Set<Integer> IDBuffer = new HashSet<>();

        for (TVFetchCriteria criteria : fetchCriterias) {
            List<Integer> IDList = this.discoverTVIDs(criteria);

            IDBuffer.addAll(IDList);
        }

        return IDBuffer;
    }

    private List<Integer> discoverTVIDs(TVFetchCriteria fetchCriteria) {
        List<Integer> criteriaBuffer = new ArrayList<>();

        for (int i = 1; i <= MAX_PAGES; i++) {
            List<Integer> IDList = this.discoverTVIDs(fetchCriteria, i);

            criteriaBuffer.addAll(IDList);
        }

        return criteriaBuffer;
    }

    private List<Integer> discoverTVIDs(TVFetchCriteria fetchCriteria, Integer TVPage) {
        logger.info("Discovering TV -> Sort by: <" + fetchCriteria + "> Page: <" + TVPage + ">");

        Mono<ResultsPage> resultsPageMono = this.fetchDiscoverTV(fetchCriteria, TVPage);

        List<Integer> IDList = this.parseResultsPageToIDList(resultsPageMono);

        return IDList;
    }

    // Get TV Details - Task Step 2 & Searching
    // Error control is added because the method can be called due to a user interaction.
    public Content getByID(Integer id) throws InvalidIDException {
        if (id == null || id < 0) {
            throw new InvalidIDException("ID must be positive");
        } else {
            try {
                logger.info("Fetching TV Details: <" + id + ">");

                Mono<TVDTO> monoTV = this.fetchTVDetails(id);

                return this.monoToContentParser(monoTV);
            } catch (WebClientResponseException exception) {
                return null;
            }
        }
    }

    // Watch providers - Task Step 3
    public WatchProvider getWatchProviders(Content tv) {
        Mono<ProvidersPage> monoProviders = this.fetchWatchProviders(tv.getId());

        ProvidersPage wrapper = monoProviders.block();

        WatchProvider provider = monoToWatchProviderParser(monoProviders);

        provider.setContent(tv);
        provider.setCountry(new Country("DE", "Germany"));

        return provider;
    }

    // Seasons - Task Step 4
    public Map<Season,List<Episode>> getSeasonsAndEpisodes(Content tv){
        int numberOfSeasons = tv.getNumberOfSeasons();
        Map<Season,List<Episode>> seasonEpisodesBuffer = new HashMap<>();

        for (int i = 1; i <= numberOfSeasons; i++) {
            Map<Season,List<Episode>> seasonEpisodesMap =
                    this.getSeasonAndEpisodes(tv,i);

            seasonEpisodesBuffer.putAll(seasonEpisodesMap);
        }

        return seasonEpisodesBuffer;
    }

    private Map<Season,List<Episode>> getSeasonAndEpisodes(Content tv, int seasonNumber){
        Mono<SeasonDTO> monoSeasonDTO = this.fetchSeason(tv.getId(), seasonNumber);
        Map<Season,List<Episode>> seasonEpisodesMap = this.parseMonoToSeasonMap(monoSeasonDTO);

        seasonEpisodesMap.keySet().forEach(season -> {
            season.setTv(tv);
        });

        return seasonEpisodesMap;
    }

    public List<Integer> discoverChanges(){
        Mono<ResultsPage> resultsPageMono = this.fetchTvChanges();

        return parseResultsPageToIDList(resultsPageMono);
    }

    public List<Integer> getLatestAdded(){
        List<Integer> idList = new LinkedList<>();

        Mono<Result> idMono = this.fetchLatestAdded();
        Integer id = parseResultToID(idMono);
        idList.add(id);
        return idList;
    }

    // Tools & AUX
    // Fetchers
    private Mono<ResultsPage> fetchDiscoverTV(TVFetchCriteria fetchCriteria, Integer MoviePage) {
        String uri = "/discover/tv?api_key=" + this.apiKey + "&sort_by=" + fetchCriteria.getCriteria() + "&page=" + MoviePage;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(ResultsPage.class);
    }

    private Mono<TVDTO> fetchTVDetails(Integer tv_id) {
        String uri = "/tv/" + tv_id + "?api_key=" + this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(TVDTO.class);
    }

    private Mono<ProvidersPage> fetchWatchProviders(Integer tv_id) {
        String uri = "/tv/" + tv_id + "/watch/providers?api_key=" + this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(ProvidersPage.class);
    }

    private Mono<SeasonDTO> fetchSeason(Integer tv_id, Integer seasonNumber){
        String uri = "/tv/" + tv_id + "/season/"+ seasonNumber+"?api_key=" + this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(SeasonDTO.class);
    }

    private Mono<Result> fetchLatestAdded() {
        String uri = "/tv/latest?api_key=" + this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(Result.class);
    }

    private Mono<ResultsPage> fetchTvChanges(){
        String uri = "/tv/changes?api_key"+this.apiKey;

        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(ResultsPage.class);
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

    private Content monoToContentParser(Mono<TVDTO> monoTV) {
        TVDTO TVDTO = monoTV.block();

        return TVDTO.parseToContent();
    }

    private Map<Season,List<Episode>> parseMonoToSeasonMap(Mono<SeasonDTO> monoSeason){
        SeasonDTO seasonDTO = monoSeason.block();
        Map<Season,List<Episode>> seasonEpisodesMap = new HashMap<>();

        Season season = seasonDTO.getSeason();
        List<Episode> episodeList = seasonDTO.getSeasonEpisodes();

        seasonEpisodesMap.put(season,episodeList);

        return seasonEpisodesMap;
    }

    private Integer parseResultToID(Mono<Result> idMono) {
        Result result = idMono.block();

        return result.getId();
    }
}
