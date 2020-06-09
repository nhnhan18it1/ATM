package Main;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.prism.PixelFormat.DataType;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerRutTien implements Initializable {

	@FXML
	private TextField txtIDTK;
	@FXML
	private TextField txtSoDuTK;
	@FXML
	private TextField txtSTMR;
	@FXML
	private Button btn500000;
	@FXML
	private Button btn1000000;
	@FXML
	private Button btn4000000;
	@FXML
	private Button btn2000000;
	@FXML
	private Button btn3000000;
	@FXML
	private Button btn5000000;
	@FXML
	private Button btnOth;

	public void SetInf(int Id, int Sd) {
		txtIDTK.setText(String.valueOf(Id));
		txtSoDuTK.setText(String.valueOf(Sd));
	}

	public void SetMonney(ActionEvent e) {
		if (e.getSource().equals(btn500000))
			txtSTMR.setText(btn500000.getText());
		else if (e.getSource().equals(btn5000000))
			txtSTMR.setText(btn5000000.getText());
		else if (e.getSource().equals(btn1000000))
			txtSTMR.setText(btn1000000.getText());
		else if (e.getSource().equals(btn4000000))
			txtSTMR.setText(btn4000000.getText());
		else if (e.getSource().equals(btn2000000))
			txtSTMR.setText(btn2000000.getText());
		else if (e.getSource().equals(btn3000000))
			txtSTMR.setText(btn3000000.getText());
		else if (e.getSource().equals(btnOth))
			txtSTMR.setEditable(true);

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
		txtSTMR.setEditable(false);
	}

	public void HoanThanh(ActionEvent e) throws NumberFormatException, NotBoundException, IOException {
		if (txtSTMR.getText().equals("")) {
			Alert al=new Alert(AlertType.ERROR);
			al.setContentText("Vui lòng chon só tiền muốn rút");
			al.show();
		}
		else if (txtSTMR.getText().equals(null)) {
			Alert al=new Alert(AlertType.ERROR);
			al.setContentText("Vui lòng chon só tiền muốn rút");
			al.show();
		}
		else if (txtSTMR.getText().equals("0")) {
			Alert al=new Alert(AlertType.ERROR);
			al.setContentText("Vui lòng chon só tiền muốn rút");
			al.show();
		}
		else if (Integer.parseInt(txtSoDuTK.getText())<=Integer.parseInt(txtSTMR.getText())) {
			Alert al=new Alert(AlertType.ERROR);
			al.setContentText("Tài khaonr không đủ");
			al.show();
		}
		else {
			connectDAO dao=new connectDAO();
			boolean r=dao.RutTien(Integer.parseInt(txtIDTK.getText()), Integer.parseInt(txtSTMR.getText()), Integer.parseInt(txtSoDuTK.getText()));
			if (r) {
				txtSoDuTK.setText(String.valueOf((Integer.parseInt(txtSoDuTK.getText())-Integer.parseInt(txtSTMR.getText()))));
				Alert al=new Alert(AlertType.CONFIRMATION);
				al.setHeaderText("Thành Công");
				al.setContentText("Bạn có muốn tiếp tục thực hiện giao dịch");
//				ButtonType yes=new ButtonType("yes",ButtonData.YES);
//				ButtonType no=new ButtonType("no",ButtonData.NO);
//				ButtonType cancel=new ButtonType("cancel",ButtonData.CANCEL_CLOSE);
//				al.getButtonTypes().addAll(yes,no,cancel);
				
				Optional<ButtonType> reOptional = al.showAndWait();
				if (reOptional.get().getButtonData()==ButtonData.OK_DONE) {
					txtSTMR.setText("0");
				}
				else if (reOptional.get().getButtonData()==ButtonData.NO) {
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
				else {
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
		}
	}
}
