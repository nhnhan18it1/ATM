package Connect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;

import ATMCN.CN;
import DataBase.ServerDao;
public class svConnectClient {

	private static ServerSocket serverSocket = null;
	private int port;
	private String host;
	//private ServerDAO dao;
	private ArrayList<Socket> list = new ArrayList<>();
	private static final long serialVersionUID = 1L;

	public svConnectClient() throws RemoteException {
		port = 9999;
		host = "localhost";
		
		list = new ArrayList<>();

		try {

			LocateRegistry.createRegistry(2019);
			Registry registry = LocateRegistry.getRegistry(2019);

			serverSocket = new ServerSocket(port);
			System.out.println("Server running!!!");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				list.add(clientSocket);
				System.out.println(clientSocket.getLocalAddress() + "" + clientSocket.getPort() + "||"
						+ clientSocket.getInetAddress() + "-Clien connect");

				thredserver ts = new thredserver(clientSocket);

				try {

					registry.bind("svtr", new thredserver());
				} catch (RemoteException | AlreadyBoundException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				} finally {
					ts.run();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String[] args) throws RemoteException {
		new svConnectClient();
	}
}
class thredserver extends UnicastRemoteObject implements   Runnable,CN {
	/**
	 * 
	 */

	Socket sk;
	ServerDao dao = new ServerDao();
	Registry registry;
	ArrayList<Integer> listID = new ArrayList<Integer>();

	public thredserver() throws RemoteException {
		sk = null;
		registry = null;
	}

	public thredserver(Socket socket) throws RemoteException {
		sk = socket;

	}

	@Override
	public void run() {

	}

	@Override
	public Vector exSelectBienDong(String arg0) throws RemoteException {
		ServerDao dao=new ServerDao();
		return dao.GetBDSD(arg0);
	}

	@Override
	public Vector exSelectTaiKhoan(String arg0) throws RemoteException {
		ServerDao dao=new ServerDao();
		return dao.selectTK(arg0);
	}

	@Override
	public boolean exUpdate(String arg0) throws RemoteException {
		ServerDao dao=new ServerDao();
		return dao.Update(arg0);
	}
	}
