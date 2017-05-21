package gui.booking_summary;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import business.Company;
import calendar.Booking;
import calendar.Calendar;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import gui.portal.CustomerPController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mainController.MainController;

public class UpcomingBookingController {
	
	private MainController menu;
	
	private String id;
	
	ObservableList<String> bookingList = FXCollections.observableArrayList();
	
	final ToggleGroup group = new ToggleGroup();
	
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
	private Label invalid;
	
	private Boolean status;
	private ArrayList<Booking> list;
	
	@FXML
	public void initialize() {;
		ta = new TextArea();
	}
	
	@FXML
	private JFXComboBox<String> chooseBooking;
	
	private BookingManagementSystem bms;
	
	String bookingID=null;
	
	@FXML
	private JFXButton cancel;
	
	@FXML
	private Label bookid;
	
	public String getUpcomingBooking() {
		Calendar cal = comp.getCalendar();
		list = cal.getDisplayFutureBooking();
		String future="";
		
		for(Booking book : list) {
			if(id != null) {
				if(book.getCustomerID().equals(id)) {
					future = future + String.format("%-20s %-20s %-20s %10s %25s %28s\n", book.getID(), book.getDate().toString(), book.getStartTime().toString(), book.getEndTime().toString(), book.getCustomerID(), book.getEmployee().getID());
				}
			} else {
				future = future + String.format("%-20s %-20s %-20s %10s %25s %28s\n", book.getID(), book.getDate().toString(), book.getStartTime().toString(), book.getEndTime().toString(), book.getCustomerID(), book.getEmployee().getID());
			}
		}
		ta.setFont(Font.font ("Lato Bold", 16));
		ta.setStyle("-fx-text-fill: white");
		ta.setText(future);
		ta.setEditable(false);
		ta.setLayoutX(340.0);
		ta.setLayoutY(270.0);
		ta.setPrefHeight(330.0);
		ta.setPrefWidth(793.0);
		
		rootPane.getChildren().remove(ta);
		rootPane.getChildren().add(ta);
		return future;
	}
	
	public void initiate(Company comp, String cust_id, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		id = cust_id;
		this.bms = bms;
		System.out.println(cust_id);
		if(cust_id == null) { 
			ArrayList<Booking> books = comp.getCalendar().getDisplayFutureBooking();
			bookingList.clear();
			if(books.size() == 0) {
				chooseBooking.setPromptText("No Bookings");
				chooseBooking.setDisable(true);
			} else {
				chooseBooking.setDisable(false);
				chooseBooking.setPromptText("Please Select");
				for(Booking book : books) {
					bookingList.add(book.getID());
					System.out.println(book.getID());
				}
				chooseBooking.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						if(bookingList.size()>0) {
							bookingID = newValue.toString();
							invalid.setText("");
						}
					}
				});
				chooseBooking.setItems(bookingList);
				chooseBooking.autosize();
			}
		} else {
			System.out.println("dis");
			chooseBooking.setOpacity(0);
			bookid.setText("");
			cancel.setOpacity(0);
			cancel.setDisable(true);
			chooseBooking.setDisable(true);
		}
		getUpcomingBooking();
	}
	
	@FXML
	void apply(ActionEvent event) {
		invalid.setText("");
		String id = bookingID;
		
		if(list == null || list.isEmpty()) {
			invalid.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
			invalid.setText("Invalid Booking ID");
		} else {
			Boolean exist = false;
			for(Booking b : list) {
				String current = b.getID();
				if(current.equals(id)) {
					if(!exist) {
						Boolean s2 = comp.getCalendar().declineBooking(id);
						if(s2) {
							invalid.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
							invalid.setText("Successfully Declined");
							exist = true;
							// implement removal in db for the booking
							comp.getBookingDb().deleteBooking(id);
							initiate(comp,this.id,bms);
						} else {
							invalid.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
							invalid.setText("Invalid Booking ID");
						}
					}
				}
				if(!exist) {
					invalid.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
					invalid.setText("Invalid Booking ID");
				}
			} 
			
		}
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(id == null) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("../portal/BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
	    	controller.initiate(comp, bms);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("../portal/CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(comp, id, bms);
		}
	}
}
