package com.example.bookservice.web;

import com.example.bookservice.domain.Book;
import com.example.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retourne la liste de tous les livres
     */
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.all();
    }

    /**
     * Crée un nouveau livre
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    /**
     * Emprunte un livre à partir de son identifiant
     */
    @PostMapping("/{bookId}/borrow")
    public BookService.BorrowResult borrowBook(@PathVariable("bookId") long bookId) {
        return bookService.borrow(bookId);
    }
}
