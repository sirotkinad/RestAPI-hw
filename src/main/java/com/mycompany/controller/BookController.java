package com.mycompany.controller;


import com.mycompany.exceptions.ResourceNotFoundException;
import com.mycompany.model.Book;
import com.mycompany.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/rest/v3")
public class BookController {

    final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/book")
    public List<Book> getAllBooks(){
        return service.findAll();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
       Book book = service.findById(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/book")
    public Map<String, Boolean> addBook (@Valid @RequestBody Book book, BindingResult result){
        if(result.hasErrors()){
            return giveResponse("Book is not valid", Boolean.FALSE);
        }
        else{
            service.addBook(book);
            return giveResponse("Book is added", Boolean.TRUE);
        }
    }

    @DeleteMapping("/book/{id}")
    public Map<String, Boolean> deleteBookById(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
        Book book = service.findById(id);
        service.deleteBook(book);
        return giveResponse("Book is deleted", Boolean.TRUE);
    }

    @PutMapping("/book/{id}")
    public Map<String, Boolean> updateBook(@PathVariable(value = "id") int id,
                                           @Valid @RequestBody Book book, BindingResult result)
            throws ResourceNotFoundException {
        if(result.hasErrors()){
            return giveResponse("Book is not valid", Boolean.FALSE);
        }
        else {
            service.updateBook(id, book);
            return giveResponse("Book is updated", Boolean.TRUE);
        }
    }

    @PatchMapping("/book/{id}")
    public Map<String, Boolean> updateBookPartially(@PathVariable(value = "id") int id, @RequestBody Map<String, Object> changes)
            throws ResourceNotFoundException {
        if(changes.containsValue(null)){
            return giveResponse("Customer is not valid", Boolean.FALSE);
        }
        else {
            service.updateBookPartially(id, changes);
            return giveResponse("Book is partially updated", Boolean.TRUE);
        }
    }

    @GetMapping("/book/names")
    public List<String> getUniqueBookNames(){
        return service.getUniqueBookNames();
    }

    @GetMapping("/book/prices")
    public List<Double> getUniqueBookPrices(){
        return service.getUniqueBookPrices();
    }

    @GetMapping("/book/nameprice")
    public List<Object[]> getSortedNamesAndPrices (){
        return service.getSortedNamesAndPrices();
    }

    public Map<String, Boolean> giveResponse(String message, Boolean result){
        Map<String, Boolean> response = new HashMap();
        response.put(message, result);
        return response;
    }
}
