package com.bwma.msc.cms.webClient.serverResponse;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ResultsPage {
    private Integer total_pages;
    private Integer page;
    private Result[] results;
}
