import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Application {

	/**
	 * 
	 * Les parametres sont :
	 * nombre de peers publier = peer_publier = 1
	 * nombre de peers consulter = peer_consulter = 1
	 * nombre de sp gestion contenu = superpeer_gestion_contenu = 1
	 * nombre de sp gestion mur = superpeer_gestion_mur = 1
	 * nombre de sp lien amis = superpeer_gestion_amis 1
	 * type de SNS = type_sns = SimiDiaspora
	 * 
	 * Ils ont tous une valeur par defaut.
	 * Quand l'utilisateur execute mod1 sans parametre, on lui affiche un menu qui liste les parametres et leurs valeurs par défaut
	 * Il peut ensuite relancer mod1 en passant en argument des chaines de la forme peer_publier:=100000 qui vont modifier les valeurs des parametres concernes
	 */

	static int peer_publier = 1;
	static int peer_consulter = 1;
	static int superpeer_gestion_contenu = 1;
	static int superpeer_gestion_mur = 1;
	static int superpeer_gestion_amis = 1;
	static String type_sns = "SimiDiaspora";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if(args == null){
			System.out.print("Les parametres sont :\n"+
					"* nombre de peers publier = peer_publier = 1\n" +
					"* nombre de peers consulter = peer_consulter = 1\n"+
					"* nombre de sp gestion contenu = superpeer_gestion_contenu = 1\n"+
					"* nombre de sp gestion mur = superpeer_gestion_mur = 1\n"+
					"* nombre de sp lien amis = superpeer_gestion_amis 1\n"+
					"* type de SNS = type_sns = SimiDiaspora\n");
		}
		else{
			int i=0;
			while(args.length>i){
				String[] argument = args[i].split(":=");
				switch(argument[0]){
				case "peer_publier":
					if(argument.length>1){
						int val = Integer.parseInt(argument[1]);
						peer_publier=val;
					}
					break;
				case "peer_consulter":
					if(argument.length>1){
						int val = Integer.parseInt(argument[1]);
						peer_consulter=val;
					}
					break;
				case "superpeer_gestion_contenu":
					if(argument.length>1){
						int val = Integer.parseInt(argument[1]);
						superpeer_gestion_contenu=val;
					}
					break;
				case "superpeer_gestion_mur":
					if(argument.length>1){
						int val = Integer.parseInt(argument[1]);
						peer_publier=val;
					}
					break;
				case "superpeer_gestion_amis":
					if(argument.length>1){
						int val = Integer.parseInt(argument[1]);
						peer_publier=val;
					}
					break;
				case "type_sns":
					if(argument.length>1){
						type_sns=argument[1];
					}
					break;
				}

				i++;
			}
			String bilan = ("Les parametres sont :\n"+
					"* nombre de peers publier = peer_publier = "+Integer.toString(peer_publier)+" \n" +
					"* nombre de peers consulter = peer_consulter = "+Integer.toString(peer_consulter)+"\n"+
					"* nombre de sp gestion contenu = superpeer_gestion_contenu = "+Integer.toString(superpeer_gestion_contenu)+"\n"+
					"* nombre de sp gestion mur = superpeer_gestion_mur = "+Integer.toString(superpeer_gestion_mur)+"\n"+
					"* nombre de sp lien amis = superpeer_gestion_amis "+Integer.toString(superpeer_gestion_amis)+"\n"+
					"* type de SNS = type_sns = "+ type_sns+"\n");
			System.out.print(bilan);

			genere_platform();
		}



	}

	static void genere_platform() throws IOException{
		FileWriter outFile = new FileWriter("platform.xml", true);
		try {
			PrintWriter out1 = new PrintWriter(outFile);
			try {
				out1.append("<?xml version='1.0'?>\n"+
						"<!DOCTYPE platform SYSTEM \"http://simgrid.gforge.inria.fr/simgrid.dtd\">\n"+
						"<platform version=\"3\">\n");
				
				
				out1.append("</platform>");
			} finally {
				out1.close();
			}
		} finally {
			outFile.close();
		}
	}

}
