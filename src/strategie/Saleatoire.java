package strategie;

import java.util.ArrayList;
import java.util.Random;

import personnage.Personnage;

public class Saleatoire extends Strategie{
	private boolean ok;
	
	public Saleatoire() {}

	public void jouer(Personnage p, ArrayList<Personnage> arrZ,ArrayList<Personnage> arrO) {
		boolean isDefensive = this.isDefensive();
		if (isDefensive == true) {
			this.sDefensive(p, arrZ,arrO);
		}else {
			this.sOffensive(p, arrZ,arrO);
		}	
	}
	
	public void sDefensive(Personnage p,  ArrayList<Personnage> arrZ,ArrayList<Personnage> arrO) {
		Random r = new Random();
		double b2 = r.nextDouble()*0.6;
		ok = this.soignerReussi(p);
		if(ok == true) {

			//arrZ.get(1)返回区域内血最少的人，此时调用的区域数组已经过排序
			int nSoigner = (int)(b2 * (arrZ.get(0).getConstitution() + 10));
			int temp = 30 + arrZ.get(0).getConstitution();
			nSoigner = arrZ.get(0).getCredit()+ nSoigner< temp ? nSoigner : 0;//temp
			arrZ.get(0).setCredit(arrZ.get(0).getCredit() + nSoigner);
			System.out.println(Thread.currentThread().getName()+":Le soldat id="+p.getId()+" du joueur"+p.appartientAJoueur+" est soigné avec succès,la quantité de soins est de :"+nSoigner+"Le soldat id="+arrZ.get(0).getId()+" du joueur"+arrZ.get(0).appartientAJoueur+" a"+arrZ.get(0).getCredit()+"points restants après avoir reçu le traitement.");
			System.out.println("");
		}else {
			System.out.println(Thread.currentThread().getName()+":Le soldat id="+p.getId()+" du joueur"+p.appartientAJoueur+" n'a pas réussi à se soigner.");
			System.out.println("");
		}
	}
	
	public boolean soignerReussi(Personnage p){
		Random r = new Random();
		int i = r.nextInt(101);
		if(i>=0 && i <20+p.getDexterite()) {
			ok = true;
		}else {
			ok = false;
		}
		return ok;
	}
	
	 //p est le guérisseur,arrz为己方的arr, arrO是对方arr
	public void sOffensive(Personnage p, ArrayList<Personnage> arrZ,ArrayList<Personnage> arrO) {
		Random r = new Random();
		//为了能取到1且不取到0
		double b2 = -r.nextDouble() + 1;
		ok = this.attaquerReussi(p);
		if(ok == true) {
			//arrZ.get(1)返回区域内血最少的人，此时调用的区域数组已经过排序
			int coeffDegat = Math.max(0, Math.min(100, p.getForce()*10 - arrO.get(0).getResistance()*5))/100;
			int nAttaquer = (int)(b2 *(1 + coeffDegat)*10);
			int credit =arrO.get(0).getCredit() - nAttaquer>=0?arrO.get(0).getCredit() - nAttaquer:0;
			arrO.get(0).setCredit(credit);
			System.out.println(Thread.currentThread().getName()+"Le soldat id="+p.getId()+" du joueur"+p.appartientAJoueur+ " attaque avec succès, les dégâts d'attaque sont de "+nAttaquer+",et le soldat"+arrO.get(0).getId()+" du joueur"+arrO.get(0).appartientAJoueur+" a "+credit+" crédits restants après avoir été attaqué.");
			System.out.println("");
			//判断被攻击者是否死亡
			if (credit == 0) {
				arrO.remove(0);
			}
			
			
		}else {
			System.out.println(Thread.currentThread().getName()+":Le soldat id="+p.getId()+" du joueur"+p.appartientAJoueur+" n'a pas réussi à se attaquer.");
			System.out.println("");
		}	
	}
	
	public boolean attaquerReussi(Personnage p){
		Random r = new Random();
		int i = r.nextInt(101);
		if(i>=0 && i <40+3*p.getDexterite()) {
			ok = true;
		}else {
			ok = false;
		}
		return ok;
	}
	
	public boolean isDefensive() {
		boolean isDefensive;
		//1:defensive, 2:offensive
		Random r = new Random();
		int i = r.nextInt(2) + 1;
		if (i==1) {
			isDefensive = true;
		}else {
			isDefensive = false;
		}
		return isDefensive;
	}
}
