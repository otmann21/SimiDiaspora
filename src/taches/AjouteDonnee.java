package taches;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.simgrid.msg.HostFailureException;
import org.simgrid.msg.Process;
import org.simgrid.msg.Task;
import org.simgrid.msg.TaskCancelledException;

/**
 * 
 * @author otmann
 *
 */
public class AjouteDonnee extends Task{
	
	protected String donnee;
	public AjouteDonnee(String donnee){
		super();
		this.donnee = donnee;
	}
	
	public void execute() throws HostFailureException,TaskCancelledException {
		try {
			((acteurs.SuperPeer) Process.currentProcess()).ajouteDonnee(donnee);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Msg.info("DEMANDE D'AJOUT DE : "+ this.donnee + ".");
	}
	
}