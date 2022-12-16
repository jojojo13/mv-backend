package fa.group1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fa.group1.converter.ObjectConverter;
import fa.group1.dto.BookedTicketDTO;
import fa.group1.dto.BookingListDTO;
import fa.group1.services.TicketService;

@RestController
@RequestMapping(value = "api/ticket")
@CrossOrigin
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private ObjectConverter objectConverter;

	@GetMapping("")
	public ResponseEntity<?> manageTicket() {
		List<BookingListDTO> list = new ArrayList<>();
		for (Object a : ticketService.findAllTicket()) {
			String[] data = objectConverter.convert(a);
			BookingListDTO bookingListDto = new BookingListDTO(Integer.valueOf(data[0]), Integer.valueOf(data[1]),
					objectConverter.convertString(data[2]), objectConverter.convertString(data[3]),
					objectConverter.convertString(data[4]), objectConverter.convertString(data[5]),
					objectConverter.convertString(data[6]), objectConverter.convertString(data[7]),
					Integer.valueOf(data[8]));
			list.add(bookingListDto);
		}
		List<BookingListDTO> lis = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getTicketId().equals(list.get(j).getTicketId())) {
					list.get(i).setSeat(list.get(i).getSeat() + "," + list.get(j).getSeat());
					lis.add(list.get(j));
				}
			}
		}
		list.removeAll(lis);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("bookedTicket")
	public ResponseEntity<?> manageBookedTicket(@RequestHeader(name = "Authorization") String token) {
		List<BookedTicketDTO> bookedTicketDTOs = ticketService.getAllBookedTicketDTOs(token);
		return new ResponseEntity<>(bookedTicketDTOs, HttpStatus.OK);
	}
}
