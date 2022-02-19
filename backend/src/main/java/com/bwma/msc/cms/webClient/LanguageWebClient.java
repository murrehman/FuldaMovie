package com.bwma.msc.cms.webClient;

import com.bwma.msc.entity.Language;
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
public class LanguageWebClient {

    @Value("${tmdb.api.baseUrl}")
    private String baseUrl;
    @Value("${tmdb.api.secret}")
    private String apiKey;
    private WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger( LanguageWebClient.class );

    @PostConstruct
    public void init(){
        this.webClient = WebClient.builder()
                .baseUrl(this.baseUrl)
                .build();
    }

    public List<Language> getLanguageList(){
        Mono<Language[]> monoLanguage = this.fetchLanguageList();

        return monoToListParser(monoLanguage);
    }

    private Mono<Language[]> fetchLanguageList(){
        String uri = "/configuration/languages?api_key="+this.apiKey;
        return this.webClient.get().uri(uri).retrieve()
                .bodyToMono(Language[].class);
    }

    private List<Language> monoToListParser(Mono<Language[]> monoLanguage){
        Language[] languagesArray = monoLanguage.block();

        return Arrays.asList(languagesArray);
    }

}
