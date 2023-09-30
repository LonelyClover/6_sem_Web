package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "performance")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Performance implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "datetime", nullable = false)
    @NonNull
    private LocalDateTime datetime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "play_id")
    @NonNull
    private Play play;
}