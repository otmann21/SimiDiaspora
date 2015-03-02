package acteurs;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import org.simgrid.msg.Host;
import org.simgrid.msg.Msg;
import org.simgrid.msg.MsgException;
import org.simgrid.msg.Task;
import org.simgrid.msg.Process;

import taches.Message;
/**
 * Superpeer d'amitié.
 * Ce superpeer va stocker des infos sur les peers et leurs amitiés. Notamment leurs liens, leur code, et leur superpeer de donnée.
 * A la base le SP connait les peers par leur nom, et il connait les liens.
 * Les peers lui envoient un code qu'ils génerent, et recuperent leur amis.
 * @param args
 */
public class SPAmities extends Process {
	
	HashMap<String, HashMap<String,Object>> infoPeers;

	public SPAmities(Host host, String name, String[]args) throws NumberFormatException, NoSuchAlgorithmException, UnsupportedEncodingException {
		super(host,name,args);

		infoPeers = new HashMap<String, HashMap<String,Object>>();
		
		//On met des liens d'amitiés manuellement dans le code java.
		//A l'avenir, un fichier XML devra stocker ces relations.
		ArrayList<String> amis0 = new ArrayList<String>();
		amis0.add("peer1");
		ArrayList<String> amis1 = new ArrayList<String>();
		amis0.add("peer0");
		
		HashMap<String,Object> donnees0 = new HashMap<String,Object>();
		HashMap<String,Object> donnees1 = new HashMap<String,Object>();
		donnees0.put("amis", amis0);
		donnees1.put("amis", amis1);
		
		infoPeers.put("peer0", donnees0);
		infoPeers.put("peer1", donnees1);
	} 

	public void main(String[] args) throws MsgException {
		//On met le SuperPeer à l'écoute sur la boite aux lettres qui a son nom
		Msg.info(this.host.getName() + " ecoute les requetes sur sa boite aux lettres.");
		while(true){
			Task.listen(this.host.getName());
			Task message = Task.receive(this.host.getName());
			//on regarde si le message est un peer qui envoie son code
			if(message instanceof Message && ((Message) message).getType()==2){
				if(this.infoPeers.containsKey(message.getSource().getName())){
					//on ajoute le code aux donnée du peer
					this.infoPeers.get(message.getSource().getName()).put("code", ((Message) message).getCode());
					//on envoie le code a chacun de ses amis
					//on envoie aux peer les noms de ses amis dans une ArrayList. C'est un Message de type 3.
					ArrayList<String> listeAmis= new ArrayList();
					for(Object amiDuPeer : ((ArrayList) this.infoPeers.get(message.getSource().getName()).get("amis"))){
						Message msgCode = new Message(((Message) message).getCode(),2);
						msgCode.send((String) amiDuPeer);
						listeAmis.add((String) amiDuPeer);
					}
					//envoi de la liste des amis
					Message msgListeAmis = new Message(listeAmis, 3);
					msgListeAmis.send(message.getSource().getName());					
				}
			}
			
		}
	}
	

	
}