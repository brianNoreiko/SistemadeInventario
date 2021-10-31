package com.example.demo.service;

import com.example.demo.models.Provider;
import com.example.demo.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ProviderService {

    @Autowired
    ProviderRepository providerRepository;

    public List<Provider> getAll() {
        return providerRepository.findAll();
    }

    public Provider add(Provider provider){
        return providerRepository.save(provider);
    }
    public Provider getById(Integer id){
        return providerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void delete(Integer id){
        Provider provider = getById(id);
        if(provider != null){
            providerRepository.deleteById(id);
        }else{
            System.out.println("El proveedor no existe");
        }
    }
}
