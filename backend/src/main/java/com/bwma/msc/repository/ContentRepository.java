package com.bwma.msc.repository;

import com.bwma.msc.entity.Content;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content,Integer> {

    List<Content> getContentByOrderByPopularityDesc(Pageable pageable);

    List<Content> getContentByOrderByVoteCountDesc(Pageable pageable);

    List<Content> getContentByTitleContainingIgnoreCase(String title);
}
