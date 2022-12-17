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
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "arrival_to", nullable = false, length = 45)
    private String arrivalTo;

    @Column(name = "arrival_from", nullable = false, length = 45)
    private String arrivalFrom;

    @Column(name = "path_length", nullable = false)
    private Long pathLength;

    @Column(name = "cost", nullable = false)
    private Long cost;

    @OneToMany(mappedBy = "rout")
    private Set<RouteSequence> routeSequences = new LinkedHashSet<>();
}