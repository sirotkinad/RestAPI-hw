package com.mycompany.controller;


import com.mycompany.exceptions.ResourceNotFoundException;
import com.mycompany.model.Customer;
import com.mycompany.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/rest/v1")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/customer")
    public List<Customer> getAll() {
        return service.findAll();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        Customer customer = service.findById(id);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("/customer")
    public Map<String, Boolean> addCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        if(result.hasErrors()){
            return giveResponse("Customer is not valid", Boolean.FALSE);
        }
        else {
            service.addCustomer(customer);
            return giveResponse("Customer is added", Boolean.TRUE);
        }
    }

    @DeleteMapping("/customer/{id}")
    public Map<String, Boolean> deleteCustomerById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        Customer customer = service.findById(id);
        service.deleteCustomer(customer);
        return giveResponse("Customer is deleted", Boolean.TRUE);
    }

    @PatchMapping("/customer/{id}")
    public Map<String, Boolean> updateCustomerPartially(@PathVariable(value = "id") int id,
                                                            @RequestBody Map<String, Object> changes)
            throws ResourceNotFoundException {
        if(changes.containsValue(null)){
            return giveResponse("Customer is not valid", Boolean.FALSE);
        }
        else {
            service.updateCustomerPartially(id, changes);
            return giveResponse("Customer is partially updated", Boolean.TRUE);
        }
    }

    @PutMapping("/customer/{id}")
    public Map<String, Boolean> updateCustomer(@PathVariable(value = "id") int id,
                                               @Valid @RequestBody Customer customer, BindingResult result)
            throws ResourceNotFoundException {
        if(result.hasErrors()){
            return giveResponse("Customer is not valid", Boolean.FALSE);
        }
        else{
            service.updateCustomer(id, customer);
            return giveResponse("Customer is updated", Boolean.TRUE);
        }
    }

    @GetMapping("customer/districts")
    public List<String> getUniqueDistricts(){
        return service.getUniqueDistricts();
    }

    @GetMapping("customer/namesale/{district}")
    public List<Object[]> findNameAndSaleByDistrict(@PathVariable(value = "district") String district){
        return service.findNameAndSaleByDistrict(district);
    }

    public Map<String, Boolean> giveResponse(String message, Boolean result){
        Map<String, Boolean> response = new HashMap();
        response.put(message, result);
        return response;
    }

}