package com.molla.entity;

import com.molla.dto.AuthenticationProvider;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
//Allow Class DataSeedingListener can be run.
@EntityListeners(AuditingEntityListener.class)
@Data @AllArgsConstructor @NoArgsConstructor @Builder
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(name = "Unique_Email_AuthProvider",
        columnNames = {"email", "auth_provider"})}
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    //Function login with username;
//    private String username;
    private boolean enabled;

    private String firstName;
    private String lastName;
    private String fullName;

//    @Column(unique = true)
    private String phone;


    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider",length = 15)
    private AuthenticationProvider authProvider;

    @CreatedDate
    private Date registeredAt;

    @Temporal(TemporalType.DATE)
    private Date lastLogin;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Cart> carts;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Transaction> transactions;
}
