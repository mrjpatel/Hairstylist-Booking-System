package gui.booking;

import java.io.IOException;
import java.util.HashMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import business.Company;
import business.Customer;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class MakeBooking1Controller {
	private MainController menu;
	
	private Company comp;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	@FXML
	private JFXTextField username;
	
	@FXML
    private Label logoText;
	
	private BookingManagementSystem bms;
	
	@FXML
	private Label invalid;

	public void initiate(Company comp, BookingManagementSystem bms) {
		menu = bms.getMenu();
		this.comp = comp;
		this.bms = bms;
		logoText.setText(comp.getName().toUpperCase());
	}
	
	@FXML
	void next(ActionEvent event) throws IOException {
		String cust_id = username.getText();
		HashMap<String, Customer> custList = comp.getCustList();
		Customer c = custList.get(cust_id);
		if(c == null) {
			invalid.setText("Invalid Customer ID.");
			invalid.setAlignment(Pos.CENTER);
		} else {
			AnchorPane pane;
	    	FXMLLoader m2 = new FXMLLoader(getClass().getResource("MakeBooking2.fxml"));
	    	pane = m2.load();
	    	rootPane.getChildren().setAll(pane);
	    	MakeBooking2Controller controller = m2.getController();
	    	controller.initiate(comp, cust_id, "business", bms);
		}
	}
	
	@FXML
	void Home(ActionEvent event) throws IOException {
		goToPortal();
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
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("../portal/BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(comp, bms);
    }
}
