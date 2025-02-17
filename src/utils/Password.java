package utils;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
	/*
	 * Pour plus d'informations sur l'utilisation de 'BCrypt'
	 * 
	 * https://www.mindrot.org/projects/jBCrypt/#download
	 */
	public static String hasherMotDePasse(String motDePasse) {
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt(12));
    }
		
	public static boolean verifierMotDePasse(String motDePasse, String motDePasseHache) {
        return BCrypt.checkpw(motDePasse, motDePasseHache);
    }
}
