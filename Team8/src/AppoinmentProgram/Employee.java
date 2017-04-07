package AppoinmentProgram;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class Employee {
	
	private String ID;
	private String firstName;
	private String lastName;
	
	HashMap<LocalDate, ArrayList<LocalTime>> availability;
	private static ArrayList<Service> serviceType;
	public enum Service {
		femaleCut,
		maleCut,
		femaleDye,
		maleDye,
		femalePerm,
		malePerm,
		femaleWash,
		maleWash
	}
	
	public Employee(String ID, String firstName, String lastName, ArrayList<Service> serviceType){
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.serviceType = (ArrayList<Service>) serviceType.clone(); 
		availability = new HashMap<LocalDate, ArrayList<LocalTime>>();
	}
	
	// TODO: Needs Testing
	public static ArrayList<Service> getService() {
		return serviceType;
	}
	
	public void addAvailability(LocalDate date, LocalTime start_time, LocalTime end_time) {
		ArrayList<LocalTime> times = new ArrayList<LocalTime>();
		LocalTime time = start_time;
		while(!time.toString().equals(end_time.plusMinutes(15).toString())) {
			times.add(time);
			time = time.plusMinutes(15);
		}
		availability.put(date,times);
	}
	
	public HashMap<LocalDate, ArrayList<LocalTime>> getAvailability() {
		return availability;
	}

	public String getID() {
		return ID;
	}
}
