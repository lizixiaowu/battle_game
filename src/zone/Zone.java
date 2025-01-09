package zone;

import java.security.Identity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Brutal.Joueur;
import Brutal.Partie;
import Brutal.Start;
import personnage.*;

public class Zone implements Runnable {
	private String nomZ;
	public int Zoneid;
	private int ZcreditA;
	private int ZcreditB;
	private ArrayList<Personnage> arrJ1orderByCredit;
	private ArrayList<Personnage> arrJ2orderByCredit; 
	private ArrayList<Personnage> arrTotalOrdreByIntiative;
	private boolean perdu;
	private Thread t;
	public static boolean isStop = false;
	private int personneGagne;

	
	//注意构造函数中就要为三个list new好对象
	public Zone() {
		arrJ1orderByCredit = new ArrayList<Personnage>();
		arrJ2orderByCredit = new ArrayList<Personnage>();
		arrTotalOrdreByIntiative = new ArrayList<Personnage>();
	}
	
	public Zone(String nom,int id) {
		Zoneid = id;
		nomZ = nom;
		arrJ1orderByCredit = new ArrayList<Personnage>();
		arrJ2orderByCredit = new ArrayList<Personnage>();
		arrTotalOrdreByIntiative = new ArrayList<Personnage>();
	}

	
	public void ininUneZone(Joueur j,ArrayList<Personnage> arr,int numberOfCombattant) {
		addCombattantDansUneZone(j, arr,numberOfCombattant);
		trierParCredit(arr);
		System.out.println("Ce sont vos combattants dans ce zone:");
		for(Personnage p : arr) {
			p.printJoueurCombattant();
			System.out.println("");
		}	
	}
	
	
	public void addCombattantDansUneZone(Joueur j,ArrayList<Personnage> arr,int numberOfCombattant) {
		boolean ok ;
		System.out.println("Veuillez choisir votre soldat:");
		System.out.println("(entrez l'id du guerrier, barre d'espace pour séparer)");
		Scanner sc = new Scanner(System.in);	
		//int numberOfCombattant = sc.nextInt();
		int count = 0;
		while (count < numberOfCombattant) {
				
					int id = sc.nextInt();
					//判断这个士兵在玩家的未分配士兵列表中是否存在
					if (j.getEtudiant().get(id) == null) {
						System.out.println("Le combattant avec id = " +id +" n'est pas sur la liste facultative , veuillez le saisir à nouveau.");
					}//判断有没有重复添加士兵
					else if (isAddDansZone(id, arr)==true) {
						System.out.println("Le combattant avec id = " +id +"a été dans le list de reserviste, veuillez le saisir à nouveau");
					}else {
						arr.add(j.getEtudiant().get(id));
						j.getEtudiant().remove(id);
						count ++;	
					}			
		}
	}
	
	//判断是否已经添加过该士兵
	public boolean isAddDansZone(int id,ArrayList<Personnage> arr) {
		boolean isadd = false;
		if (arr.isEmpty()==true) {
			isadd = false;
		}else {
			for(Personnage p : arr) {
				if (id == p.getId()) {
					isadd = true;
				}
			}
		}

		return isadd;
	}
	
	//两方分开的arraylist按照credit排序
	public void trierParCredit(ArrayList<Personnage> arr) {
		Collections.sort(arr, new Comparator<Personnage>() {
			public int compare(Personnage p1,Personnage p2) {
				int num = p1.getCredit()- p2.getCredit();
				num = num ==0 ? p1.getId()-p2.getId():num;
				return num;
			}	
		});
	}
	
	//合并俩方进入arrTotalOrdreByIntiative且排好序
	public void addDansArrTotalOrdreByIntiative() {
		for(Personnage p1: this.arrJ1orderByCredit) {
			arrTotalOrdreByIntiative.add(p1);
			ZcreditA += p1.getCredit();
		}
		for(Personnage p2: this.arrJ2orderByCredit) {
			arrTotalOrdreByIntiative.add(p2);
			ZcreditB += p2.getCredit();
		}
		trierParInitiative(arrTotalOrdreByIntiative);
		System.out.println("Le nombre total de crédits des soldats de joueur 1 en "+nomZ+" est "+ZcreditA);
		System.out.println("Le nombre total de crédits des soldats de joueur 2 en "+nomZ+" est "+ZcreditB);
	}
	
	//合并的arraylist按照主动性排序
	public void trierParInitiative(ArrayList<Personnage> arr) {
		Collections.sort(arr, new Comparator<Personnage>() {
			public int compare(Personnage p1,Personnage p2) {
				int num = p2.getInitiative()- p1.getInitiative();
				num = num ==0 ? p2.getId()-p1.getId():num;
				num = num ==0 ? p1.getId():num;
				return num;
			}	
		});
	}
	
