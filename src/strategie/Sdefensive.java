package strategie;

import java.util.ArrayList;
import java.util.Random;

import personnage.Personnage;

public class Sdefensive extends Strategie{
	private boolean ok;
	

	public Sdefensive() {}
	 
	 //p est le guérisseur,arrz为己方的arr, arrO是对方arr
	public void jouer(Personnage p,  ArrayList<Personnage> arrZ,ArrayList<Personnage> arrO) {
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
		
	
	 
	

}
