package com.hyuseinmustafa.librarymanagement.web.v1.model;

import com.hyuseinmustafa.librarymanagement.domain.BookCopy;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerDto {
    private Long id;
    private String name;
    private String address;
    private Set<BookCopy> bookCopies;
}
