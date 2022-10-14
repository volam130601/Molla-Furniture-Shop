package com.molla.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "product_review")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Product product;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductReview productReview;

    @OneToMany(mappedBy = "productReview")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProductReview> productReviewList;

    private String title;
    private int rating;
    private boolean published;

    @CreatedDate
    private Date createdAt;
    private Date publishedAt;
    @Column(columnDefinition = "TEXT")
    private String content;

}
