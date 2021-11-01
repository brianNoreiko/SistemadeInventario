package com.example.demo.controller;


import com.example.demo.models.Provider;
import com.example.demo.models.responses.Response;
import com.example.demo.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.EntityResponse;
import utils.EntityURLBuilder;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.POST,RequestMethod.GET})
@RestController
@RequestMapping("/providers")
public class ProviderController {

    final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<Response> addProvider(@RequestBody Provider provider) {
        Provider providerAdded = providerService.add(provider);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .location(EntityURLBuilder.buildURL("providers",providerAdded.getId()))
                .body(EntityResponse.messageResponse("Provider created successfully"));
    }

    @GetMapping
    public List<Provider> getAllProviders(){
        return providerService.getAll();
    }

    @GetMapping("/{id}")
        public ResponseEntity<Provider> getProviderById(@PathVariable(value = "id") Integer id){
            Provider provider = providerService.getById(id);
            return ResponseEntity.ok(provider);
        }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable(value = "id") Integer id){
        providerService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
