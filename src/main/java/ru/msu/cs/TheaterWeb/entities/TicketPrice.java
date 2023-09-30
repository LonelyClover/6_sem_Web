package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "ticket_price")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class TicketPrice implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performance_id")
    @NonNull
    private Performance performance;

    @Column(name = "place_type", nullable = false)
    @NonNull
    private PlaceType placeType;

    @Column(name = "price", nullable = false)
    @NonNull
    private Long price;
}