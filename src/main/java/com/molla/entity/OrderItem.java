package com.molla.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;

    @Column(length = 100 ,nullable = false)
    private String sku;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Double discount;
    @Column(nullable = false)
    private Integer quantity;

    @CreatedDate
    @Column(nullable = false)
    private Date createAt;
    @Temporal(TemporalType.DATE)
    private Date updateAt;
    @Temporal(TemporalType.DATE)
    private Date publishedAt;
    @Column(columnDefinition = "text")
    private String content;
}
