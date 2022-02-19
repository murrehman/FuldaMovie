package com.bwma.msc.cms.webClient.content;

public enum MovieFetchCriteria {
    POPULARITY_DESC("popularity.desc"),
    RELEASE_DATE_DESC("release_date.desc"),
    VOTE_COUNT_DESC("vote_count.desc");

    public final String label;

    private MovieFetchCriteria(String label) {
        this.label = label;
    }

    public String getCriteria(){
        return this.label;
    }

}
