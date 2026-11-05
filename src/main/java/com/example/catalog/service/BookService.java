package com.example.catalog.service;

import com.example.catalog.dto.BookRequestDTO;
import com.example.catalog.dto.BookResponseDTO;
import com.example.catalog.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final Map<Long, Book> books = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<BookResponseDTO> list() {
        return books.values().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public BookResponseDTO get(Long id) {
        Book book = books.get(id);
        if (book == null) {
            return null; // En la Entrega 5 lanzaremos una excepción propia aquí
        }
        return toResponseDTO(book);
    }

    public BookResponseDTO create(BookRequestDTO request) {
        Long newId = idGenerator.getAndIncrement();
        Book book = new Book(newId, request.isbn(), request.title(), request.author(), request.year());
        books.put(newId, book);
        return toResponseDTO(book);
    }

    public BookResponseDTO update(Long id, BookRequestDTO request) {
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

    public void delete(Long id) {
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