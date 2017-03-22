import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;

public class Calendar {
	public enum Status {
		pending,
		booked,
		free
	}
	private HashMap<String,HashMap<String,Status>> information;
	private Booking[] bookingPendingList;
	private LocalDate currentDate;
	private HashMap<String, Booking> bookingList;
	
	public Calendar(LocalDate date, HashMap<String,HashMap<String,Status>> info) {
		information = info;
		bookingList = new HashMap<String, Booking>();
		currentDate = date;
	}
	
	// TODO: Needs Testing
	public void addAvailableTime(String employeeID, String date, String time) {
		
	}
	
	// TODO: Needs Testing
	public Boolean isBooked(String date, String time) {
		Status status = information.get(date).get(time);
		if(status == Status.booked) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public LocalDate getDate() {
		return currentDate;
	}
	
	public void setCurrentDate(LocalDate date) {
		currentDate = date;
	}
	
	public HashMap<String, HashMap<String, Status>> getCalendarInfo() {
		return information;
	}
	
	// TODO: Check if booking is free before add pending status
	// TODO: Needs Testing
	public void requestBooking(String date, String time) {
		information.get(date).put(time, Status.pending);
	}
	
	// TODO: Have to do error checking in case there are no last 7 days in Calendar
	// TODO: Needs Testing
	public HashMap<String,HashMap<String,Status>> getHistory() {
		LocalDate oldDate = currentDate.minusDays(7);
		String oldDateString = oldDate.toString();
		HashMap<String,HashMap<String,Status>> historyInfo = new HashMap<String, HashMap<String, Status>>();
		for(int i =0; i < 7; i++){
			HashMap<String, Status> timeInfo = information.get(oldDateString);
			historyInfo.put(oldDateString, timeInfo);
			oldDate = oldDate.plusDays(1);
			oldDateString = oldDate.toString();
		}
		return historyInfo;
	}
	
	// TODO: Needs Testing
	public HashMap<String, HashMap<String, Status>> getNextWeek() {
		LocalDate newDate = currentDate.plusDays(1);
		String newDateString = newDate.toString();
		HashMap<String,HashMap<String,Status>> futureInfo = new HashMap<String, HashMap<String, Status>>();
		for(int i =0; i < 7; i++){
			HashMap<String, Status> timeInfo = information.get(newDateString);
			futureInfo.put(newDateString, timeInfo);
			newDateString = newDate.plusDays(1).toString();
		}
		return futureInfo;
	}
	
    // TODO: Needs Testing
	public Booking[] getBookingPendingList() {
		return bookingPendingList;
	}
	
	// TODO: returns a concatenated String which displays the calendar on console
	public String displayCalendar() {
		return null;
	}
	
}
