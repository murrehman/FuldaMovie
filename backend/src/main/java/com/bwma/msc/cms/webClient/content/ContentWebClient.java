package com.bwma.msc.cms.webClient.content;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.WatchProvider;
import com.bwma.msc.exception.InvalidIDException;

import java.util.Collection;

public interface ContentWebClient {

    Collection<Integer> discoverIDs();

    Content getByID(Integer id) throws InvalidIDException;

    WatchProvider getWatchProviders(Content content);

    Collection<Integer> discoverChanges();

    Collection<Integer> getLatestAdded();
}
