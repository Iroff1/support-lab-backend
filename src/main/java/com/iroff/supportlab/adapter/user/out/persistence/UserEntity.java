package com.iroff.supportlab.adapter.user.out.persistence;

import com.iroff.supportlab.adapter.common.out.persistence.BaseTimeEntity;
import com.iroff.supportlab.domain.user.model.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_user_phone", columnNames = "phone")
    }
)
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean termsOfServiceAgreed;

    @Column(nullable = false)
    private Boolean privacyPolicyAgreed;

    @Column(nullable = false)
    private Boolean marketingAgreed;

    @Column(nullable = false)
    private Boolean active;

    @Version
    private Long version;
}