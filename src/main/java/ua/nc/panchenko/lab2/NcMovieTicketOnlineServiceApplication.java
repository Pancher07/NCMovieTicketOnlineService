package ua.nc.panchenko.lab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class NcMovieTicketOnlineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NcMovieTicketOnlineServiceApplication.class, args);
	}

}
