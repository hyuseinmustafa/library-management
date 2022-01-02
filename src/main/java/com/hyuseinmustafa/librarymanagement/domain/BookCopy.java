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

    @Column(nullable = false)
    private Boolean available = false;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Book book;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Location location;

    @ManyToOne
    private Customer customer;
}
