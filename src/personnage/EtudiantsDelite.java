package personnage;

public class EtudiantsDelite extends Personnage{
	
	public EtudiantsDelite() {
		super();
		//super.setConstitution(super.getConstitution() + 5 );
		super.setCredit(super.getCredit() + 5);
		super.setForce(super.getForce() + 1 );
		super.setDexterite(super.getDexterite() + 1 );
		super.setResistance(super.getResistance() + 1 );
		super.setInitiative(super.getInitiative() + 1 );
	}
	
	public EtudiantsDelite(int j) {
		super(j);
		super.setCredit(super.getCredit()+ 5 );
		super.setForce(super.getForce() + 1 );
		super.setDexterite(super.getDexterite() + 1 );
		super.setResistance(super.getResistance() + 1 );
		super.setConstitution(super.getConstitution() + 5 );
		super.setInitiative(super.getInitiative() + 1 );
	}
	
}
