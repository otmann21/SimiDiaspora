package taches;
import java.io.UnsupportedEncodingException;

import org.simgrid.msg.HostFailureException;
import org.simgrid.msg.Process;
import org.simgrid.msg.Task;
import org.simgrid.msg.TaskCancelledException;
import org.simgrid.msg.TimeoutException;
import org.simgrid.msg.TransferFailureException;

/**
 * 
 * @author otmann
 *
 */
public class Requete extends Task{
	
	protected byte[] cle;
	public Requete(byte[] cle){
		super();
		this.cle = cle;
	}
	
	public void execute() throws HostFailureException,TaskCancelledException {
		String reponse;
		try {
			reponse = ((acteurs.SuperPeer) Process.currentProcess()).reponse(this.cle, this.getSource().getName());
//			Msg.info("REPONSE : \"" + reponse+"\".");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransferFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}