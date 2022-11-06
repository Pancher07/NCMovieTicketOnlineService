package ua.nc.panchenko.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.nc.panchenko.lab2.models.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketsRepository extends JpaRepository<Ticket, Integer> {
}
