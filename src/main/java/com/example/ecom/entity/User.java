package com.example.ecom.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 30)
    private String username;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is must")
    private String password;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
    private Set<Role> role = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="users_coupons",
            joinColumns={@JoinColumn(name="userId")},
            inverseJoinColumns={@JoinColumn(name="couponId")})
    private Set<Coupon> couponUsed;


//    public GrantedAuthority getRoleName(){
//        return (GrantedAuthority) role.stream()
//                .map(Role::getRole)
//                .collect(Collectors.toSet());
//    }

}
