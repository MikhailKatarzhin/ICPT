package volodov.cursework.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Personality personality;

    @OneToMany(mappedBy = "driver")
    private Set<Trip> trips = new LinkedHashSet<>();

    @OneToMany(mappedBy = "consumer")
    private Set<Bill> bills = new LinkedHashSet<>();

    @Column(name = "username", nullable = false, length = 45)
    private String username;
}