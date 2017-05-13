package testCases;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import business.Company;
import business.Customer;
import business.Employee;
import calendar.Calendar;
import database.AvailabilityDatabase;
import database.CompanyDatabase;
import database.CustomerDatabase;

public class CompanyTest {
	Company comp;
	String ID;
	Customer customer;
	HashMap<String, Customer> custList;
	Employee employee;
	ArrayList<String> service;
	CompanyDatabase compDb;
	CustomerDatabase custDb;
	AvailabilityDatabase availDb;
	
	@Before
	public void setUp() throws Exception {
		comp = new Company();
		compDb = new CompanyDatabase();
		custDb = new CustomerDatabase();
		availDb = new AvailabilityDatabase();
		ID = "000";
		customer = new Customer(ID,"fname", "lname");
		comp.addCustomer(customer);
		custList = comp.getCustList();
		
		service = new ArrayList<String>();
		service.add("femaleCut");
		employee = new Employee("0", "fname", "lname", service);
		comp.addEmployee(employee);
	}

	@Test
	public void testAddCustomer() {
		String expected_ID = "000";
		String expected_fname = "fname";
		String expected_lname = "lname";
		Customer customer = new Customer(expected_ID,expected_fname, expected_lname);
		
		comp.addCustomer(customer);
		HashMap<String, Customer>custList = comp.getCustList();
		Customer actual_customer = custList.get(expected_ID);
		
		// Does the customer ID exist in the HashMap
		Boolean expected = true;
		Boolean actual;
		Customer cust = custList.get(ID);
		if(cust != null) {
			actual = true;
		} else {
			actual = false;
		}
		assertEquals(expected, actual);
		
		String actual_ID = actual_customer.getUsername();
		assertEquals(expected_ID, actual_ID);
		
		String actual_fname = customer.getFirstName();
		assertEquals(expected_fname, actual_fname);
	}
	
	@Test 
	public void testGetCustomer() {
		Customer expected_customer = customer;
		Customer actual_customer = comp.getCustomer(ID);
		
		assertEquals(expected_customer, actual_customer);
	}
	
	@Test
	public void testGetCustList() {
		HashMap<String, Customer> expected = custList;
		HashMap<String, Customer> actual = comp.getCustList();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSetCustList() {
		HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> nested_info = new HashMap<String, String>();
		String expected_fname = "fname";
		String expected_lname = "lname";
		String expected_ID = "000";
		
		nested_info.put("fName", expected_fname);
		nested_info.put("lName", expected_lname);
		map.put(expected_ID, nested_info);
		
		comp.setCustList(map);
		HashMap<String, Customer> customerList = comp.getCustList();
		Customer customer = customerList.get(expected_ID);
		
		int expected_size = 1;
		int actual_size = comp.getCustList().size();
		assertEquals(expected_size, actual_size);
		
		Boolean expected_keyExist = true;
		Boolean actual_keyExist = customerList.containsKey(expected_ID);
		assertEquals(expected_keyExist, actual_keyExist);
		
		String actual_fname = customer.getFirstName();
		assertEquals(expected_fname, actual_fname);
		
		String actual_lname = customer.getLastName();
		assertEquals(expected_lname, actual_lname);
	}
	
	@Test
	public void testAddEmployee() {
		ArrayList<String> expected_service = new ArrayList<String>();
		expected_service.add("femaleCut");
		String expected_ID = "0";
		String expected_fname = "fname";
		String expected_lname ="lname";
		Employee employee = new Employee("0", "fname", "lname", service);
		
		comp.addEmployee(employee);
		HashMap<String, Employee> employee_list = comp.getEmployeeList();
		Employee actual_employee = employee_list.get(expected_ID);
		
		int expected_size = 1;
		int actual_size = comp.getEmployeeList().size();
		assertEquals(expected_size, actual_size);
		
		Boolean expected_keyExist = true;
		Boolean actual_keyExist = employee_list.containsKey(expected_ID);
		assertEquals(expected_keyExist, actual_keyExist);
		
		String actual_fname = actual_employee.getFirstName();
		assertEquals(expected_fname, actual_fname);
		
		String actual_lname = actual_employee.getLastName();
		assertEquals(expected_lname, actual_lname);
		
		ArrayList<String> actual_service = actual_employee.getService();
		assertEquals(expected_service, actual_service);
	}
	
	@Test
	public void testGetEmployeeList() {
		HashMap<String, Employee> expected_list = new HashMap<String, Employee>();
		expected_list.put(employee.getID(), employee);
		HashMap<String, Employee> actual_list = comp.getEmployeeList();
		
		assertEquals(expected_list, actual_list);
	}
	
	@Test
	public void testGetCalendar() {
		Calendar expected_cal = new Calendar(LocalDate.of(2017, 02, 02));
		comp.setCalendar(expected_cal);
		Calendar actual_cal = comp.getCalendar();
		
		assertEquals(expected_cal, actual_cal);
	}
	
	@Test
	public void testSetEmployeeList() {
		HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> nested_info = new HashMap<String, String>();
		String expected_fname = "fname";
		String expected_lname = "lname";
		String expected_ID = "000";
		String expected_service_string = "femaleCut, maleCut";
		ArrayList<String> expected_service = new ArrayList<String>();
		expected_service.add("femaleCut");
		expected_service.add("maleCut");
		
		nested_info.put("fname", expected_fname);
		nested_info.put("lname", expected_lname);
		nested_info.put("service", expected_service_string);
		map.put(expected_ID, nested_info);
		
		comp.setEmployeeList(map);
		HashMap<String, Employee> employee_list = comp.getEmployeeList();
		Employee actual_employee = employee_list.get(expected_ID);
		
		int expected_size = 1;
		int actual_size = comp.getEmployeeList().size();
		assertEquals(expected_size, actual_size);
		
		Boolean expected_keyExist = true;
		Boolean actual_keyExist = employee_list.containsKey(expected_ID);
		assertEquals(expected_keyExist, actual_keyExist);
		
		String actual_fname = actual_employee.getFirstName();
		assertEquals(expected_fname, actual_fname);
		
		String actual_lname = actual_employee.getLastName();
		assertEquals(expected_lname, actual_lname);
		
		ArrayList<String> actual_service = actual_employee.getService();
		assertEquals(expected_service, actual_service);
	}
	
	@Test
	public void testGetServiceFemaleCut() {
		String expected = "femaleCut";
		String s = "femaleCut";
		String actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceMaleCut() {
		String expected = "maleCut";
		String s = "maleCut";
		String actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceFemaleDye() {
		String expected = "femaleDye";
		String s = "femaleDye";
		String actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceMaleDye() {
		String expected = "maleDye";
		String s = "maleDye";
		String actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceFemalePerm() {
		String expected = "femalePerm";
		String s = "femalePerm";
		String actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceMalePerm() {
		String expected = "malePerm";
		String s = "malePerm";
		String actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceFemaleWash() {
		String expected = "femaleWash";
		String s = "femaleWash";
		String actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceMaleWash() {
		String expected = "maleWash";
		String s = "maleWash";
		String actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceFail() {
		String expected = null;
		String s = "cut";
		String actual = comp.getService(s);
		
		assertEquals(expected, actual);
	}
}
