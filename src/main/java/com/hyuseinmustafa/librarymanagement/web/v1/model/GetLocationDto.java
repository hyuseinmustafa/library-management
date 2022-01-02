package com.hyuseinmustafa.librarymanagement.web.v1.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLocationDto {
    private Long id;
    private Long roomNumber;
    private Long shelfNumber;
}
