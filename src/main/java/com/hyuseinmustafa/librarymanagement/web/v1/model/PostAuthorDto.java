package com.hyuseinmustafa.librarymanagement.web.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAuthorDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
}
