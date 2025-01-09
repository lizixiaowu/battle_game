package Brutal;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.plaf.synth.SynthScrollPaneUI;

import personnage.*;
import strategie.*;
import zone.Zone;


public class Joueur {
	private String nomJ;
	private int id;	
	private int credit;
	private HashMap<Integer,Personnage> reserviste = new HashMap<Integer,Personnage>();
	private ArrayList<Integer> reservisteKey = new ArrayList<Integer>();
	private HashMap<Integer,Personnage> etudiant = new HashMap<Integer,Personnage>();

	public void initJoueur(int j) {	
		System.out.println("-----Welcome to C'est Brutal!-----");
		System.out.println("veuillez entrer les informations du joueur" + id);
		System.out.println("S'il vous plaît entrez votre nom:");
		Scanner sc = new Scanner(System.in);
		nomJ = sc.nextLine();
		credit = 400;
		this.printJoueurInformation();
		for(int i=1;i<=20;i++) {
			if(i==1) {
				Personnage p = new MaitreDuGobi(j);
				p.setId(i);
				etudiant.put(i,p);
			}else if(i<=5 && i>1){
				Personnage p = new EtudiantsDelite(j);
				p.setId(i);
				etudiant.put(i,p);
			}else {
				Personnage p = new Etudiant(j);
				p.setId(i);
				etudiant.put(i,p);

			}
		}
		AttribuerDesPoints();
		
	} 
	
	public void AttribuerDesPoints() {
		System.out.println("Vous avez un total de 20 combattants, veuillez leur attribuer des points à tour de rôle: ");
		Set<Integer> keyset = etudiant.keySet();
		for (Integer k :keyset) {

			if (credit>0) {
				if (k==1) {
				System.out.println("C'est un Maitre du gobi: ");
				}else if (k<=5 && k>1) {
					System.out.println("C'est un étudiant d’élite: ");
				}else {
					System.out.println("C'est un étudiant normal: ");
				}
				etudiant.get(k).initEtudiant(this);
			}else if (credit==0) {
				System.out.println("Étant donné que les crédits attribuables restants sont 0, les crédits ne peuvent pas être attribués.");
			}
			
			etudiant.get(k).selectStrategie();
			System.out.println("------------------------");
			
		}
	}
	
	public int ShowAvailableCombattantIds(HashMap<Integer, Personnage> hm) {
		int countNbCombattant = 0;
		StringBuffer sBuffer = new StringBuffer();
		Set<Integer> key = hm.keySet();
		for (Integer i :key) {
			sBuffer.append(i);
			sBuffer.append(" ");
			countNbCombattant++;
			
		}
		System.out.println("Les ids de combattant que vous pouvez choisir sont : "+sBuffer);
		return countNbCombattant;
	}
	
	public int ShowAvailableCombattantIds(ArrayList<Integer> arr) {
		int countNbCombattant = 0;
		StringBuffer sBuffer = new StringBuffer();
		for (Integer i :arr) {
			sBuffer.append(i);
			sBuffer.append(" ");
			countNbCombattant++;
			
		}
		System.out.println("Les ids de combattant que vous pouvez choisir sont : "+sBuffer);
		return countNbCombattant;
	}
	
	public void printJoueurInformation() {
		System.out.println("------ Vous avez créé votre compte avec succès------");
		System.out.println("\t Votre nom = " + nomJ);
		System.out.println("\t Votre Joueur id = " + id);
		System.out.println("\t Total credits that can be allocated = " + credit);
		System.out.println("Merci pour votre création.");
		System.out.println("------------------------------------------------------");
	}
	
	
	public Joueur(int id) {
		super();
		this.id = id;
	}
	
	public void addReservist() {
		System.out.println("Veuillez sélectionner cinq réservistes ");
		System.out.println("(entrez l'id du guerrier, Veuillez utiliser la touche Entrée pour séparer)");
		Scanner sc = new Scanner(System.in);
		int numberOfReserviste = 0;
		//Après le signe inférieur à est le nombre de personnel de réserve
		while (numberOfReserviste <5) {
			int i1 = sc.nextInt();
			//Si l'id n'existe pas, il sera affiché comme nul
			if (etudiant.get(i1) == null) {
				System.out.println("Le combattant avec id = " +i1 +" n'existe pas, veuillez le saisir à nouveau");
			}else if (isAdd(i1, reserviste)==true) {

				System.out.println("Le combattant avec id = " +i1 +" a été dans le list de reserviste, veuillez le saisir à nouveau");
			}else {
				
				reserviste.put(i1,etudiant.get(i1));
				reservisteKey.add(i1);
				etudiant.remove(i1);
				numberOfReserviste ++;	
			
			}
					
		}
	}
	
	//Déterminez si le soldat a été ajouté
	public boolean isAdd(int id,HashMap<Integer, Personnage> hm) {
		boolean isadd = false;
		Set<Integer> s= hm.keySet();
		for(int i : s) {
			if (i == id) {
				isadd = true;
			}
		}
		return isadd;
	}
	
