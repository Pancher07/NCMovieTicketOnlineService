package ua.nc.panchenko.lab2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lab2_pdv_tickets")
public class Ticket implements Comparable<Ticket> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Min(value = 1, message = "Rows must be >= 1")
    @Max(value = 20, message = "Rows must be <= 20")
    @Column
    private int row;
    @Min(value = 1, message = "Seats must be >= 1")
    @Max(value = 30, message = "Seats must be <= 30")
    @Column
    private int seat;
    @Min(value = 0, message = "Price must be >= 0")
    @Column
    private int price;
    @Column
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "cinemaHall_id", referencedColumnName = "id")
    private CinemaHall cinemaHall;

    @ManyToOne
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public Ticket(int row, int seat, int price) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        available = true;
    }

    @Override
    public int compareTo(Ticket t) {
        return this.getId() - t.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && row == ticket.row && seat == ticket.seat && price == ticket.price
                && available == ticket.available && Objects.equals(cinemaHall, ticket.cinemaHall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, seat, price, available, cinemaHall);
    }
}
