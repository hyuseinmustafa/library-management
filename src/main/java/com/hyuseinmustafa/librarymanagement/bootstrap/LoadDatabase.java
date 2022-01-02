package com.hyuseinmustafa.librarymanagement.bootstrap;

import com.hyuseinmustafa.librarymanagement.domain.*;
import com.hyuseinmustafa.librarymanagement.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Setter
@Getter
@RequiredArgsConstructor
@Component
public class LoadDatabase implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;

    @Override
    public void run(String... args) throws Exception {
        loadDatabase();
    }

    private void loadDatabase() {
        Author author1 = Author.builder().name("Author 1").books(new HashSet<>()).build();
        Author author2 = Author.builder().name("Author 2").books(new HashSet<>()).build();

        Book book1 = Book.builder().name("Book 1").isbn("978-3-16-148410-0").authors(new HashSet<>()).build();
        Book book2 = Book.builder().name("Book 2").isbn("978-3-16-128430-0").authors(new HashSet<>()).build();
        author1.getBooks().add(book1);
        book1.getAuthors().add(author1);
        author2.getBooks().add(book2);
        book2.getAuthors().add(author2);

        authorRepository.save(author1);
        authorRepository.save(author2);
        bookRepository.save(book1);
        bookRepository.save(book2);

        Customer customer1 = Customer.builder().name("Name 1").address("Address 1").build();
        Customer customer2 = Customer.builder().name("Name 2").address("Address 2").build();
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        Location location1 = Location.builder().roomNumber(1L).shelfNumber(1L).build();
        Location location2 = Location.builder().roomNumber(1L).shelfNumber(2L).build();
        locationRepository.save(location1);
        locationRepository.save(location2);

        BookCopy bookCopy1 = BookCopy.builder().
                book(book1).customer(customer1).location(location1).available(true).build();
        BookCopy bookCopy2 = BookCopy.builder().
                book(book2).customer(customer2).location(location2).available(true).build();
        bookCopyRepository.save(bookCopy1);
        bookCopyRepository.save(bookCopy2);
    }
}
