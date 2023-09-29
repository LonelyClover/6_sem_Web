package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Performance")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Performance implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Date and time", nullable = false)
    @NonNull
    private LocalDateTime datetime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Play ID")
    @NonNull
    private Play play;
}