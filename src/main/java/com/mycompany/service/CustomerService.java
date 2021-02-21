package com.mycompany.service;

import com.mycompany.exceptions.ResourceNotFoundException;
import com.mycompany.model.Customer;
import com.mycompany.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class CustomerService {

    final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findAll(){
        return repository.findAll();
    }

    public Customer findById(int id) throws ResourceNotFoundException {
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " is not found"));
    }

    public void addCustomer(Customer customer){
        repository.save(customer);
    }

    public void deleteCustomer(Customer customer){
        repository.delete(customer);
    }

    public void updateCustomer(int id, Customer customer) throws ResourceNotFoundException {
        Customer updtCustomer = findById(id);
        updtCustomer.setSurname(customer.getSurname());
        updtCustomer.setDistrict(customer.getDistrict());
        updtCustomer.setSale(customer.getSale());
        repository.save(updtCustomer);
    }

    public void updateCustomerPartially(int id, Map<String, Object> changes) throws ResourceNotFoundException {
        Customer customer = findById(id);
        changes.forEach((field, value) -> {
            switch(field){
                case "surname": customer.setSurname((String) value);
                    break;
                case "district": customer.setDistrict((String) value);
                    break;
                case "sale": customer.setSale((Integer) value);
                    break;
            }
        });
        repository.save(customer);
    }

    public List<String> getUniqueDistricts(){
        return repository.getUniqueDistricts();
    }

    public List<Object[]> findNameAndSaleByDistrict(String district){
        return repository.findNameAndSaleByDistrict(district);
    }


}
