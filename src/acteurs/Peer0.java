package acteurs;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.simgrid.msg.Host;
import org.simgrid.msg.Msg;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Process;

import taches.AjouteDonnee;
import taches.Message;
/**
 * Ce peer envoie une donnee au superpeer0, puis le signale sur la boite aux lettres de ses amis, en y mettant la cle.
 * La boite aux lettres de ses amis a pour nom le SHA1 de son code.
 * 
 * args0 est le nom du superpeer utilisé, args1 est la donnee traitée.
 * 
 * @author otmann
 *
 */
public class Peer0 extends Process {
	
	private byte[] code;
	private String mbox;
	
	public Peer0(Host host, String name, String[]args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		super(host,name,args);
		code = new byte[]{42, 23, 76, 21};
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(code);
		mbox = new String(md.digest(), "UTF-8");
	}

	public void main(String[] args) throws MsgException {
		
		AjouteDonnee env = new AjouteDonnee(args[1]);
		Msg.info(Host.currentHost().getName()+" ajoute une donnée sur " + args[0] + ".");
		env.send(args[0]);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(args[1].getBytes());
			Msg.info(Host.currentHost().getName()+" donne à ses amis le hash de l'info qu'il a ajouté sur " + args[0] + ".");
			(new Message(md.digest())).send(this.mbox);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}