package com.bwma.msc.cms.webClient.serverResponse.WatchProvider;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProviderWrapper {
    @JsonProperty("DE")
    private WatchProviderDTO de;

    @Override
    public String toString() {
        return "ProviderWrapper{" +
                "DE=" + de +
                '}';
    }
}
