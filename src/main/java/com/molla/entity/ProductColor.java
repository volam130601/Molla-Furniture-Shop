package com.molla.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "p_color")
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_color_id")
    private Long id;
    private String code;
    private String name;
}
