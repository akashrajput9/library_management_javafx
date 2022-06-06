package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import helpers.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IssueBookController implements Initializable {

	String query = null;
	String query2 = null;
    Connection connection = DbConnection.getConnect(); ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    
    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnIssueBook;

    @FXML
    private Spinner<String> spinnerBook;

    @FXML
    private TextField txtStudentName;

    @FXML
    void goBack(ActionEvent event) throws IOException {
    	Stage stag2e = (Stage) btnGoBack.getScene().getWindow();
		stag2e.close();
		 
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Panel.fxml"));
		Parent root3 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root3));  
		stage.show();
    }

    @FXML
    void issueBook(ActionEvent event) {
    	var studentName = txtStudentName.getText();
    	var bookName = spinnerBook.getValue();
    	
    	if(studentName == "" || bookName == "") {
			this.makeAlert("Sorry Can't add", "Validation Error", "All fileds are required");
			return;
		}
		
//		query = "INSERT INTO `issued_books`(`student_name`, `book_id`) VALUES ('"+studentName+"','SELECT id FROM `books` WHERE book_name = '"+bookName+"' `')";
		query = "INSERT INTO `issued_books`(`student_name`, `book_id`) VALUES ('"+studentName+"',(SELECT id FROM `books` WHERE book_name = '"+bookName+"' LIMIT 1) );";
		query2 = "UPDATE `books` SET `stock`= stock-1 WHERE book_name = '"+bookName+"';";
        try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(query2);
			preparedStatement.executeUpdate();
			this.makeAlert(bookName, " Book Issue", "Book successfully Issued to: "+ studentName);
			
			txtStudentName.clear();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			this.makeAlert("Sorry Can't add", "Query Error", "Query Error");
			e.printStackTrace();

			
		}
    	
    	

    }
//    ObservableList<ModelTable>  bookList = FXCollections.observableArrayList();

    ObservableList<String> bookList = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		query = "SELECT * FROM `books` WHERE stock > 0 ORDER BY id desc";
        try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
	        
	        while (resultSet.next()){
	        	bookList.add(resultSet.getString("book_name"));
	        	System.out.println(bookList);
	        	
	            
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpinnerValueFactory<String> books = new SpinnerValueFactory.ListSpinnerValueFactory<>(bookList);
		
		spinnerBook.setValueFactory(books);
	       
	}

	public void makeAlert(String headerText,String title,String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(content);
		alert.show();
	}
}
