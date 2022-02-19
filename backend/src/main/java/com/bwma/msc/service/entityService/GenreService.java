package com.bwma.msc.service.entityService;

import com.bwma.msc.entity.Genre;
import com.bwma.msc.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository repository;

    @Autowired
    public GenreService(GenreRepository repository){
        this.repository = repository;
    }

    //Create
    //Update
    public void createOrUpdate(Genre genre){
        repository.save(genre);
    }


    //Read
    public List<Genre> findAll(){
        return repository.findAll();
    }

    public Genre findById(Integer id){
        Optional<Genre> genre = repository.findById(id);

        return genre.orElse(null);
    }

    //Delete
    public void deleteById (Integer id){
        repository.deleteById(id);
    }
}
