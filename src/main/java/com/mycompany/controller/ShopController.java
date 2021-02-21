package com.mycompany.controller;


import com.mycompany.exceptions.ResourceNotFoundException;
import com.mycompany.model.Shop;
import com.mycompany.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/v2")
public class ShopController {

    final ShopService service;

    public ShopController(ShopService service) {
        this.service = service;
    }

    @GetMapping("/shop")
    public List<Shop> getAllShops(){
        return service.findAll();
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        Shop shop = service.findById(id);
        return ResponseEntity.ok().body(shop);
    }

    @PostMapping("/shop")
    public Map<String, Boolean> addShop(@Valid @RequestBody Shop shop, BindingResult result){
        if(result.hasErrors()){
            return giveResponse("Shop is not valid", Boolean.FALSE);
        }
        else{
            service.addShop(shop);
            return giveResponse("Shop is added", Boolean.TRUE);
        }
    }

    @DeleteMapping("/shop/{id}")
    public Map<String, Boolean> deleteShopById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
       Shop shop = service.findById(id);
        service.deleteShop(shop);
       return giveResponse("Shop is deleted", Boolean.TRUE);
    }

    @PutMapping("/shop/{id}")
    public Map<String, Boolean> updateShop(@PathVariable(value = "id") int id,
                                           @Valid @RequestBody Shop shop, BindingResult result)
            throws ResourceNotFoundException {
        if(result.hasErrors()){
            return giveResponse("Shop is not valid", Boolean.FALSE);
        }
        else{
            service.updateShop(id, shop);
            return giveResponse("Shop is updated", Boolean.TRUE);
        }
    }

    @PatchMapping("/shop/{id}")
    public Map<String, Boolean> updateShopPartially(@PathVariable(value = "id") int id, @RequestBody Map<String, Object> changes)
            throws ResourceNotFoundException {
        if(changes.containsValue(null)){
            return giveResponse("Customer is not valid", Boolean.FALSE);
        }
        else{
            service.updateShopPartially(id, changes);
            return giveResponse("Shop is partially updated", Boolean.TRUE);
        }
    }

    @GetMapping("/shop/names")
    public List<String> getShopNamesByDistrict(@RequestParam List<String> districts) throws ResourceNotFoundException {
        return service.getShopNamesByDistrict(districts);
    }

    public Map<String, Boolean> giveResponse(String message, Boolean result){
        Map<String, Boolean> response = new HashMap();
        response.put(message, result);
        return response;
    }
}
