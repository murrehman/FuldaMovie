package com.bwma.msc.cms.webClient.content;

public enum TVFetchCriteria {
    POPULARITY_DESC("popularity.desc"),
    FIRST_AIR_DATE_DESC("first_air_date.desc"),
    VOTE_AVERAGE_DESC("vote_average.desc");

    public final String label;

    private TVFetchCriteria(String label) {
        this.label = label;
    }

    public String getCriteria(){
        return this.label;
    }

}
