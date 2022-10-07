package com.molla.entity;

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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String username;
    private boolean enabled;

    private String firstName;
    private String lastName;
    private String phone;

    @CreatedDate
    private Date registeredAt;

    @Temporal(TemporalType.DATE)
    private Date lastLogin;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}
