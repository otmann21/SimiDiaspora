package acteurs;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.simgrid.msg.Host;
import org.simgrid.msg.HostFailureException;
import org.simgrid.msg.Msg;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Task;
import org.simgrid.msg.Process;
import org.simgrid.msg.TimeoutException;
import org.simgrid.msg.TransferFailureException;

import taches.Message;
/**
 * Superpeer de donnée.
 * Un superpeer se constitue des donnees sous la forme d'une HashMap de byte.
 * Un superpeer se met en ecoute des requetes envoyees sur sa boite aux lettres.
 * Une requete est un byte[] qui correspond a un SHA-1. Si on a une entrée qui y correspond, on la renvoie, sinon on renvoit null.
 * @author otmann
 *
 */
public class SuperPeer extends Process {

	public SuperPeer(Host host, String name, String[]args) throws NumberFormatException, NoSuchAlgorithmException, UnsupportedEncodingException {
		super(host,name,args);

		this.host.setData(genData(Integer.parseInt(args[0])));
	} 

	public void main(String[] args) throws MsgException {
		//On met le SuperPeer à l'écoute sur la boite aux lettres qui a son nom
		Msg.info(this.host.getName() + " ecoute les requetes sur sa boite aux lettres.");
		while(true){
			Task.listen(this.host.getName());
			Task requete = Task.receive(this.host.getName());
			requete.execute();
		}
	}
	
	/**
	 * Cette fonction sert a generer une HashMap avec des bytes comme valeur, pour mettre qqch dans le data du host.
	 * @param nb
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public HashMap<String, String> genData(int nb) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		HashMap<String, String> data = new HashMap<String, String>();
		for(int i=0;i<nb;i++){
			md.reset();
			md.update(("Donnee " + Integer.toString(i)).getBytes());
			byte[] hash = md.digest();
			String s = new String(hash, "UTF-8");
			data.put(s, "Donnee " + Integer.toString(i));
		}
		return data;
	}

	public String reponse(byte[] requete, String sender) throws UnsupportedEncodingException, TransferFailureException, HostFailureException, TimeoutException{
		//On convertit la requete en string pour l'utiliser comme cle de la hashmap.
		String req = new String(requete, "UTF-8");
		String res=null;
		if(((HashMap<String, String>) host.getData()).containsKey(req)){res = ((HashMap<String, String>) host.getData()).get(req);}
		Message reponseMessage = new Message(res);
		reponseMessage.send(sender);
		return res;
	}
	
	public boolean ajouteDonnee(String donnee) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(donnee.getBytes());
		String cle = new String(md.digest(), "UTF-8");
		((HashMap<String, String>) host.getData()).put(cle, donnee);
		
		return true;
	}
}
