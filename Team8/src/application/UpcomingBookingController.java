package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import Business.Company;
import Business.Employee.Service;
import Calendar.Booking;
import Calendar.Calendar;
import Menu.Menu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UpcomingBookingController {
	
	private Menu menu;
	
	private Company comp;
	
	//@FXML
	private TextArea ta;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	@FXML
	private VBox textBox;
	
	@FXML
	public void initialize() {;
		ta = new TextArea();
		ta.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println(oldValue+"-"+newValue);
				
			}
		});
	}
	
	public String getUpcomingBooking() {
		Calendar cal = comp.getCalendar();
		//cal.addBookingToCalendar(LocalDate.of(2017, 04, 29), LocalTime.of(8, 00), LocalTime.of(9, 00));
		ArrayList<Booking> list = cal.getDisplayFutureBooking();
		String future="";
		for(Booking book : list) {
			future = future + String.format("%-20s %-20s %-20s %10s %25s %28s\n", book.getID(), book.getDate().toString(), book.getStartTime().toString(), book.getEndTime().toString(), book.getCustomerID(), "000");
		}
		ta.setFont(Font.font ("Helvetica-Normal", 16));
		ta.setText(future);
		ta.setEditable(false);
		ta.setLayoutX(299.0);
		ta.setLayoutY(230.0);
		ta.setPrefHeight(430.0);
		ta.setPrefWidth(793.0);
		
		System.out.println(future);
		rootPane.getChildren().add(ta);
		return future;
	}
	
	public void initiate(Menu menu) {
		this.menu = menu;
		comp = menu.getCompany();
		getUpcomingBooking();
	}
	
	@FXML
	void Home(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu);
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu);
    }
	
	@FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu);
    }
}
