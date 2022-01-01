package com.hyuseinmustafa.librarymanagement.repository;

import com.hyuseinmustafa.librarymanagement.domain.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
