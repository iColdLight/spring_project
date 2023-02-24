package com.coldlight.spring_project.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

    @Column(name = "email")
    private String email;

    @CreatedDate
    @Column(name = "user_created")
    private Date created;

    @LastModifiedDate
    @Column(name = "user_updated")
    private Date updated;

    @Column(name = "file_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;


    @ToString.Exclude
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<EventEntity> events = new ArrayList<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

}
