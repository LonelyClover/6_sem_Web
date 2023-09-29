package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Place")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Place implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Number", nullable = false)
    @NonNull
    private String name;

    @Column(name = "Place type", nullable = false)
    @NonNull
    private PlaceType placeType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Theater ID")
    @NonNull
    private Theater theater;
}