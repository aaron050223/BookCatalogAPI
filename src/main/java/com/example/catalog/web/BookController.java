package com.example.catalog.web;

import com.example.catalog.dto.BookRequestDTO;
import com.example.catalog.dto.BookResponseDTO;
import com.example.catalog.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/books")
public class BookController {

    private final Map<Long, Book> books = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @GetMapping
    public List<BookResponseDTO> list() {
        return books.values().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public BookResponseDTO getOne(@PathVariable("id") Long id) {
        Book book = books.get(id);
        if (book == null) {
            return null;
        }
        return toResponseDTO(book);
    }

    @PostMapping
    public BookResponseDTO create(@RequestBody BookRequestDTO request) {
        Long newId = idGenerator.getAndIncrement();
        Book book = new Book(newId, request.isbn(), request.title(), request.author(), request.year());
        books.put(newId, book);
        return toResponseDTO(book);
    }

    @PutMapping("/{id}")
    public BookResponseDTO update(@PathVariable("id") Long id, @RequestBody BookRequestDTO request) {
        Book book = books.get(id);
        if (book != null) {
            book.setIsbn(request.isbn());
            book.setTitle(request.title());
            book.setAuthor(request.author());
            book.setYear(request.year());
            return toResponseDTO(book);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        books.remove(id);
    }

    private BookResponseDTO toResponseDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear()
        );
    }
}