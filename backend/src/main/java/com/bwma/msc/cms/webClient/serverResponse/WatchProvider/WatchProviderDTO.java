package com.bwma.msc.cms.webClient.serverResponse.WatchProvider;

import com.bwma.msc.entity.Provider;
import com.bwma.msc.entity.WatchProvider;
import lombok.*;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class WatchProviderDTO {
    private String link;
    private Provider[] flatrate;
    private Provider[] buy;
    private Provider[] rent;

    public WatchProvider parseToWatchProvider() {
        WatchProvider watchProvider = new WatchProvider();

        watchProvider.setLink(link);

        if(flatrate != null){
            watchProvider.setFlatrate(Arrays.asList(flatrate));
        }

        if(buy != null){
            watchProvider.setBuy(Arrays.asList(buy));
        }

        if(rent != null){
            watchProvider.setRent(Arrays.asList(rent));
        }

        return watchProvider;
    }

    @Override
    public String toString() {
        return "WatchProviderDTO{" +
                "link='" + link + '\'' +
                ", flatrate=" + Arrays.toString(flatrate) +
                ", buy=" + Arrays.toString(buy) +
                ", rent=" + Arrays.toString(rent) +
                '}';
    }
}
