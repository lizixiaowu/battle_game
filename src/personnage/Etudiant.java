package personnage;

public class Etudiant extends Personnage{
	

	public Etudiant() {}
	public Etudiant(int j) {
		super(j);
	}
	
	
	public Etudiant(int id,int appartientAJoueur,int credit,int dexterite,int force,int resistance,int constitution,int initiative) {
		super(id,appartientAJoueur,credit, dexterite, force, resistance, constitution, initiative);
		
	}
	
}
