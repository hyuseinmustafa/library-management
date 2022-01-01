package com.hyuseinmustafa.librarymanagement.repository;

import com.hyuseinmustafa.librarymanagement.domain.BookCopy;
import org.springframework.data.repository.CrudRepository;

public interface BookCopyRepository extends CrudRepository<BookCopy, Long> {
}
