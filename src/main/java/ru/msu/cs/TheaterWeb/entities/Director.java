package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Director")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Director implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "Info")
    private String info;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Theater ID")
    @NonNull
    private Theater theater;
}