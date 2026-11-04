package com.example.catalog.web;

import com.example.catalog.dto.BookRequestDTO;
import com.example.catalog.dto.BookResponseDTO;
import com.example.catalog.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/books")
public class BookController {

    private final Map<Long, Book> books = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> list() {
        List<BookResponseDTO> list = books.values().stream()
                .map(this::toResponseDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getOne(@PathVariable("id") Long id) {
        Book book = books.get(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponseDTO(book));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO request) {
        Long newId = idGenerator.getAndIncrement();
        Book book = new Book(newId, request.isbn(), request.title(), request.author(), request.year());
        books.put(newId, book);

        BookResponseDTO response = toResponseDTO(book);
        URI location = URI.create("/books/" + newId);

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(@PathVariable("id") Long id, @RequestBody BookRequestDTO request) {
        Book book = books.get(id);
        if (book != null) {
            book.setIsbn(request.isbn());
            book.setTitle(request.title());
            book.setAuthor(request.author());
            book.setYear(request.year());
            return ResponseEntity.ok(toResponseDTO(book));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        books.remove(id);
        return ResponseEntity.noContent().build();
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