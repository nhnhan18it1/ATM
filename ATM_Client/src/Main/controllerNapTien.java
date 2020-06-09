package Main;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.ResourceBundle;

import ATMCN.TaiKhoan;
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
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class controllerNapTien implements Initializable {

	@FXML
	private TextField txtIDTK;
	@FXML
	private TextField txtSoDuTK;
	@FXML
	private TextField txtSoTien;
	public void SetInf(int ID,int SD) {
		txtIDTK.setText(String.valueOf(ID));
		txtSoDuTK.setText(String.valueOf(SD));
	}
	public void NapTien(ActionEvent e) throws AccessException, NumberFormatException, RemoteException, NotBoundException {
		if (Integer.parseInt(txtSoTien.getText())!=0) {
			connectDAO dao=new connectDAO();
			boolean r= dao.NapTien(Integer.parseInt(txtIDTK.getText()), Integer.parseInt(txtSoDuTK.getText()),Integer.parseInt(txtSoTien.getText()));
			if (r) {
				
				Alert al=new Alert(AlertType.INFORMATION);
				al.setContentText("Bạn Đã Nạp Thành công'"+txtSoTien.getText()+"' vào Số Tài Khoản'"+txtIDTK.getText()+"' ");
				al.show();
				txtSoDuTK.setText(String.valueOf(Integer.parseInt(txtSoDuTK.getText())+Integer.parseInt(txtSoTien.getText())));
				txtSoTien.setText(String.valueOf(0));
			}
			else {
				Alert al=new Alert(AlertType.ERROR);
				al.setContentText("lỗi Hệ Thống Vui Lòng Thưt LẠi Sau");
				al.show();
			}
		}
		else {
			Alert al=new Alert(AlertType.ERROR);
			al.setContentText("Vui Lòng Nhập Số Tiền muốn Nạp");
			al.show();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		txtSoTien.textProperty().addListener((Observable,oldvalue,newvalue)->{
			if (newvalue.length()==0) {
				txtSoTien.setText(String.valueOf(0));
			}
		});
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
