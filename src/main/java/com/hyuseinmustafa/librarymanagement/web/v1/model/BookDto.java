package com.hyuseinmustafa.librarymanagement.web.v1.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String name;
    private String isbn;
}