	@Override
	public void run() {
		boolean isOver = false;
		while (perdu==false) {
			this.melee();	
		}
		if (Partie.getJoueur1Gagne()>=3) {
			isOver = true;
			System.out.println("joueur 1 est gagné!");
		}else if (Partie.getJoueur2Gagne()>=3) {
			isOver = true;
			System.out.println("joueur 2 est gagné!");
		}
		
		//再次分兵函数，这里锁住线程
		if (isOver==false) {
			synchronized (Start.lock) {
				Partie partie = Partie.getInstance();
				System.out.println(Thread.currentThread().getName()+":La guerre régionale est terminée, il est maintenant temps de faire une trêve.");
				System.out.println("-----------------------pour Joueur 1 ----------------------------");
				partie.getJ1().mouvementDeTroupesDesReserves(partie.getZoneEnCours());
				System.out.println("-----------------------pour Joueur 2 ----------------------------");
				partie.getJ2().mouvementDeTroupesDesReserves(partie.getZoneEnCours());;
				
				gagneMouvementDeTroupesDesZone();
				
				partie.getZonePerdu().add(this);
				partie.getZoneTemp().remove(this.Zoneid);
				trierParInitiative(arrTotalOrdreByIntiative);
				if (partie.getZoneTemp().isEmpty()) {
					isStop = false;	
				}
			}	
		}
		
	}

	
	//胜利者从zone里再次调兵
	public void gagneMouvementDeTroupesDesZone() {
		boolean isok = true;
		//System.out.println("Vos soldats restants dans cette zone sont :");		
		//afficherCombattantAttribut(arrTotalOrdreByIntiative);
		System.out.println("pour Joueur"+ personneGagne +" ,Vous pouvez envoyer les soldats à "+Thread.currentThread().getName()+"dans les zones suivantes, mais n'oubliez pas de laisser au moins une personne garder cette zone.");
		Partie partie = Partie.getInstance();
		printZoneEnCours(partie.getZoneEnCours());
		Scanner sc = new Scanner(System.in);
		for (int k = 0;k<arrTotalOrdreByIntiative.size();k++) {
			if (arrTotalOrdreByIntiative.size()>1) {
				OVER:
				do{
					System.out.println("Veuillez saisir id de région que vous souhaitez attribuer :");
					int i1 = sc.nextInt();
					if (i1>=1&&i1<=5) {
						//重新分配strategie
						arrTotalOrdreByIntiative.get(k).selectStrategie();
						if (personneGagne==1) {
							try {
								
								//在i对应的区域添加进胜利区域索引为k的士兵：arrTotalOrdreByIntiative.get(k)
								partie.getZoneEnCours().get(i1).getArrJ1orderByCredit().add(arrTotalOrdreByIntiative.get(k));
								partie.getZoneEnCours().get(i1).trierParCredit(partie.getZoneEnCours().get(i1).getArrJ1orderByCredit());
								partie.getZoneEnCours().get(i1).getArrTotalOrdreByIntiative().add(arrTotalOrdreByIntiative.get(k));
								this.arrTotalOrdreByIntiative.remove(k);
								
							} catch (NullPointerException e) {
								// TODO Auto-generated catch block
								System.out.println("La bataille dans cette zone est terminée et plus aucune troupe ne peut être envoyée.");
								afficherZoneEnCours(partie.getZoneEnCours());
								k--;
								break OVER;
							}
						}else {
							try {
								
								partie.getZoneEnCours().get(i1).getArrJ2orderByCredit().add(arrTotalOrdreByIntiative.get(k));
								partie.getZoneEnCours().get(i1).trierParCredit(partie.getZoneEnCours().get(i1).getArrJ2orderByCredit());
								partie.getZoneEnCours().get(i1).getArrTotalOrdreByIntiative().add(arrTotalOrdreByIntiative.get(k));
								this.arrTotalOrdreByIntiative.remove(k);
								afficherCombattantAttribut(arrTotalOrdreByIntiative);
							} catch (NullPointerException e) {
								System.out.println("La bataille dans cette zone est terminée et plus aucune troupe ne peut être envoyée.");
								afficherZoneEnCours(partie.getZoneEnCours());
								k--;
								break OVER;
							}
						}
						isok=true;
					}else if (i1==6) {
						isok=true;
					}else {
						System.out.println("Il y a un problème avec le numéro que vous avez saisi, veuillez réessayer.");
						isok=false;
					}
				}while(isok==false);	
			}else {
				System.out.println("Puisque vous devez laisser un soldat dans cette zone, désolé vous ne pouvez plus déplacer de soldats de cette zone.");
				System.out.println(" ");
			}
			
		}
	}
	
	//Affiche les zones où la bataille n'est pas encore terminée
	public void printZoneEnCours(HashMap<Integer, Zone> zEnCours) {
		System.out.println("\t-----------Les zones disponibles sont—-------------");
		Set<Integer> arrkey = zEnCours.keySet();
		for(Integer i:arrkey) {
			System.out.println("\t"+i+":"+zEnCours.get(i).getNomZ());
		}
		System.out.println("\t"+this.Zoneid+ ":reste dans la zone");
		System.out.println("\t------------------------—-------------");		
	}
	
