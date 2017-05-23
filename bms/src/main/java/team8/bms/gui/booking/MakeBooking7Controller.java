package team8.bms.gui.booking;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import team8.bms.gui.portal.BusinessPController;
import team8.bms.gui.portal.CustomerPController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import team8.bms.BookingManagementSystem;
import team8.bms.business.Company;
import team8.bms.business.Employee;
import team8.bms.gui.LoginController;
import team8.bms.mainController.MainController;

public class MakeBooking7Controller {
	private MainController menu;
	
	private Company comp;
	
	private String cust_id;
	
	private String service;
	
	private Employee employee;
	
	private LocalDate date;
	
	private LocalTime time;
	
	private LocalTime end_time;
	
	private String portal;
	
	@FXML
	private Label invalid;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	@FXML
    private Label logoText;
	
	private BookingManagementSystem bms;

	public void initiate(Company comp, String cust_id, String service, Employee employee, LocalDate date, LocalTime time, String portal, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		this.date = date;
		this.time = time;
		this.portal = portal;
		this.comp = comp;
		this.bms = bms;
		logoText.setText(comp.getName().toUpperCase());
		
		Label id_label = new Label();
		Label service_label = new Label();
		Label emp_label = new Label();
		Label date_label = new Label();
		Label time_label = new Label();
		
		int time_taken = comp.getServiceTime(service);
		end_time = time;
		for(int i = 0; i<time_taken;i++) {
			end_time = end_time.plusMinutes(15);
		}
		
		id_label.setText("Customer ID: "+cust_id);
		service_label.setText("Service: "+service.toString());
		emp_label.setText("Employee: "+employee.getFirstName()+" "+employee.getLastName());
		date_label.setText("Date: "+date.toString());
		time_label.setText("Time: "+time.toString()+"-"+end_time);
		
		ArrayList<Label> labels = new ArrayList<Label>();
		labels.add(id_label);
		labels.add(service_label);
		labels.add(emp_label);
		labels.add(date_label);
		labels.add(time_label);
		
		int counter = 0;
		for(Label lab : labels) {
			lab.setFont(Font.font(16));
			lab.setStyle("-fx-text-fill: white");
			lab.setLayoutX(598.0);
			lab.setLayoutY(280.0+(counter*70));
			rootPane.getChildren().add(lab);
			counter++;
		}
		
	}
	
	@FXML
	void accept(ActionEvent event) throws IOException {
		String status = "pending";
		comp.getCalendar().requestBooking(date, time, end_time, employee, service, cust_id);
		String bID = comp.getCalendar().getCalendarInfo().get(date).get(time).getID();
		menu.addBooking(bID, cust_id, service.toString(), employee.getID(), date.toString(), time.toString()+"-"+end_time.toString(), status);
		employee.addBooking(date, time, end_time);
		if(portal.equals("business")) {
			status = "booked";
			comp.getCalendar().acceptBooking(bID);
			menu.addBooking(bID, cust_id, service.toString(), employee.getID(), date.toString(), time.toString()+"-"+end_time.toString(), status);
		}
		goToPortal();
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb6 = new FXMLLoader(getClass().getResource("/MakeBooking6.fxml"));
    	pane = mb6.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking6Controller controller = mb6.getController();
    	controller.initiate(comp, cust_id, service, employee, date, portal, bms);
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("/BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
	    	controller.initiate(comp, bms);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("/CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(comp, cust_id, bms);
		}
    }
}
