package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Ticket price")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class TicketPrice implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Performance ID")
    @NonNull
    private Performance performance;

    @Column(name = "Place type", nullable = false)
    @NonNull
    private PlaceType placeType;

    @Column(name = "Price", nullable = false)
    @NonNull
    private Long price;
}