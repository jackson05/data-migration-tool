package launcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Configurations {
	
	public static final String JACKSON_PROPS="migration.properties";
	public static final String PREFIX=";";
	
	
	public String[] getDataBases() {
		String databases[];
		for(String s:props()) {
			if(s.toUpperCase().startsWith("DATABASES") && s.endsWith(PREFIX)) {
				databases =(s.substring("DATABASES".length()+2, s.length()-2)).split(",");
				return  databases;
			}
		}
		return null;
	}
	
	public List<String> props() {
		List<String>lines=new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(JACKSON_PROPS))) {
            String line;
            while ((line = br.readLine()) != null) {
            	lines.add(line);
               
            }
        } catch (IOException e) {
            e.printStackTrace();
            
        }
		return lines;
		
	}
	
	/*
	 * Retourne le types du RDBMS de source configuré
	 */
	public String getSrcRDBMS() {
		for(String s:props()) {
			if(s.startsWith("SRC_RDBMS") && s.endsWith(PREFIX)) {
				return s.substring("SRC_RDBMS".length()+1,s.length()-1);
			}
		}
		return null;
	}
	
	/*
	 * Retourne  l'adress du serveur de source des donnée configurée
	 * */
	
	public String getSrcHost() {
		for(String s:props()) {
			if(s.startsWith("SRC_HOST") && s.endsWith(PREFIX)) {
				return s.substring("SRC_HOST".length()+1,s.length()-1);
				
			}
		}
		return null;
	}
	
	/*
	 *	Retourne le numero de port d'ecoute à partir de la source des données  configuré
	 * */
	public String getSrcPort() {
		for(String s:props()) {
			if(s.startsWith("SRC_PORT") && s.endsWith(PREFIX)) {
				return s.substring("SRC_PORT".length()+1,s.length()-1);
			}
		}
		return null;
	}
	
	
	
	/*
	 * Retourne l'utilisateur du serveur de source des données configurée 
	 */
	
	public String getSrcUser() {
		for(String s:props()) {
			if(s.startsWith("SRC_USER") && s.endsWith(PREFIX)) {
				return s.substring("SRC_USER".length()+1,s.length()-1);
			}
		}
		return null;
	}
	
	
	
	public String getSrcPassword() {
		for(String s:props()) {
			if(s.startsWith("SRC_PSWD") && s.endsWith(PREFIX)) {
				return s.substring("SRC_PSWD".length()+1,s.length()-1);
			}
		}
		return null;
	}
	
	/*
	 * Retourne le type du RDBMS de destination des données configuré
	 */
	public String getDestRDBMS() {
		for(String s:props()) {
			if(s.startsWith("SRC_RDBMS") && s.endsWith(PREFIX)) {
				return s.substring("SRC_RDBMS".length()+1,s.length()-1);
			}
		}
		return null;
	}
	
	
	/*
	 * Retourne l'adress du serveur de destination des données
	 */
	public String getDestHost() 
	{
		for(String s:props()) {
			if(s.startsWith("DEST_HOST") && s.endsWith(PREFIX)) {
					return s.substring("DEST_HOST".length()+1,s.length()-1);
			}
		}
			return null;
	}
	
	/*
	 * Retourne le numero de port d'ecoute du destination des données
	 */
	public String getDestPort() {
		for(String s:props()) {
			if(s.startsWith("DEST_PORT") && s.endsWith(PREFIX)) {
				return s.substring("DEST_PORT".length()+1,s.length()-1);
			}
		}
		return null;
	}
	
	/*
	* Retourne l'utilisateur du serveur destination des données configuré
	*/
	public String getDestUser() {
		for(String s:props()) {
			if(s.startsWith("DEST_USER") && s.endsWith(PREFIX)) {
				return s.substring("DEST_USER".length()+1,s.length()-1);
			}
		}
		return null;
	}
	
	/*
	* Retourne l'utilisateur du serveur destination des données configuré
	*/
	public String getDestPsswd() {
		for(String s:props()) {
			if(s.startsWith("DEST_PSWD") && s.endsWith(PREFIX)) {
				return s.substring("DEST_PSWD".length()+1,s.length()-1);
			}
		}
		return null;
	}
}
