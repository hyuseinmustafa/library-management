package com.hyuseinmustafa.librarymanagement.domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean available = false;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Location location;

    @ManyToOne
    private Customer customer;
}
