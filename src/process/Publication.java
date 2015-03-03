package process;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.simgrid.msg.Host;
import org.simgrid.msg.HostFailureException;
import org.simgrid.msg.Msg;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;
import org.simgrid.msg.Task;
import org.simgrid.msg.TimeoutException;
import org.simgrid.msg.TransferFailureException;

import taches.AjouteDonnee;
import taches.Message;
/**
 * On envoie une publi sur le sp.
 * args[0] : le sp ; args[1] : la donnee
 * 
 * @author otmann
 *
 */
public class Publication extends Process {

	private byte[] monCode;
	private String mboxPublication;
	private HashMap<String, byte[]> amis;


	public Publication(Host host, String name, String[]args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		super(host,name,args);
		this.amis=(HashMap<String, byte[]>) host.getData();
		monCode = new byte[]{01};
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(monCode);
		mboxPublication = new String(md.digest(), "UTF-8");
	}

	public void main(String[] args) throws MsgException {
		Process.sleep(1400);
		AjouteDonnee env = new AjouteDonnee(args[1]);
		Msg.info(Host.currentHost().getName()+" ajoute une donnée sur " + args[0] + ".");
		env.send(args[0]);
		MessageDigest md;
		try {//on met le hash de la publi sur ma mbox de publi
			md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(args[1].getBytes());
			Msg.info(Host.currentHost().getName()+" donne à ses amis le hash de l'info qu'il a ajouté sur " + args[0] + ".");
			(new Message(md.digest(), 1)).send(this.mboxPublication);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//je ping mes potos pour leur dire que j'ai publié un truc
		for(String ami : this.amis.keySet()){
			Message ping = new Message("ping");
			ping.setNomPeer(host.getName());
			ping.send(ami);
		}
	}
}