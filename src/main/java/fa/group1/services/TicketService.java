package fa.group1.services;

import java.util.List;

import fa.group1.dto.BookedTicketDTO;
import fa.group1.entities.Ticket;

public interface TicketService {

	List<Object> findAllTicket();
//	Ticket addTicket(Ticket ticket);
	List<BookedTicketDTO> getAllBookedTicketDTOs(String token);
}
