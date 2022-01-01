package com.hyuseinmustafa.librarymanagement.repository;

import com.hyuseinmustafa.librarymanagement.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
