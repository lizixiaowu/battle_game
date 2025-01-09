package Brutal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import personnage.Etudiant;
import personnage.Personnage;
import zone.Zone;

public class Start{
	
	public static final Object lock = new Object();
	
	
	public static void main(String[] args) {	
		Partie partie = Partie.getInstance();
		
		Joueur j1 = partie.getJ1();
		j1.initJoueur(1);
		j1.addReservist();

		boolean isSelect = true;
		do {		
			System.out.println("Voici vos élus réservistes");
			j1.afficherCombattantAttribut(j1.getReserviste());
			System.out.println("Voulez-vous resélectionner?");
			System.out.println("Entrée 1 : oui\r\n"
					+ "Entrée 2 : non");
			Scanner sc = new Scanner(System.in);
			int x = sc.nextInt();
			if (x==1) {
				//Avant de réélire la réserve, la personne sélectionnée la dernière fois doit être réinsérée dans la liste facultative etudiant
				for(Integer m :j1.getReservisteKey()) {
					j1.getEtudiant().put(m, j1.getReserviste().get(m));
				}
				j1.getReserviste().clear();
				j1.getReservisteKey().clear();
				j1.addReservist();
				isSelect = false;
			}else if (x==2) {	
				isSelect = true;
			}else{
				System.out.println("Saisie incorrecte, veuillez réessayer:");
				isSelect = false;
			}
		}while(isSelect == false);
		
		Set<Integer> keyZone = partie.getZoneEnCours().keySet();
		
		System.out.println("----------Joueur 1-------------");
		System.out.println("Vous devriez décider de répartir ses 15 combattants sur les 5 zones de combat");
		for(int m :keyZone) {
			System.out.println("------------"+partie.getZoneEnCours().get(m).getNomZ()+"-------------");
			int NbCombattant1 =j1.ShowAvailableCombattantIds(j1.getEtudiant());
			System.out.println("Veuillez entrer votre nombre de soldats：");
			boolean isValide = true;
			Scanner sc = new Scanner(System.in);
			do {
						
				int numberOfCombattant = sc.nextInt();
				//NbCombattant1-(3-m)Assurez-vous que le reste de la zone a également au moins un pion
				if (numberOfCombattant>0 && numberOfCombattant<=NbCombattant1-(5-m)) {
					//partie.getZoneEnCours().get(m)est la zone où les soldats sont divisés.
					partie.getZoneEnCours().get(m).ininUneZone(j1,partie.getZoneEnCours().get(m).getArrJ1orderByCredit(),numberOfCombattant);
					isValide = true;
				}else {
					System.out.println("Étant donné qu'au moins un pion doit être placé dans chaque zone et ne peut pas dépasser le nombre total de pions restants, la valeur que vous avez saisie ne répond pas aux exigences, veuillez la saisir à nouveau.");
					isValide = false;
				}
			} while (isValide == false);

		}
		
		//------------------------------joueur 2-----------------------------
		
		Joueur j2 = partie.getJ2();
		j2.initJoueur(2);
		
		
		j2.addReservist();

		boolean isSelect2 = true;
		do {		
			System.out.println("Voici vos élus réservistes");
			j2.afficherCombattantAttribut(j2.getReserviste());
			System.out.println("Voulez-vous resélectionner?");
			System.out.println("Entrée 1 : oui\r\n"
					+ "Entrée 2 : non");
			Scanner sc = new Scanner(System.in);
			int x = sc.nextInt();
			if (x==1) {
				
				for(Integer m :j1.getReservisteKey()) {
					j2.getEtudiant().put(m, j2.getReserviste().get(m));
				}
				j2.getReserviste().clear();
				j2.getReservisteKey().clear();
				j2.addReservist();
				isSelect = false;
			}else if (x==2) {	
				isSelect2 = true;
			}else{
				System.out.println("Saisie incorrecte, veuillez réessayer:");
				isSelect2 = false;
			}
		}while(isSelect2 == false);
		
		System.out.println("----------Joueur 2-------------");
		System.out.println("Vous devriez décider de répartir ses 15 combattants sur les 5 zones de combat");
		
		for(int m :keyZone) {
			System.out.println("------------"+partie.getZoneEnCours().get(m).getNomZ()+"-------------");
			int NbCombattant2 =j2.ShowAvailableCombattantIds(j2.getEtudiant());		
			System.out.println("Veuillez entrer votre nombre de soldats：");
			boolean isValide = true;
			Scanner sc = new Scanner(System.in);
			do {
						
				int numberOfCombattant = sc.nextInt();
				if (numberOfCombattant>0 && numberOfCombattant<=NbCombattant2-(5-m)) {
					partie.getZoneEnCours().get(m).ininUneZone(j2,partie.getZoneEnCours().get(m).getArrJ2orderByCredit(),numberOfCombattant);
					isValide = true;
				}else {
					System.out.println("Étant donné qu'au moins un pion doit être placé dans chaque zone et ne peut pas dépasser le nombre total de pions restants, la valeur que vous avez saisie ne répond pas aux exigences, veuillez la saisir à nouveau.");
					isValide = false;
				}
			} while (isValide == false);
		}
		
		
		System.out.println("la bataille commence!");
		//System.out.println("这个区域按照主动性排序");
		partie.getBibliotheque().addDansArrTotalOrdreByIntiative();
		partie.getBureauDesEtudiants().addDansArrTotalOrdreByIntiative();
		partie.getHallesIndustrielle().addDansArrTotalOrdreByIntiative();
		partie.getHalleSportive().addDansArrTotalOrdreByIntiative();
		partie.getQuartierAdministratif().addDansArrTotalOrdreByIntiative();
	
		
		partie.getBibliotheque().start("La bibliothèque");
		partie.getBureauDesEtudiants().start("Le bureau des Etudiants");
		partie.getHallesIndustrielle().start("Les Halles industrielles");
		partie.getHalleSportive().start("La Halle Sportive");
		partie.getQuartierAdministratif().start("Le Quartier Administratif");
	
		

	}
	

	 
		
	
	
	
	
	public static void selectZone() {
		System.out.println("\t 1.La bibliothèque");
		System.out.println("\t 2.Le Bureau des Etudiants");
		System.out.println("\t 3.Le Quartier Administratif");
		System.out.println("\t 4.Les Halles industrielles");
		System.out.println("\t 5.La Halle Sportive");
		System.out.println("-----------------------------------");
		
	}

}
