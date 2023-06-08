package pl.lodz.p.it.ssbd2023.ssbd03.entities;

import jakarta.persistence.*;
import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd03.common.HeatEntityListener;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode
@Entity
@Table(name = "advance",
        indexes = {
                @Index(name = "advance_place_id", columnList = "place_id")
        })
@EntityListeners(value = HeatEntityListener.class)
public abstract class Advance extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "date_", nullable = false)
    protected LocalDate date;

    @ManyToOne
    @JoinColumn(name = "place_id", updatable = false, referencedColumnName = "id")
    protected Place place;

    public Advance(LocalDate date, Place place) {
        this.date = date;
        this.place = place;
    }
}
