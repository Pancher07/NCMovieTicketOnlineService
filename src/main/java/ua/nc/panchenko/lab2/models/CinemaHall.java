package ua.nc.panchenko.lab2.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "lab2_pdv_cinema_hall")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Min(value = 1, message = "Rows must be >= 1")
    @Max(value = 20, message = "Rows must be <= 20")
    @Column(name = "number_of_rows")
    private int numberOfRows;
    @Min(value = 1, message = "Seats must be >= 1")
    @Max(value = 30, message = "Seats must be <= 30")
    @Column(name = "number_of_seats_in_row")
    private int numberSeatsInRow;
    @Min(value = 0, message = "Price must be >= 0")
    @Column(name = "price_for_seat")
    private int priceForSeat;
    @OneToMany(mappedBy = "cinemaHall")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Ticket> hallLayout;


    @OneToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private Session sessionOfCinemaHall;

    public void creatTicketsInCinemaHall(int numberOfRows, int numberSeatsInRow, int priceForSeat) {
        hallLayout = new ArrayList<>();
        for (int row = 0; row < numberOfRows; row++) {
            for (int seat = 0; seat < numberSeatsInRow; seat++) {
                hallLayout.add(new Ticket(row + 1, seat + 1, priceForSeat));
            }
        }
    }

    public List<Ticket> getSortedHallLayout() {
        if (hallLayout != null) {
            Collections.sort(hallLayout);
        }
        return hallLayout;
    }
}
