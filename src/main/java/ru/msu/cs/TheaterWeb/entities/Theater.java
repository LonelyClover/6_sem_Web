package ru.msu.cs.TheaterWeb.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "theater")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Theater implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "address", nullable = false)
    @NonNull
    private String address;

    @Column(name = "info")
    private String info;
}