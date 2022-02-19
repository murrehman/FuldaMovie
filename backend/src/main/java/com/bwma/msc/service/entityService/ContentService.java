package com.bwma.msc.service.entityService;

import com.bwma.msc.entity.Content;
import com.bwma.msc.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ContentService {
    private final ContentRepository repository;

    @Autowired
    public ContentService(ContentRepository repository){
        this.repository = repository;
    }

    //Create
    //Update
    public void createOrUpdate(Content Content){
        repository.save(Content);
    }

    //Read
    public Optional<Content> findById(Integer id){
        return repository.findById(id);
    }

    public List<Content> findContentByTitle(String title){
        return repository.getContentByTitleContainingIgnoreCase(title);
    }
}
