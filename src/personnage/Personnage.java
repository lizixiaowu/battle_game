package personnage;
import java.util.Scanner;

import Brutal.*;
import strategie.Saleatoire;
import strategie.Sdefensive;
import strategie.Soffensive;
import strategie.Strategie;

public abstract class Personnage {
	private int credit = 30;
	private int dexterite;
	private int force;
	private int resistance;
	private int constitution;
	private int initiative;
	private Strategie strategie;
	private int id;
	public int appartientAJoueur;
	
	public Personnage() {
	}
	
	public Personnage(int j) {
		appartientAJoueur = j;
	}
	
	public Personnage(int id ,int appartientAJoueur,int credit,int dexterite,int force,int resistance,int constitution,int initiative) {
		this.id=id;
		this.appartientAJoueur = appartientAJoueur;
		this.credit=credit;
		this.dexterite=dexterite;
		this.force=force;
		this.resistance=resistance;
		this.constitution=constitution;
		this.initiative=initiative;
		strategie=new Soffensive();
	}

	
	public boolean isMort() {
		boolean i = false;
		return i;
	}
	


	public void setPoint() {}
	
	public void setCombattant() {}
	
	public boolean isAleatoire() {
		boolean i = false;
		return i;
	}
	
	public void initEtudiant(Joueur j) {
		boolean ok = true;
		do {			
			System.out.println("\t attributs originale du combattant" + id + ": ");
			this.printJoueurCombattant();
			System.out.println("Veuillez entrer les points attribués à [Force, Dextérité, Résistance, Constitution, Initiative] ");
			System.out.println("(Séparez chaque nombre par un espace)");
			Scanner sc = new Scanner(System.in);
			boolean isTrue = true;
			while (isTrue) {
				try {
				int f = sc.nextInt();
				int d = sc.nextInt();
				int r = sc.nextInt();
				int c = sc.nextInt();
				int i = sc.nextInt();
				int count = f + d + r + c + i;
				if (f<0 || f>10) {
					System.out.println("Le nombre de points que vous attribuez à la force doit être compris entre 0 et 10, veuillez saisir à nouveau :");
					ok = false;
				}else if (d<0 || d>10){
					System.out.println("Le nombre de points que vous attribuez sur Dextérité doit être compris entre 0 et 10, veuillez saisir à nouveau :");
					ok = false;
				}else if (r<0 || r>10){
					System.out.println("Le nombre de points que vous attribuez à Résistance doit être compris entre 0 et 10, veuillez saisir à nouveau :");
					ok = false;
				}else if (c<0 || c>30){
					System.out.println("Le nombre de points que vous attribuez à Consititution doit être compris entre 0 et 30, veuillez saisir à nouveau :");
					ok = false;
				}else if (i<0 || i>10){
					System.out.println("Le nombre de points que vous attribuez à Initiative doit être compris entre 0 et 10, veuillez saisir à nouveau :");
					ok = false;
				}else if((j.getCredit() - count)<0) {
					System.out.println("Vous n'avez pas assez de points restants, veuillez attribuer à nouveau des points");
					ok = false;
				}else {			
						force = force + f;
						dexterite = dexterite + d;
						resistance = resistance + r;
						credit = credit + c;
						constitution=constitution + c;
						initiative = initiative + i;
						j.setCredit(j.getCredit() - count);
						
						System.out.println("Attributs du soldat après affectation: -----");
						this.printJoueurCombattant();
						System.out.println("Vous avez réussi à attribuer des points à ce combattant. ");
						System.out.println("Credits reste: " + j.getCredit());
						isTrue = false;
						ok = true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Le numéro saisi est incorrect, veuillez le saisir à nouveau.");
				sc.nextLine();
			}
			}
			
		}while(ok == false);
	}
	
	public void printJoueurCombattant() {
		System.out.println("\t Joueur id = " + this.appartientAJoueur);
		System.out.println("\t combattant id = " + this.id);
		System.out.println("\t credit = " + this.credit);
		System.out.println("\t force = " + this.force);
		System.out.println("\t dexterite = " + this.dexterite);
		System.out.println("\t resistance = " + this.resistance);
		System.out.println("\t initiative = " + this.initiative);
		System.out.println("\t constitution = " + this.constitution);
	}
	
	
	public void selectStrategie() {
		boolean sok = true;
		do {
			System.out.println("Vous devez choisir l'une des strategies suivantes pour combattant id="+this.id);
			System.out.println("\t 1. Attaque");
			System.out.println("\t 2. Défense (soigner)");
			System.out.println("\t 3. Aléatoire");
			System.out.println("Veuillez entrer votre choix (1, 2 ou 3)");
			Scanner sc = new Scanner(System.in);
			boolean isTrue = true;
			while (isTrue) {
				try {
					int choix = sc.nextInt();
					if (choix == 1) {
						strategie = new Soffensive();
						System.out.println("La stratégie du combattant "+ id + " est d'attaquer.");
						sok = true;
					}else if(choix == 2) {
						strategie = new Sdefensive();
						System.out.println("La stratégie du combattant "+ id + " est d'soigner.");
						sok = true;
					}else if(choix == 3) {
						strategie = new Saleatoire();
						System.out.println("La stratégie du combattant "+ id + " est d'aleatoire.");
						sok = true;
					}else {
						System.out.println("Saisie incorrecte, veuillez réessayer-----");
						System.out.println("-------------------------------------------");
						sok = false;
					}
					isTrue = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Le numéro saisi est incorrect, veuillez le saisir à nouveau.");
				sc.nextLine();
			}
				
			}

		}while(sok==false);		
	}

	/**
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}

	/**
	 * @return the dexterite
	 */
	public int getDexterite() {
		return dexterite;
	}

	/**
	 * @param dexterite the dexterite to set
	 */
	public void setDexterite(int dexterite) {
		this.dexterite = dexterite;
	}

	/**
	 * @return the force
	 */
	public int getForce() {
		return force;
	}

	/**
	 * @param force the force to set
	 */
	public void setForce(int force) {
		this.force = force;
	}

	/**
	 * @return the resistance
	 */
	public int getResistance() {
		return resistance;
	}

	/**
	 * @param resistance the resistance to set
	 */
	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	/**
	 * @return the constitution
	 */
	public int getConstitution() {
		return constitution;
	}

	/**
	 * @param constitution the constitution to set
	 */
	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}

	/**
	 * @return the initiative
	 */
	public int getInitiative() {
		return initiative;
	}

	/**
	 * @param initiative the initiative to set
	 */
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	/**
	 * @return the strategie
	 */
	public Strategie getStrategie() {
		return strategie;
	}

	/**
	 * @param strategie the strategie to set
	 */
	public void setStrategie(Strategie strategie) {
		this.strategie = strategie;
	}



	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
