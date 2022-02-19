package com.bwma.msc.cms.webClient.serverResponse;

import com.bwma.msc.entity.Genre;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Genres {
    private Genre[] genres;
}
