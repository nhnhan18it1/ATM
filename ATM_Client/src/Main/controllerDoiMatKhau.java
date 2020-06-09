package Main;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.ResourceBundle;

import DAO.connectDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerDoiMatKhau implements Initializable{
	
	@FXML
	private TextField txtIDTK;
	@FXML
	private TextField txtSoDuTK;
	@FXML
	private PasswordField pwfOld;
	@FXML
	private PasswordField pwfNew;
	@FXML
	private PasswordField pwfReP;
	@FXML
	private Label lblCPass;
	boolean CP=false;
	@FXML
	private Label lblCNP;
	boolean CNP=false;
	@FXML
	private Label lblCRpass;
	boolean CRP=false;
	
	connectDAO dao=new connectDAO();
	public void SetInf(int ID,int SD) {
		txtIDTK.setText(String.valueOf(ID));
		txtSoDuTK.setText(String.valueOf(SD));
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		CheckIn4();
	}

	public void CheckIn4() {
		Image imgtrue = new Image("file:///C:/Users/nhavb/eclipse-workspace/ATM_Client/img/icon-true.jpg");
		Image imgf = new Image("file:///C:/Users/nhavb/eclipse-workspace/ATM_Clien/img/icon-false.jpg");
		pwfOld.textProperty().addListener((Observable,oldvalue,newvalue)->{
			String s=newvalue;
			try {
				if (dao.CheckOldPass(Integer.parseInt(txtIDTK.getText()), s)) {
					ImageView imgv = new ImageView(imgtrue)	;
					imgv.setFitHeight(25);
					imgv.setFitWidth(25);
					lblCPass.setGraphic(imgv);
					CP = true;
				}
				else {
					ImageView imgv = new ImageView(imgf);
					imgv.setFitHeight(25);
					imgv.setFitWidth(25);
					lblCPass.setGraphic(imgv);
					CP = false;
				}
			} catch (NumberFormatException | RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		pwfNew.textProperty().addListener((Observable, oldvalue, newvalue) -> {
			String s = newvalue;
			if (s.length() >= 8 && s.length() < 14) {
				ImageView imgv = new ImageView(imgtrue);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCNP.setGraphic(imgv);
				CNP = true;

			} else {
				ImageView imgv = new ImageView(imgf);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCNP.setGraphic(imgv);
				CNP = false;
			}
		});
		pwfReP.textProperty().addListener((Observable, oldvalue, newvalue) -> {
			String s = newvalue;
			if (s.length() >= 8 && s.length() < 14&&s.equals(pwfNew.getText())) {
				ImageView imgv = new ImageView(imgtrue);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCRpass.setGraphic(imgv);
				CRP = true;

			} else {
				ImageView imgv = new ImageView(imgf);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCRpass.setGraphic(imgv);
				CRP = false;
			}
		});
	}
	public void DoiMK(ActionEvent e) throws AccessException, NumberFormatException, RemoteException, NotBoundException {
		connectDAO dao=new connectDAO();
		boolean r= dao.doimk(Integer.parseInt(txtIDTK.getText()), pwfNew.getText());
		if (r) {
			Alert al=new Alert(AlertType.INFORMATION);
			al.setContentText("Thay đổi mật khẩu thành công");
			al.show();
		}
	}
	public void Back(ActionEvent e) throws IOException {
		Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("frMainM.fxml"));
		Parent cnAdm = loader.load();
		cnAdm.getStylesheets().add(getClass().getResource("frMaimM.css").toExternalForm());
		Scene sc = new Scene(cnAdm);
		controllerMainM cta = loader.getController();
		cta.SetInf(Integer.parseInt(txtIDTK.getText()), Integer.parseInt(txtSoDuTK.getText()));
		s.setScene(sc);
	}
	
}
