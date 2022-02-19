package com.bwma.msc.cms.webClient;

import com.bwma.msc.entity.Country;
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
public class CountryWebClient {
    @Value("${tmdb.api.baseUrl}")
    private String baseUrl;
    @Value("${tmdb.api.secret}")
    private String apiKey;
    private WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger( CountryWebClient.class );

    @PostConstruct
    public void init(){
        this.webClient = WebClient.builder()
                .baseUrl(this.baseUrl)
                .build();
    }

    public List<Country> getCountryList(){
        Mono<Country[]> monoCountries = this.fetchCountryList();

        return monoToListParser(monoCountries);
    }







    private Mono<Country[]> fetchCountryList(){
        String uri = "/configuration/countries?api_key="+this.apiKey;
        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(Country[].class);
    }

    private List<Country> monoToListParser(Mono<Country[]> monoCountries){
        Country[] countriesArray = monoCountries.block();

        return Arrays.asList(countriesArray);
    }
}
