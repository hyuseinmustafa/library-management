package com.hyuseinmustafa.librarymanagement.web.v1.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBookCopyDto {
    private Long id;
    private Boolean available;
    private GetBookDto book;
    private GetLocationDto location;
    private GetCustomerDto customer;
}
