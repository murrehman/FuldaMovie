package com.bwma.msc.cms.webClient;

import com.bwma.msc.entity.Genre;
import com.bwma.msc.cms.webClient.serverResponse.Genres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class GenreWebClient {

    @Value("${tmdb.api.baseUrl}")
    private String baseUrl;
    @Value("${tmdb.api.secret}")
    private String apiKey;
    private WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger( GenreWebClient.class );

    @PostConstruct
    public void init(){
        this.webClient = WebClient.builder()
                .baseUrl(this.baseUrl)
                .build();
    }


    public List<Genre> getMovieGenreList(){
        Mono<Genres> monoGenres = this.fetchMovieGenreList();

        return monoToListParser(monoGenres);
    }

    public List<Genre> getTVGenreList(){
        Mono<Genres> monoGenres = this.fetchTVGenreList();

        return monoToListParser(monoGenres);
    }

    private Mono<Genres> fetchMovieGenreList(){
        String uri = "/genre/movie/list?api_key="+this.apiKey;
        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(Genres.class);
    }

    private Mono<Genres> fetchTVGenreList(){
        String uri = "/genre/tv/list?api_key="+this.apiKey;
        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(Genres.class);
    }

    private List<Genre> monoToListParser(Mono<Genres> monoGenres){
        Genres genresWrapper = monoGenres.block();
        Genre[] genreArray = genresWrapper.getGenres();

        return Arrays.asList(genreArray);
    }
}
