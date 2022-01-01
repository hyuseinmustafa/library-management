package com.hyuseinmustafa.librarymanagement.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long roomNumber;
    private Long shelfNumber;

    @OneToMany(mappedBy = "location")
    private Set<BookCopy> bookCopies;
}
