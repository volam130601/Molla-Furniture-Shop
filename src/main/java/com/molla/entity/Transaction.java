package com.molla.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @Column(length = 100)
    private String code;
    private Integer type;
    private Integer mode;
    private Integer status;

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
