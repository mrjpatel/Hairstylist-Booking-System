package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Business.Company;
import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CustomerPController {
	
	private Menu menu;
	
	private String cust_id;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private Label cust_name;

    @FXML
    private JFXButton cust_nBooking;

    @FXML
    private JFXButton cust_vCalendar;

    @FXML
    private JFXButton cust_uBooking;

    @FXML
    private JFXButton cust_vHistory;

    @FXML
    private JFXButton gotoLogout;

	public void initiate(Menu menu, String username) {
		this.menu = menu;
		cust_id = username;
		cust_name.setText(username);
	}
	
	@FXML
	void newBooking(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader mb2 = new FXMLLoader(getClass().getResource("MakeBooking2.fxml"));
    	pane = mb2.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking2Controller controller = mb2.getController();
		controller.initiate(menu, cust_id, "customer");
	}
	
	@FXML
	void viewCalendar(ActionEvent event) throws IOException{
		
	}
	
	@FXML
	void upcomingBooking(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader up = new FXMLLoader(getClass().getResource("UpcomingBooking.fxml"));
    	pane = up.load();
    	rootPane.getChildren().setAll(pane);
    	UpcomingBookingController controller = up.getController();
    	System.out.println(cust_id);
		controller.initiate(menu, cust_id);
	}
	
	@FXML
	void viewHistory(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader bh = new FXMLLoader(getClass().getResource("BookingHistory.fxml"));
    	pane = bh.load();
    	rootPane.getChildren().setAll(pane);
    	BookingHistoryController controller = bh.getController();
		controller.initiate(menu, cust_id);
	}
	
	@FXML
	void goToLogout(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu);
	}

}
