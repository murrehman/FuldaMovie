package com.bwma.msc.service.entityService;

import com.bwma.msc.entity.Company;
import com.bwma.msc.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository repository;

    @Autowired
    public CompanyService(CompanyRepository repository){
        this.repository = repository;
    }

    //Create
    //Update
    public void createOrUpdate(Company Company){
        repository.save(Company);
    }

    //Read
    public Company findById(Integer id){
        Optional<Company> Company = repository.findById(id);

        return Company.orElse(null);
    }

    //Delete
    public void deleteById (Integer id){
        repository.deleteById(id);
    }
}
