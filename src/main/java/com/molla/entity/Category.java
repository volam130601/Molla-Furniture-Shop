package com.molla.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private String name;

    @ManyToMany(mappedBy = "categories")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Product> products;
}
