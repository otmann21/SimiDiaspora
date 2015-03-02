package acteurs;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.simgrid.msg.Host;
import org.simgrid.msg.HostFailureException;
import org.simgrid.msg.Msg;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;
import org.simgrid.msg.TimeoutException;
import org.simgrid.msg.TransferFailureException;

import taches.AjouteDonnee;
import taches.Message;
import taches.Requete;

/**
 * 
 * @author otmann
 *
 */

public class Peer extends Process {
	public Peer(Host host, String name, String[]args) {
		super(host,name,args);
	}

	public void main(String[] args) throws MsgException {
		try {
			envoiRequete();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		envoiDonnee("Otmann est stylé");
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(("Otmann est stylé").getBytes());
			Requete req = new Requete(md.digest());
			Process.sleep(1000);
			Msg.info(Host.currentHost().getName() + " envoie une requete a superpeer0");
			req.send("superpeer0");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void envoiRequete() throws NoSuchAlgorithmException, TransferFailureException, HostFailureException, TimeoutException{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(("Donnee 4").getBytes());
		Requete req = new Requete(md.digest());
		Process.sleep(1000);
		Msg.info(Host.currentHost().getName() + " envoie une requete a superpeer0");
		req.send("superpeer0");

		md.reset();
		md.update((byte) 16);
		Requete req2 = new Requete(md.digest());
		Process.sleep(1400);
		Msg.info(Host.currentHost().getName() + " envoie une requete a superpeer0");
		req2.send("superpeer0");
	}

	public void envoiDonnee(String donnee) throws TransferFailureException, HostFailureException, TimeoutException{
		AjouteDonnee env = new AjouteDonnee(donnee);
		env.send("superpeer0");
	}

	public void envoiMessage() throws HostFailureException, TransferFailureException, TimeoutException{
		Message msg = new Message("prout");
		Process.sleep(8200);
		msg.send("superpeer0");
	}
}