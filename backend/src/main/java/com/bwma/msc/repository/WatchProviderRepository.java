package com.bwma.msc.repository;

import com.bwma.msc.entity.Content;
import com.bwma.msc.entity.WatchProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchProviderRepository extends JpaRepository<WatchProvider,String> {
    WatchProvider getWatchProviderByContent(Content content);
}
