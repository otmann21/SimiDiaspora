package taches;
import java.util.ArrayList;

import org.simgrid.msg.HostFailureException;
import org.simgrid.msg.Task;
import org.simgrid.msg.TaskCancelledException;

/**
 * Cette classe rassemble tous les messages que les peers peuvent s'envoyer. Elle est bordelique, il va falloir mieux la structurer, 
 * avec notamment cet argument type qui donne le type du message (texte, haché, code...)
 * Vu le nombre de types de messages, peut etre faire une classe générique Message<T>
 * 
 * @author otmann
 *
 */
public class Message extends Task{
	
	protected String message;
	protected byte[] hash;
	protected byte[] code;
	protected ArrayList<String> list;
	protected int type;
	
	public Message(String message){
		super();
		this.message = message;
		this.type=0;
	}
	
	public Message(String message, int type){
		super();
		this.message = message;
		this.type=type;
	}
	
	public Message(ArrayList<String> list, int type){
		this.list=list;
		this.type=type;
	}
	
	public Message(byte[] hash){
		super();
		this.hash=hash;
		this.type=1;
	}
	
	public Message(byte[] code, int type){
		super();
		this.code=code;
		this.type=2;
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
	
	public int getType(){
		return type;
	}
	
	public byte[] getCode(){
		return code;
	}
}