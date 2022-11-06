package ua.nc.panchenko.lab2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lab2_pdv_sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @NotEmpty(message = "Date shouldn`t empty")
    private String date;
    @Column
    @NotEmpty(message = "Time shouldn`t empty")
    private String time;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movieOfSessions;

    @OneToOne(mappedBy = "sessionOfCinemaHall")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private CinemaHall cinemaHall;
}
