package Main;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class controllerChuyenTien implements Initializable {
	connectDAO dao=new connectDAO();
	@FXML
	private TextField txtIDTK;
	@FXML
	private TextField txtSoDuTK;
	@FXML
	private TextField txtSoTien;
	@FXML
	private Label lblCST;
	boolean CST=false;
	@FXML
	private TextField txtIDNN;
	@FXML
	private Label lblCIDNN;
	boolean CIDNN=false;
	@FXML
	private PasswordField pwfXacNhanMK;
	@FXML
	private Label lblCPW;
	boolean CPW=false;
	@FXML
	private Pane pnIn4;
	
	public void SetInf(int ID,int SD) {
		txtIDTK.setText(String.valueOf(ID));
		txtSoDuTK.setText(String.valueOf(SD));
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		pnIn4.setVisible(false);
		CheckIn4();
	}

	public void CheckIn4() {
		Image imgtrue = new Image("file:///C:/Users/nhavb/eclipse-workspace/ATM_Client/img/icon-true.jpg");
		Image imgf = new Image("file:///C:/Users/nhavb/eclipse-workspace/ATM_Client/img/icon-false.jpg");
		txtIDNN.textProperty().addListener((Observable,oldvalue,newvalue)->{
			String s=newvalue;
			
				try {
					if (dao.getInfor(Integer.parseInt(s)).size()==1) {
						ImageView imgv = new ImageView(imgtrue);
						imgv.setFitHeight(25);
						imgv.setFitWidth(25);
						lblCIDNN.setGraphic(imgv);
						CIDNN = true;
					} else {
						ImageView imgv = new ImageView(imgf);
						imgv.setFitHeight(25);
						imgv.setFitWidth(25);
						lblCIDNN.setGraphic(imgv);
						CIDNN = false;
					}
				} catch (NumberFormatException | RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		});
		txtSoTien.textProperty().addListener((Observable,oldvalue,newvalue)->{
			String s=newvalue;
			if (Integer.parseInt(s)<Integer.parseInt(txtSoDuTK.getText())) {
				ImageView imgv = new ImageView(imgtrue);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCST.setGraphic(imgv);
				CST = true;
			} else {
				ImageView imgv = new ImageView(imgf);
				imgv.setFitHeight(25);
				imgv.setFitWidth(25);
				lblCST.setGraphic(imgv);
				CST = false;
			}
		});
		pwfXacNhanMK.textProperty().addListener((Observable,oldvalue,newvalue)->{
			String s=newvalue;
			try {
				if (dao.CheckOldPass(Integer.parseInt(txtIDTK.getText()), s)) {
					ImageView imgv = new ImageView(imgtrue);
					imgv.setFitHeight(25);
					imgv.setFitWidth(25);
					lblCPW.setGraphic(imgv);
					CPW = true;
				} else {
					ImageView imgv = new ImageView(imgf);
					imgv.setFitHeight(25);
					imgv.setFitWidth(25);
					lblCPW.setGraphic(imgv);
					CPW = false;
				}
			} catch (NumberFormatException | RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	public void ChuyenTien(ActionEvent e) throws NumberFormatException, RemoteException, NotBoundException {
		if (CIDNN&&CPW&&CST) {
			boolean r= dao.ChuyenTien(Integer.parseInt(txtIDTK.getText()), Integer.parseInt(txtIDNN.getText()), Integer.parseInt(txtSoTien.getText()), Integer.parseInt(txtSoDuTK.getText()));
			if (r) {
				txtSoDuTK.setText(String.valueOf(Integer.parseInt(txtSoDuTK.getText())-Integer.parseInt(txtSoTien.getText())));
				txtSoTien.setText("0");
				txtIDNN.setText("");
				pwfXacNhanMK.setText("");
				Alert al=new Alert(AlertType.INFORMATION);
				al.setContentText("Thành Công");
				al.show();
			}
			else {
				txtSoTien.setText("0");
				txtIDNN.setText("");
				pwfXacNhanMK.setText("");
				Alert al=new Alert(AlertType.INFORMATION);
				al.setContentText("Lỗi Hệ Thống");
				al.show();
			}
		}
	}
}
