package Brutal;

import java.util.ArrayList;
import java.util.HashMap;

import zone.Bibliotheque;
import zone.BureauDesEtudiants;
import zone.HalleSportive;
import zone.HallesIndustrielles;
import zone.QuartierAdministratif;
import zone.Zone;

public class Partie {
	private static Partie instance = new Partie();
	private Joueur j1 = new Joueur(1);
	private Joueur j2 = new Joueur(2);
	private Zone bibliotheque = new Bibliotheque("La bibliothèque",1); 
	private Zone bureauDesEtudiants = new BureauDesEtudiants("Le Bureau des Etudiants",2);
	private Zone hallesIndustrielle =new HallesIndustrielles("Les Halles industrielles",3);
	private Zone halleSportive = new HalleSportive("La Halle Sportive",4);
	private Zone quartierAdministratif = new QuartierAdministratif("Le Quartier Administratif",5);
	private ArrayList<Zone> zonePerdu=new ArrayList<Zone>();
	private HashMap<Integer,Zone> zoneEnCours=new HashMap<Integer,Zone>();
	private HashMap<Integer,Zone> zoneTemp = new HashMap<>();
	private static int joueur1Gagne;
	private static int joueur2Gagne;
	
	private Partie() {
		zoneEnCours.put(1,bibliotheque);
		zoneEnCours.put(2,bureauDesEtudiants);
		zoneEnCours.put(3,hallesIndustrielle);
		zoneEnCours.put(4,halleSportive);
		zoneEnCours.put(5,quartierAdministratif);
		};
	
	
	
	
	public static Partie getInstance() {
		
		return instance;
	}

	
	
	/**
	 * @return the zoneTemp
	 */
	public HashMap<Integer, Zone> getZoneTemp() {
		return zoneTemp;
	}




	/**
	 * @param zoneTemp the zoneTemp to set
	 */
	public void setZoneTemp(HashMap<Integer, Zone> zoneTemp) {
		this.zoneTemp = zoneTemp;
	}




	/**
	 * @return the zonePerdu
	 */
	
	public ArrayList<Zone> getZonePerdu() {
		return zonePerdu;
	}

	public void setZonePerdu(ArrayList<Zone> zonePerdu) {
		this.zonePerdu = zonePerdu;
	}


	public HashMap<Integer,Zone> getZoneEnCours() {
		return zoneEnCours;
	}

	public void setZoneEnCours(HashMap<Integer,Zone> zoneEnCours) {
		this.zoneEnCours = zoneEnCours;
	}

	public Joueur getJ1() {
		return j1;
	}

	void setJ1(Joueur j1) {
		this.j1 = j1;
	}


	public Joueur getJ2() {
		return j2;
	}

	public void setJ2(Joueur j2) {
		this.j2 = j2;
	}
	
	public Zone getBibliotheque() {
		return bibliotheque;
	}

	public void setBibliotheque(Bibliotheque bibliotheque) {
		this.bibliotheque = bibliotheque;
	}

	public Zone getBureauDesEtudiants() {
		return bureauDesEtudiants;
	}

	public void setBureauDesEtudiants(BureauDesEtudiants bureauDesEtudiants) {
		this.bureauDesEtudiants = bureauDesEtudiants;
	}

	public Zone getHallesIndustrielle() {
		return hallesIndustrielle;
	}

	public void setHallesIndustrielle(HallesIndustrielles hallesIndustrielle) {
		this.hallesIndustrielle = hallesIndustrielle;
	}

	public Zone getHalleSportive() {
		return halleSportive;
	}

	public void setHalleSportive(HalleSportive halleSportive) {
		this.halleSportive = halleSportive;
	}

	public Zone getQuartierAdministratif() {
		return quartierAdministratif;
	}

	public void setQuartierAdministratif(QuartierAdministratif quartierAdministratif) {
		this.quartierAdministratif = quartierAdministratif;
	}




	/**
	 * @return the joueur1Gagne
	 */
	public static int getJoueur1Gagne() {
		return joueur1Gagne;
	}




	/**
	 * @param joueur1Gagne the joueur1Gagne to set
	 */
	public static void setJoueur1Gagne(int joueur1Gagne) {
		Partie.joueur1Gagne = joueur1Gagne;
	}




	/**
	 * @return the joueur2Gagne
	 */
	public static int getJoueur2Gagne() {
		return joueur2Gagne;
	}




	/**
	 * @param joueur2Gagne the joueur2Gagne to set
	 */
	public static void setJoueur2Gagne(int joueur2Gagne) {
		Partie.joueur2Gagne = joueur2Gagne;
	}
	
	

}
