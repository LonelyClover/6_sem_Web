package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import javax.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "Play")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Play implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "Duration", nullable = false)
    @NonNull
    private Duration duration;

    @Column(name = "Info")
    private String info;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Theater ID")
    @NonNull
    private Theater theater;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Director ID")
    @NonNull
    private Director director;
}