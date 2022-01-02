package com.hyuseinmustafa.librarymanagement.web.v1.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLocationDto {

    @NotNull
    private Long roomNumber;

    @NotNull
    private Long shelfNumber;
}
