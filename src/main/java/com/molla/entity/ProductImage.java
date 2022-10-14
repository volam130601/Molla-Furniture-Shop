package com.molla.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "p_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_image_id")
    private Long id;
    @Column(name = "p_image_small")
    private String image_small;
    @Column(name = "p_image_medium")
    private String image_medium;
    @Column(name = "p_image_large")
    private String image_large;

    @ManyToMany(mappedBy = "productImages")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Product> products;

    @OneToOne
    @JoinColumn(name = "p_color_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductColor productColor;
}
