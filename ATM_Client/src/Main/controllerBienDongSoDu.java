package Main;

import java.io.IOException;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Vector;

import ATMCN.BienDongSD;
import ATMCN.TaiKhoan;
import DAO.connectDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class controllerBienDongSoDu implements Initializable {

	@FXML
	private TextField txtIDTK;
	@FXML
	private TextField txtSoDuTK;
	@FXML
	private TableView<BienDongSD> tableView;
	@FXML
	private TableColumn<BienDongSD, Integer> ID;
	@FXML
	private TableColumn<BienDongSD, String> ThoiGian;
	@FXML
	private TableColumn<BienDongSD, Integer> ID_TD;
	@FXML
	private TableColumn<BienDongSD, String> LoaiTD;
	@FXML
	private TableColumn<BienDongSD, Integer> STBD;
	@FXML
	private TextField txtID_BTD;
	@FXML
	private TextField txtTenCK_BTD;
	@FXML
	private TextField txtID_TD;
	@FXML
	private TextField txtTenCK_TD;
	@FXML
	private TextField txtThoiGian_TD;
	@FXML
	private TextField txtSoTien_BD;
	@FXML
	private TextField txtLoai_BD;

	ObservableList<BienDongSD> listBD = FXCollections.observableArrayList();

	public void SetInf(int Id, int Sd) {
		txtIDTK.setText(String.valueOf(Id));
		txtSoDuTK.setText(String.valueOf(Sd));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		SetInf(0, 1);

		try {
			SetTableData();
		} catch (NumberFormatException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtIDTK.textProperty().addListener((Observable, oldvalue, newvalue) -> {
			String s=newvalue;
			if (!s.equals("")) {
				RemoveDT();
				connectDAO dao = new connectDAO();
				Vector list;
				try {
					list = dao.GetBDSD(Integer.parseInt(txtIDTK.getText()));
					Enumeration en = list.elements();
					while (en.hasMoreElements()) {
						BienDongSD bdsd = (BienDongSD) en.nextElement();
						listBD.add(bdsd);

					}
				} catch (NumberFormatException | RemoteException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else {
				
			}
		});
	}

	public void SetTableData() throws AccessException, NumberFormatException, RemoteException, NotBoundException {
		connectDAO dao = new connectDAO();
		Vector list = dao.GetBDSD(Integer.parseInt(txtIDTK.getText()));
		Enumeration en = list.elements();
		while (en.hasMoreElements()) {
			BienDongSD bdsd = (BienDongSD) en.nextElement();
			listBD.add(bdsd);

		}
		ID.setCellValueFactory(new PropertyValueFactory<>("IDTK"));
		ID_TD.setCellValueFactory(new PropertyValueFactory<>("IDTK_TD"));
		ThoiGian.setCellValueFactory(new PropertyValueFactory<>("ThoiGian"));
		LoaiTD.setCellValueFactory(new PropertyValueFactory<>("Type"));
		STBD.setCellValueFactory(new PropertyValueFactory<>("BienDong"));
		tableView.setItems(listBD);
		tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			BienDongSD bdsd1 = tableView.getSelectionModel().getSelectedItem();
			txtLoai_BD.setText(bdsd1.getType());
			txtSoTien_BD.setText(String.valueOf(bdsd1.getBienDong()));
			txtThoiGian_TD.setText(bdsd1.getThoiGian());
			try {
				
				SetInForBD_TKHT(bdsd1.getIDTK());
				SetInForBD_TKTD(bdsd1.getIDTK_TD());
				
				
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	public void SetInForBD_TKHT(int ID) throws AccessException, RemoteException, NotBoundException {
		connectDAO dao=new connectDAO();
		Vector list1 = dao.getInfor(ID);
		if (list1.size()==0) {
			txtID_BTD.setText("");
			txtTenCK_BTD.setText("");
		}
		else {
			Enumeration en=list1.elements();
			while (en.hasMoreElements()) {
				TaiKhoan tk = (TaiKhoan) en.nextElement();
				txtID_BTD.setText(String.valueOf(tk.getID()));
				txtTenCK_BTD.setText(tk.getTenChuTK());
				
			}
		}
		
	}
	public void SetInForBD_TKTD(int ID) throws AccessException, RemoteException, NotBoundException {
		connectDAO dao=new connectDAO();
		Vector list1 = dao.getInfor(ID);
		if (list1.size()==0) {
			txtID_TD.setText("");
			txtTenCK_TD.setText("");
		}
		else {
			Enumeration en=list1.elements();
		while (en.hasMoreElements()) {
			TaiKhoan tk = (TaiKhoan) en.nextElement();
			txtID_TD.setText(String.valueOf(tk.getID()));
			txtTenCK_TD.setText(tk.getTenChuTK());
		}
		}
		
	}
	public void RemoveDT() {
		for (int i = 0; i < listBD.size(); i++) {
			listBD.remove(i);
		}
		if (listBD.size()!=0) {
			RemoveDT();
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
