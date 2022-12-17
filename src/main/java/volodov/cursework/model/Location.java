package volodov.cursework.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @OneToMany(mappedBy = "location")
    private Set<RouteSequence> routeSequences = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RouteSequence> getRouteSequences() {
        return routeSequences;
    }

    public void setRouteSequences(Set<RouteSequence> routeSequences) {
        this.routeSequences = routeSequences;
    }

}