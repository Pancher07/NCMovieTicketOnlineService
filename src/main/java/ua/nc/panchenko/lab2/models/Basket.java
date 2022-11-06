package ua.nc.panchenko.lab2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lab2_pdv_baskets")
public class Basket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "total_price")
    private int totalPrice;
    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "basket")
    private List<Ticket> tickets;

    public Basket(User user) {
        this.user = user;
    }

    public int calculateTotalPrice(List<Ticket> tickets) {
        totalPrice = 0;
        for (Ticket ticket : tickets) {
            totalPrice = totalPrice + ticket.getPrice();
        }
        return totalPrice;
    }
}
