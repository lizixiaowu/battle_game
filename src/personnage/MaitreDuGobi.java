package personnage;

import strategie.Strategie;

public class MaitreDuGobi extends Personnage{

	
	

	public MaitreDuGobi() {
		super();
		//super.setConstitution(super.getConstitution() + 10 );
		super.setCredit(super.getCredit() + 10);
		super.setForce(super.getForce() + 2 );
		super.setDexterite(super.getDexterite() + 2 );
		super.setResistance(super.getResistance() + 2 );
		super.setInitiative(super.getInitiative() + 2 );
	}

	
	public MaitreDuGobi(int j) {
		super(j);
		super.setCredit(super.getCredit()+10 );
		super.setForce(super.getForce() + 2 );
		super.setDexterite(super.getDexterite() + 2 );
		super.setResistance(super.getResistance() + 2 );
		super.setConstitution(super.getConstitution() + 10 );
		super.setInitiative(super.getInitiative() + 2 );

	}


	
	

}
