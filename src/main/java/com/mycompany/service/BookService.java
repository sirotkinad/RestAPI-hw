package com.mycompany.service;

import com.mycompany.exceptions.ResourceNotFoundException;
import com.mycompany.model.Book;
import com.mycompany.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class BookService {

    final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(){
        return repository.findAll();
    }

    public Book findById(int id) throws ResourceNotFoundException {
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " is not found"));
    }

    public void addBook(Book book){
        repository.save(book);
    }

    public void deleteBook(Book book){
        repository.delete(book);
    }

    public void updateBook(int id, Book book) throws ResourceNotFoundException {
        Book updtBook = findById(id);
        updtBook.setName(book.getName());
        updtBook.setStorage(book.getStorage());
        updtBook.setCount(book.getCount());
        updtBook.setPrice(book.getPrice());
        repository.save(updtBook);
    }

    public void updateBookPartially(int id, Map<String, Object> changes) throws ResourceNotFoundException {
        Book book = findById(id);
        changes.forEach((field, value) -> {
            switch(field){
                case "name": book.setName((String) value);
                    break;
                case "storage": book.setStorage((String) value);
                    break;
                case "price": book.setPrice((Double) value);
                    break;
                case "count": book.setCount((Integer) value);
                    break;
            }
        });
        repository.save(book);
    }

    public List<String> getUniqueBookNames(){
        return repository.findUniqueNames();
    }

    public List<Double> getUniqueBookPrices(){
        return repository.findUniquePrices();
    }

    public List<Object[]> getSortedNamesAndPrices(){
        return repository.findSortedNamesAndPrices();
    }
}