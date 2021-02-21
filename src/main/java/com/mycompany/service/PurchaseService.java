package com.mycompany.service;


import com.mycompany.exceptions.ResourceNotFoundException;
import com.mycompany.model.Purchase;
import com.mycompany.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseService {

    final PurchaseRepository repository;

    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    public List<Purchase> findAll(){
        return repository.findAll();
    }

    public Purchase findById(int id) throws ResourceNotFoundException {
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Purchase with id " + id + " is not found"));
    }

    public void addPurchase(Purchase purchase){
        repository.save(purchase);
    }

    public void deletePurchase(Purchase purchase){
        repository.delete(purchase);
    }

    public void updatePurchase(int id, Purchase purchase) throws ResourceNotFoundException {
        Purchase updtPurchase = findById(id);
        updtPurchase.setOrderdate(purchase.getOrderdate());
        updtPurchase.setQuantity(purchase.getQuantity());
        updtPurchase.setOrdersum((purchase.getOrdersum()));
        repository.save(updtPurchase);
    }

    public void updatePurchasePartially(int id, Map<String, Object> changes) throws ResourceNotFoundException {
        Purchase purchase = findById(id);
        changes.forEach((field, value) -> {
            switch(field){
                case "quantity": purchase.setQuantity((Integer) value);
                    break;
                case "orderdate": purchase.setOrderdate((Date) value);
                    break;
                case "ordersum": purchase.setOrdersum((Double) value);
                    break;
            }
        });
        repository.save(purchase);
    }

    public List<String> findUniquePurchaseMonths(){
        return repository.findUniqueMonths();
    }

    public List<Object[]> getCustomerAndShopName(){
        return repository.getCustomerAndShopName();
    }

    public List<Object[]> getPurchaseDetails(){
        return repository.getPurchaseDetails();
    }

}
