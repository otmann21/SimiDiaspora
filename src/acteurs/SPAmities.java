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
/**
 * Superpeer d'amitié.
 * Ce superpeer va stocker des infos sur les peers et leurs amitiés. Notamment leurs liens, leur code, et leur superpeer de donnée.
 * A la base le SP connait les peers par leur nom, et il connait les liens.
 * Les peers lui envoient un code qu'ils génerent, et recuperent leur amis.
 * @param args
 */
public class SPAmities extends Process {

	public SPAmities(Host host, String name, String[]args) throws NumberFormatException, NoSuchAlgorithmException, UnsupportedEncodingException {
		super(host,name,args);

		HashMap<String, HashMap<String,Object>> infoPeers = new HashMap<String, HashMap<String,Object>>();
		
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
			Task requete = Task.receive(this.host.getName());
			requete.execute();
		}
	}

	
}