	//traverser hashMap
	public void afficherCombattantAttribut(HashMap<Integer, Personnage> map) {
		Set<Integer> kset = map.keySet();
		for(Integer i : kset) {
			Personnage p = map.get(i);
			System.out.println("\t combattant id = " + i);
			System.out.println("\t credit = " + p.getCredit());
			System.out.println("\t force = " + p.getForce());
			System.out.println("\t dexterite = " + p.getDexterite());
			System.out.println("\t resistance = " + p.getResistance());
			System.out.println("\t initiative = " + p.getInitiative());
			System.out.println("-----------------------------------");
		}	
	}
	
	
	//Fonction pour séparer les troupes de la réserve
	public void mouvementDeTroupesDesReserves(HashMap<Integer,Zone> zEnCours) {
		boolean isok=true;
		ShowAvailableCombattantIds(reserviste);
		if (reserviste.isEmpty()==false) {
			System.out.println("Vous pouvez déployer les soldats ci-dessus dans des zones qui se battent encore : ");
			printZoneEnCours(zEnCours);
			try {
				for (int j = 0;j < reservisteKey.size();j++) {	
					int id = reservisteKey.get(j);
					if (reserviste.get(id)!=null) {
						Personnage p = reserviste.get(id);
						System.out.println(" ");
						System.out.println("Pour Joueur"+p.appartientAJoueur+",le soldat avec id="+id+",veuillez entrer le numéro de la zone correspondante dans laquelle vous souhaitez le placer :");				
						System.out.println(" ");
						Scanner sc = new Scanner(System.in);
						OVER:
						do{
							int i1 = sc.nextInt();
							if (i1>=1&&i1<=5) {
								p.selectStrategie();
								if (p.appartientAJoueur==1) {
									try {
										zEnCours.get(i1).getArrJ1orderByCredit().add(p);
										zEnCours.get(i1).trierParCredit(zEnCours.get(i1).getArrJ1orderByCredit());
										zEnCours.get(i1).getArrTotalOrdreByIntiative().add(p);
										reserviste.remove(id);
										//reservisteKey.remove(j);
										//ShowAvailableCombattantIds(reserviste);
										//ShowAvailableCombattantIds(reservisteKey);
									} catch (NullPointerException e) {
										// TODO Auto-generated catch block
										System.out.println(" ");
										System.out.println("La bataille dans cette zone est terminée et plus aucune troupe ne peut être envoyée.");
										printZoneEnCours(zEnCours);
										j--;
										break OVER;
									}
								}else {
									try {
										zEnCours.get(i1).getArrJ2orderByCredit().add(p);
										zEnCours.get(i1).trierParCredit(zEnCours.get(i1).getArrJ2orderByCredit());
										zEnCours.get(i1).getArrTotalOrdreByIntiative().add(p);
										reserviste.remove(id);
										//reservisteKey.remove(j);
										//ShowAvailableCombattantIds(reserviste);
										//ShowAvailableCombattantIds(reservisteKey);
									} catch (NullPointerException e) {
										System.out.println("La bataille dans cette zone est terminée et plus aucune troupe ne peut être envoyée.");
										printZoneEnCours(zEnCours);
										j--;
										break OVER;
									}
								}
								isok=true;
							}else if (i1==6) {
								isok=true;
								//ShowAvailableCombattantIds(reserviste);
							}else {
								System.out.println("Il y a un problème avec le numéro que vous avez entré, veuillez réessayer.");
								isok=false;
							}
						}while(isok==false);
					}
				
				}
			} catch (ConcurrentModificationException e) {
				// TODO Auto-generated catch block
	
			}
		}else {
			System.out.println("Vos soldats sont déjà sur le champ de bataille, veuillez continuer à vous battre.");
		}
	}
	
	
	
	//Afficher les zones qui se battent encore
	public void printZoneEnCours(HashMap<Integer, Zone> zEnCours) {
		System.out.println("\t-----------Les zones disponibles sont—-------------");
		Set<Integer> arrkey = zEnCours.keySet();
		for(Integer i:arrkey) {
			System.out.println("\t"+i+":"+zEnCours.get(i).getNomZ());
		}
		System.out.println("\t6:rester en réserve");
		System.out.println("\t------------------------—-------------");		
	}

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	


	public int getCredit() {
		return credit;
	}



	public void setCredit(int credit) {
		this.credit = credit;
	}


	
	public String getNomJ() {
		return nomJ;
	}

	
	public void setNomJ(String nomJ) {
		this.nomJ = nomJ;
	}

	
	public HashMap<Integer,Personnage> getEtudiant() {
		return etudiant;
	}

	
	public void setEtudient(HashMap<Integer,Personnage> etudiant) {
		this.etudiant = etudiant;
	}
	
	public HashMap<Integer,Personnage> getReserviste() {
		return reserviste;
	}

	public void setReserviste(HashMap<Integer,Personnage> reserviste) {
		this.reserviste = reserviste;
	}

	/**
	 * @return the reservisteKey
	 */
	public ArrayList<Integer> getReservisteKey() {
		return reservisteKey;
	}

	/**
	 * @param reservisteKey the reservisteKey to set
	 */
	public void setReservisteKey(ArrayList<Integer> reservisteKey) {
		this.reservisteKey = reservisteKey;
	}

	
}
