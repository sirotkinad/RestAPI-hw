package com.mycompany.service;

import com.mycompany.exceptions.ResourceNotFoundException;
import com.mycompany.model.Shop;
import com.mycompany.repository.ShopRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ShopService {

    final ShopRepository repository;

    public ShopService(ShopRepository repository) {
        this.repository = repository;
    }

    public List<Shop> findAll(){
        return repository.findAll();
    }

    public Shop findById(int id) throws ResourceNotFoundException {
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Shop with id " + id + " is not found"));
    }

    public void addShop(Shop shop){
        repository.save(shop);
    }

    public void deleteShop(Shop shop){
        repository.delete(shop);
    }

    public void updateShop(int id, Shop shop) throws ResourceNotFoundException {
        Shop updtShop = findById(id);
        updtShop.setName(shop.getName());
        updtShop.setDistrict(shop.getDistrict());
        updtShop.setCommission(shop.getCommission());
        repository.save(updtShop);
    }

    public void updateShopPartially(int id, Map<String, Object> changes) throws ResourceNotFoundException {
        Shop shop = findById(id);
        changes.forEach((field, value) -> {
            switch(field){
                case "name": shop.setName((String) value);
                    break;
                case "district": shop.setDistrict((String) value);
                    break;
                case "commission": shop.setCommission((Double) value);
                    break;
            }
        });
        repository.save(shop);
    }


   public List<String> getShopNamesByDistrict(List<String> districts){
       return repository.getShopNamesByDistrict(districts);
   }

}