package com.molla.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @Column(length = 75 ,nullable = false)
    private String title;

    //Product_review.
    private Double rating;
    @ManyToMany(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "product_category",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToMany(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "p_image_id"))
    private List<ProductImage> productImages;

    @Column(length = 100 ,nullable = false)
    private String sku;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Double discount;
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Brand brand;

    @OneToMany(mappedBy = "product")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProductReview> productReviews;

    @CreatedDate
    @Column(nullable = false)
    private Date createAt;
    private Date updateAt;
    private Date publishedAt;

    private Date startAt;
    private Date endsAt;

    @Column(columnDefinition = "text")
    private String content;

    @OneToMany(mappedBy = "product")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<CartItem> cartItems;
}
