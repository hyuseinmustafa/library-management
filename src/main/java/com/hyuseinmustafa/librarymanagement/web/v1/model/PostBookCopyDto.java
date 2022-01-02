package com.hyuseinmustafa.librarymanagement.web.v1.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostBookCopyDto {

    @NotNull
    private Long bookId;

    @NotNull
    private Long locationId;
}
