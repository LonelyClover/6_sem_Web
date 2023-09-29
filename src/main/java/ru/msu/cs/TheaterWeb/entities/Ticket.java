package ru.msu.cs.TheaterWeb.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "Ticket")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Performance ID")
    @NonNull
    private Performance performance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Place ID")
    @NonNull
    private Place place;

    @Column(name = "Customer name", nullable = false)
    @NonNull
    private String customerName;

    @Column(name = "Customer phone number", nullable = false)
    @NonNull
    private String customerPhoneNumber;
}