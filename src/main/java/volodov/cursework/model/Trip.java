package volodov.cursework.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private TripStatus status;

    @Column(name = "places", nullable = false)
    private Short places;

    @Column(name = "cost", nullable = false)
    private Long cost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;

    @Column(name = "departure_time", nullable = false)
    private Instant departureTime;

    @OneToMany(mappedBy = "trip")
    private Set<RouteSequence> routeSequences = new LinkedHashSet<>();

    @OneToMany(mappedBy = "trip")
    private Set<Bill> bills = new LinkedHashSet<>();

}