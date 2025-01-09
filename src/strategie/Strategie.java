package strategie;

import java.util.ArrayList;

import personnage.Personnage;

public abstract class Strategie {

	public Strategie() {}
	
	public abstract void jouer(Personnage p, ArrayList<Personnage> arrZ,ArrayList<Personnage> arrO);
	
}
