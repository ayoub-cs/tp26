package com.example.bookservice.service;

import com.example.bookservice.domain.Book;
import com.example.bookservice.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PricingClient pricingClient;

    public BookService(BookRepository bookRepository, PricingClient pricingClient) {
        this.bookRepository = bookRepository;
        this.pricingClient = pricingClient;
    }

    /**
     * Récupère la liste complète des livres
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Ajoute un nouveau livre après vérification de l’unicité du titre
     */
    public Book add(Book book) {
        bookRepository.findByTitle(book.getTitle()).ifPresent(existing -> {
            throw new IllegalArgumentException("Un livre avec ce titre existe déjà");
        });
        return bookRepository.save(book);
    }

    /**
     * Emprunte un livre de manière transactionnelle avec verrouillage pessimiste
     */
    @Transactional
    public BorrowResult borrowBook(long bookId) {
        // Verrouillage de la ligne en base de données
        Book book = bookRepository.findByIdForUpdate(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Livre non trouvé"));

        // Décrémentation du stock (peut lever une exception si stock = 0)
        book.decrementStock();

        // Récupération du prix depuis le service externe
        double price = pricingClient.getPrice(bookId);

        return new BorrowResult(
                book.getId(),
                book.getTitle(),
                book.getStock(),
                price
        );
    }

    /**
     * DTO de retour pour l’opération d’emprunt
     */
    public record BorrowResult(Long id, String title, int remainingStock, double price) {}
}
