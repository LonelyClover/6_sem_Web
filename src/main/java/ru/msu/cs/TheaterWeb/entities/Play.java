package ru.msu.cs.TheaterWeb.entities;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.*;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(
        typeClass = PostgreSQLIntervalType.class,
        defaultForType = Duration.class
)
public class Play implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "duration", nullable = false)
    @NonNull
    private Duration duration;

    @Column(name = "info")
    private String info;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theater_id")
    @NonNull
    private Theater theater;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "director_id")
    @NonNull
    private Director director;


    public String getDurationString() {
        if (this.duration == null) {
            return null;
        }
        return DurationFormatUtils.formatDuration(this.duration.toMillis(), "HH:mm");
    }
}