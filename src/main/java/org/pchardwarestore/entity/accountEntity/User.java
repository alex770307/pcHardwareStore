package org.pchardwarestore.entity.accountEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required and must be not blank")
    @Size(min = 3, max = 25, message = "First name length not correct")
    private String firstName;

    @Size(max = 25, message = "Last name too long")
    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    private String hashPassword;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    private String photoLink;

    //TODO Добавил 07.05.25
    @Builder.Default
    //TODO Добавил 07.05.25
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileInfo> photos = new HashSet<>();
}
