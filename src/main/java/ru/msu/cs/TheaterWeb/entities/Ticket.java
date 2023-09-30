package ru.msu.cs.TheaterWeb.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket implements CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performance_id")
    @NonNull
    private Performance performance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    @NonNull
    private Place place;

    @Column(name = "customer_name", nullable = false)
    @NonNull
    private String customerName;

    @Column(name = "customer_phone_number", nullable = false)
    @NonNull
    private String customerPhoneNumber;
}