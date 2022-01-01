package com.hyuseinmustafa.librarymanagement.repository;

import com.hyuseinmustafa.librarymanagement.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
