package com.bwma.msc.cms.webClient.serverResponse.WatchProvider;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProvidersPage {
    private int id;
    private ProviderWrapper results;

    @Override
    public String toString() {
        return "ProvidersPage{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
