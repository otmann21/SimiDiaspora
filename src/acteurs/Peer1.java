package acteurs;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.simgrid.msg.Host;
import org.simgrid.msg.Msg;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Task;
import org.simgrid.msg.Process;
import taches.Message;
import taches.Requete;

/**
 * peer1 ecoute la mbox de son ami peer0.
 * Il recoit l'info que ce dernier a uploadé une donnée sur superpeer0, alors il va voir ce que c'est en la demandant a superpeer0.
 * 
 * args0 est le superpeer utilisé.
 * @author otmann
 *
 */

public class Peer1 extends Process {

	private byte[] code;
	private String mbox;

	public Peer1(Host host, String name, String[]args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		super(host,name,args);


		//pour l'instant, on donne directement a peer1 le code de peer0...
		//il faudra eventuellement passer par un autre superpeer pour les relations amicales.
		code = new byte[]{42, 23, 76, 21};
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(code);
		mbox = new String(md.digest(), "UTF-8");
	}

	public void main(String[] args) throws MsgException {
		Msg.info(Host.currentHost().getName() + " regarde si son ami peer0 publie qqch.");
		Task.listen(mbox);
		Task msg = Task.receive(mbox);
		if(msg instanceof Message){
			byte[] hash = ((Message) msg).getHash();
			Requete req = new Requete(hash);
			Msg.info(Host.currentHost().getName() + " voit que peer0 a mis qqch sur " + args[0] + ". Il fait une requete a " + args[0] + " en se servant du hash que peer0 a distribué à ses amis.");
			req.send(args[0]);
			Task.listen(Host.currentHost().getName());
			Task reponse = Task.receive(Host.currentHost().getName());
			if(reponse instanceof Message){
				String contenu = ((Message)reponse).getMessage();
				Msg.info(Host.currentHost().getName() + " a récupéré sur " + args[0] + " la donnée : \"" + contenu + "\".");
			}
		}
	}
}