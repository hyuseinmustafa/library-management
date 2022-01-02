package com.hyuseinmustafa.librarymanagement.web.v1.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAuthorDto {
    private Long id;
    private String name;
}
