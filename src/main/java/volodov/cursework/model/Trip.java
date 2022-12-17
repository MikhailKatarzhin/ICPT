package volodov.cursework.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;

    @Column(name = "departure_time", nullable = false)
    private Instant departureTime;

    @OneToMany(mappedBy = "trip")
    private Set<RouteSequence> routeSequences = new LinkedHashSet<>();

    @OneToMany(mappedBy = "trip")
    private Set<Bill> bills = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public Short getPlaces() {
        return places;
    }

    public void setPlaces(Short places) {
        this.places = places;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public Set<RouteSequence> getRouteSequences() {
        return routeSequences;
    }

    public void setRouteSequences(Set<RouteSequence> routeSequences) {
        this.routeSequences = routeSequences;
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

}