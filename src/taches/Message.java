package taches;
import org.simgrid.msg.HostFailureException;
import org.simgrid.msg.Task;
import org.simgrid.msg.TaskCancelledException;

/**
 * 
 * @author otmann
 *
 */
public class Message extends Task{
	
	protected String message;
	protected byte[] hash;
	
	public Message(String message){
		super();
		this.message = message;
	}
	
	public Message(byte[] hash){
		super();
		this.hash=hash;
	}
	
	public void execute() throws HostFailureException,TaskCancelledException {
		//Msg.info("MESSAGE : \"" + message+"\".");
	}
	
	public String getMessage(){
		return message;
	}
	
	public byte[] getHash(){
		return hash;
	}
	
}