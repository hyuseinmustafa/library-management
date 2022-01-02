package com.hyuseinmustafa.librarymanagement.web.v1.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookDto {
    private Long id;
    private String name;
    private String isbn;
    private List<GetAuthorDto> authors;
}
