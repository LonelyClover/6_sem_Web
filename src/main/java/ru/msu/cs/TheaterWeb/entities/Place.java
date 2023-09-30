package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "place")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Place implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number", nullable = false)
    @NonNull
    private String number;

    @Column(name = "place_type", nullable = false)
    @NonNull
    private PlaceType placeType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theater_id")
    @NonNull
    private Theater theater;
}