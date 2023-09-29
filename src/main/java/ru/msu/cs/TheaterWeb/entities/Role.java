package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "ROle")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Role implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Actor ID")
    @NonNull
    private Actor actor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Play ID")
    @NonNull
    private Play play;

    @Column(name = "Info")
    private String info;
}