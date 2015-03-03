package process;
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
import taches.Requete;

/**
 * S'occupe de la connexion au sp, puis ecoute la boite aux lettres du peer.
 * Relou en fait parce que y a plusieurs boites aux lettres a ecouter. Celle du peer, et celles des publications de ses amis.
 * Eventuellement : quand on publie un truc, on met le hash dans sa boite aux lettres de publications, et on ping ses amis pour qu'ils aillent la voir.
 * @author otmann
 *
 */

public class Reception extends Process {

	private byte[] code;
	private String mbox;
	private HashMap<String, byte[]> amis;

	public Reception(Host host, String name, String[]args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		super(host,name,args);

		code = new byte[]{42, 23, 76, 21};
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.reset();
		md.update(code);
		mbox = new String(md.digest(), "UTF-8");
		amis = new HashMap<String, byte[]>();
	}

	public void main(String[] args) throws MsgException {
		Msg.info(Host.currentHost().getName() + " regarde si son ami peer0 publie qqch.");
		Task.listen(mbox);
		Task msg = Task.receive(mbox);
		if(msg instanceof Message){
			if(((Message) msg).getType()==1){ //Un ami nous dit qu'il
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
			else if(((Message) msg).getType()==2){
				if(this.amis.containsKey(((Message) msg).getNomPeer())){
					this.amis.put(((Message)msg).getNomPeer(),((Message)msg).getCode());
				}
			}

		}
	}

	/*
	 * Le peer se connecte à son superpeer pour lui donner son code et obtenir ses amis.
	 */
	public void connexion(String sp) throws TransferFailureException, HostFailureException, TimeoutException{
		this.code = new byte[]{42, 23, 76, 21};
		Message envoiCode = new Message(this.code, 2);
		envoiCode.send(sp);
		Task.listen(Host.currentHost().getName());
		Task listeAmis = Task.receive(Host.currentHost().getName());
		if(listeAmis instanceof Message && ((Message) listeAmis).getType()==3){
			for(String a : ((Message)listeAmis).getList()){
				this.amis.put(a, null);
			}
		}
	}
}