package com.molla.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @Column(nullable = false, length = 100)
    private String sessionId;
    @Column(nullable = false, length = 100)
    private String token;
    @Column(nullable = false)
    private String status;
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 15)
    private String mobile;
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String line1;
    @Column(length = 50)
    private String line2;
    @Column(length = 50)
    private String province;
    @Column(length = 50)
    private String country;

    @CreatedDate
    @Column(nullable = false)
    private Date createAt;
    @Temporal(TemporalType.DATE)
    private Date updateAt;
    @Temporal(TemporalType.DATE)
    private Date publishedAt;
    @Column(columnDefinition = "text")
    private String content;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
}
