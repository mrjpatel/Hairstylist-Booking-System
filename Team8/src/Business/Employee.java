package Business;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Logger;

import Calendar.Booking;

public class Employee {
	private String ID;
	private String firstName;
	private String lastName;
	private HashMap<DayOfWeek, ArrayList<LocalTime>> availability;
	private HashMap<LocalDate, ArrayList<LocalTime>> bookings;
	private ArrayList<Service> serviceType;
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	// 1 block equals to 15 minutes so 2 blocks is 30minutes etc
	public enum Service {
		femaleCut(2),
		maleCut(1),
		femaleDye(4),
		maleDye(3),
		femalePerm(4),
		malePerm(3),
		femaleWash(1),
		maleWash(1);
		
		private int time;
		
		private Service(int s) {
			time = s;
		}
		
		public int getTime() {
			return time;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Employee(String ID, String firstName, String lastName, ArrayList<Service> serviceType){
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.serviceType = (ArrayList<Service>) serviceType.clone(); 
		availability = new HashMap<DayOfWeek, ArrayList<LocalTime>>();
		bookings =  new HashMap<LocalDate, ArrayList<LocalTime>>();
	}
	
	public ArrayList<Service> getService() {
		return serviceType;
	}
	
	public void addAvailability(DayOfWeek day, LocalTime start_time, LocalTime end_time) {
		ArrayList<LocalTime> times = new ArrayList<LocalTime>();
		LocalTime time = start_time;
		while(!time.toString().equals(end_time.toString())) {
			times.add(time);
			time = time.plusMinutes(15);
		}
		availability.put(day,times);
	}
	
	// Stores booking with employee so they are booked at the given time
	public void addBooking(LocalDate date, LocalTime start_time, LocalTime end_time) {
		ArrayList<LocalTime> times = new ArrayList<LocalTime>();
		LocalTime current_time = start_time;
		if(bookings.get(date)!=null){
			if(bookings.get(date).size() > 0) {
				for(LocalTime t : bookings.get(date)) {
					times.add(t);
				}
			}
		}
		while(!current_time.equals(end_time)) {
			times.add(current_time);
			current_time = current_time.plusMinutes(15); 
		}
		bookings.put(date, times);
	}
	
	// Is the employee free at given time
	public Boolean isFree(LocalDate date, LocalTime start_time, LocalTime end_time) {
		ArrayList<LocalTime> times = bookings.get(date);
		LocalTime current_time;
		
		if(times != null) {
			current_time = start_time;
		} else {
			LOGGER.info("isFree: bookings is empty -> TRUE");
			return true;
		}
		
		while(!current_time.equals(end_time)) {
			for(LocalTime t : times) {
				if(t.equals(current_time)){
					LOGGER.info("isFree: time has been taken -> FALSE");
					return false;
				}
			}
			current_time = current_time.plusMinutes(15);
		}
		LOGGER.info("isFree: TRUE");
		return true;
	}
	
	public HashMap<DayOfWeek,ArrayList<LocalTime>> getAvailability() {
		return availability;
	}
	
	public String getID() {
		return ID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public HashMap<LocalDate, ArrayList<LocalTime>> getBookings() {
		return bookings;
	}
}
