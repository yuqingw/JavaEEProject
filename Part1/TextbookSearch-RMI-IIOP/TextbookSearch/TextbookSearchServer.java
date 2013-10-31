package textbooksearch;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TextbookSearchServer extends Remote {

  // this method is invoked by remote clients.
  // it is implemented by the remote object
	public boolean search(int courseCode)
		throws RemoteException;

	public String getTextbook(int courseCode)
		throws RemoteException;

	public int checkCopies(String textbook)
		throws RemoteException;
	

}
