/*
 *  Generated Main Class. Most user won't have to modify it.
 */
package SimiDiaspora;
import org.simgrid.msg.Msg;
import org.simgrid.msg.NativeException;
 
/**
 * 
 * @author otmann
 *
 */
public class SimiDiasporaMain  {
    
    public static void main(String[] args) throws NativeException {    
        /* check usage error and initialize with defaults */
        if (args.length == 0){
            args = new String[2];
            System.out.print("** WARNING **\nusing default values:\n"+
                "SimiDiaspora_platform.xml SimiDiaspora_deployment.xml\n\n");
            args[0] = "SimiDiaspora_platform.xml";
            args[1] = "SimiDiaspora_deployment.xml";
        }else if(args.length != 2) {
            System.out.print("** ERROR **\n"+
                "Usage:\nplatform_file deployment_file\n");
            System.out.print("Example:\nSimiDiaspora_platform.xml SimiDiaspora_deployment.xml\n");
            System.exit(1);
        }	
	    /* initialize the MSG simulation. Must be done before anything else (even logging). */
        Msg.init(args);
        Msg.info("Simulation start...");
	    /* construct the platform and deploy the application */
        Msg.createEnvironment(args[0]);
        Msg.deployApplication(args[1]);
	    
	    /*  execute the simulation. */
        Msg.run();
        Msg.info("Simulation time:"+Msg.getClock());
    }
}
