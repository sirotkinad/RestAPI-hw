package com.mycompany.controller;

import com.mycompany.exceptions.ResourceNotFoundException;
import com.mycompany.model.Purchase;

import com.mycompany.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/rest/v4")
public class PurchaseController {

    final PurchaseService service;

    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @GetMapping("/purchase")
    public List<Purchase> getAllPurchase(){
        return service.findAll();
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        Purchase purchase = service.findById(id);
        return ResponseEntity.ok().body(purchase);
    }

    @PostMapping("/purchase")
    public Map<String, Boolean> addPurchase(@Valid @RequestBody Purchase purchase, BindingResult result){
        if(result.hasErrors()){
            return giveResponse("Purchase is not valid", Boolean.FALSE);
        }
        else{
            service.addPurchase(purchase);
            return giveResponse("Purchase is added", Boolean.TRUE);
        }
    }

    @DeleteMapping("/purchase/{id}")
    public Map<String, Boolean> deletePurchaseById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        Purchase purchase = service.findById(id);
        service.deletePurchase(purchase);
        return giveResponse("Purchase is deleted", Boolean.TRUE);
    }

    @PutMapping("/purchase/{id}")
    public Map<String, Boolean> updatePurchase(@PathVariable(value = "id") int id,
                                               @Valid @RequestBody Purchase purchase, BindingResult result)
            throws ResourceNotFoundException {
        if(result.hasErrors()){
            return giveResponse("Purchase is not valid", Boolean.FALSE);
        }
        else {
            service.updatePurchase(id, purchase);
            return giveResponse("Purchase is updated", Boolean.TRUE);
        }
    }

    @PatchMapping("/purchase/{id}")
    public Map<String, Boolean> updatePurchasePartially(@PathVariable(value = "id") int id,
                                                        @RequestBody Map<String, Object> changes)
            throws ResourceNotFoundException {
        if(changes.containsValue(null)){
            return giveResponse("Customer is not valid", Boolean.FALSE);
        }
        else {
            service.updatePurchasePartially(id, changes);
            return giveResponse("Purchase is partially updated", Boolean.TRUE);
        }
    }

    @GetMapping("/purchase/months")
    public List<String> getPurchaseMonths(){
        return service.findUniquePurchaseMonths();
    }

    @GetMapping("/purchase/info")
    public List<Object[]> getCustomerAndShopName(){
        return service.getCustomerAndShopName();
    }

    @GetMapping("/purchase/details")
    public List<Object[]> getPurchaseDetails(){
        return service.getPurchaseDetails();
    }

    public Map<String, Boolean> giveResponse(String message, Boolean result){
        Map<String, Boolean> response = new HashMap();
        response.put(message, result);
        return response;
    }

}