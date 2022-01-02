package com.hyuseinmustafa.librarymanagement.web.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostBookDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank
    private String isbn;

    @NotEmpty
    private Set<Long> authorIds;
}
