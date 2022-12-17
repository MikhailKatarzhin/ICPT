package volodov.cursework.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "route_sequence")
public class RouteSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rout_id", nullable = false)
    private Route rout;

    @Column(name = "sequence_number", nullable = false)
    private Long sequenceNumber;

    @Column(name = "arrival_time", nullable = false)
    private Instant arrivalTime;

    @Column(name = "departure_time", nullable = false)
    private Instant departureTime;
}