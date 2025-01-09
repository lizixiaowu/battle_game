package strategie;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.plaf.synth.SynthScrollPaneUI;

import personnage.Personnage;

public class Soffensive extends Strategie {
	private boolean ok;
	
	public Soffensive() {}
	
	//arrz为己方的arr, arro为对方的arr
	public void  jouer(Personnage p, ArrayList<Personnage> arrZ,ArrayList<Personnage> arrO) {
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
			System.out.println(Thread.currentThread().getName()+":Le soldat id="+p.getId()+" du joueur"+p.appartientAJoueur+ " attaque avec succès, les dégâts d'attaque sont de"+nAttaquer+",et le soldat"+arrO.get(0).getId()+" du joueur"+arrO.get(0).appartientAJoueur+" a "+credit+" crédits restants après avoir été attaqué.");
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

}