	//afficher les réservistes dans arraylist
		public void afficherCombattantAttribut(ArrayList<Personnage> arr) {
			for(Personnage p : arr) {
				System.out.println("\tjoueur"+p.appartientAJoueur+" combattant id = " + p.getId());
				System.out.println("\t credit = " + p.getCredit());
				System.out.println("\t force = " + p.getForce());
				System.out.println("\t dexterite = " + p.getDexterite());
				System.out.println("\t resistance = " + p.getResistance());
				System.out.println("\t initiative = " + p.getInitiative());
				System.out.println("-----------------------------------");
				
			}	
		}
		
	
	//appeler le multithreading
	public void start(String tname) {
		if (t==null) {
			t= new Thread(this,tname);
			t.start();
		}
	}
	
	//fonction de guerre
	public void melee() {
		Partie partie = Partie.getInstance();
		OUT:
		while (Zone.isStop == false) {
			for(int j =0;j<arrTotalOrdreByIntiative.size();j++) {
				if (arrTotalOrdreByIntiative.get(j).appartientAJoueur == 1) {
					//joueur1的兵
					arrTotalOrdreByIntiative.get(j).getStrategie().jouer(arrTotalOrdreByIntiative.get(j), arrJ1orderByCredit,arrJ2orderByCredit);
					
				}else {
					arrTotalOrdreByIntiative.get(j).getStrategie().jouer(arrTotalOrdreByIntiative.get(j), arrJ2orderByCredit,arrJ1orderByCredit);
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i =0;i<arrTotalOrdreByIntiative.size();i++) {
					if (arrTotalOrdreByIntiative.get(i).getCredit()==0) {
						arrTotalOrdreByIntiative.remove(i);
					}
				}
				//joueur 1 mort
				perdu = isPerdu(arrJ1orderByCredit);
 				if (perdu) {
 					partie.getZoneTemp().put(this.Zoneid,this);

					partie.getZoneEnCours().remove(this.Zoneid);

					isStop = perdu;
					System.out.println(Thread.currentThread().getName()+":joueur 2 gagné.");
					this.personneGagne = 2;
					Partie.setJoueur2Gagne(Partie.getJoueur2Gagne()+1);
					break OUT;
				}
				
				perdu = isPerdu(arrJ2orderByCredit);
				if (perdu) {
					partie.getZoneTemp().put(this.Zoneid,this);
					partie.getZoneEnCours().remove(this.Zoneid);
					//this.afficherZoneEnCours(partie.getZoneEnCours());
					isStop = perdu;
					System.out.println(Thread.currentThread().getName()+"joueur 1 gagné.");
					Partie.setJoueur1Gagne(Partie.getJoueur1Gagne()+1);
					this.personneGagne = 1;
					break OUT;
				}
				this.trierParCredit(arrJ1orderByCredit);
				this.trierParCredit(arrJ2orderByCredit);
			}
		}
	}
	
	
	//遍历hashMap
	public void afficherZoneEnCours(HashMap<Integer, Zone> map) {
		Set<Integer> kset = map.keySet();
		for(Integer i : kset) {
			Zone z = map.get(i);
			System.out.println("\t zone id = " + i);
			System.out.println("\t zone nome = " + z.getNomZ());
	
		}	
	}
	
	//遍历arraylist
	public void afficherZonePerdu(ArrayList<Zone> arr) {
		for(Zone i : arr) {
			System.out.println("\t zone id = " + i.Zoneid);
			System.out.println("\t zone nome = " + i.getNomZ());
			
		}	
	}
	
	
	
	//判断是否阵亡
	private boolean isPerdu(ArrayList<Personnage> arr) {
		return arr.isEmpty();
	}
	
	
	public int afficheCredit() {
		int credit = 0;
		return credit;
	}

	


	/**
	 * @return the nomZ
	 */
	public String getNomZ() {
		return nomZ;
	}


	public void setNomZ(String nomZ) {
		this.nomZ = nomZ;
	}


	public int getZcreditA() {
		return ZcreditA;
	}


	public void setZcreditA(int zcreditA) {
		ZcreditA = zcreditA;
	}

	public int getZcreditB() {
		return ZcreditB;
	}

	
	public void setZcreditB(int zcreditB) {
		ZcreditB = zcreditB;
	}


	public ArrayList<Personnage> getArrJ1orderByCredit() {
		return arrJ1orderByCredit;
	}


	public void setArrJ1orderByCredit(ArrayList<Personnage> arrJ1orderByCredit) {
		this.arrJ1orderByCredit = arrJ1orderByCredit;
	}


	public ArrayList<Personnage> getArrJ2orderByCredit() {
		return arrJ2orderByCredit;
	}


	public void setArrJ2orderByCredit(ArrayList<Personnage> arrJ2orderByCredit) {
		this.arrJ2orderByCredit = arrJ2orderByCredit;
	}


	public ArrayList<Personnage> getArrTotalOrdreByIntiative() {
		return arrTotalOrdreByIntiative;
	}


	public void setArrTotalOrdreByIntiative(ArrayList<Personnage> arrTotalOrdreByIntiative) {
		this.arrTotalOrdreByIntiative = arrTotalOrdreByIntiative;
	}


	public boolean isPerdu() {
		return perdu;
	}

	public void setPerdu(boolean perdu) {
		this.perdu = perdu;
	}

	
	
	
}
