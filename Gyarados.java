import javax.swing.ImageIcon;


public class Gyarados extends Pokemon{
	public Gyarados(){
		super("gyarados", 600, new ImageIcon("images/gyarados.png"), 75.0);
		allAttacks[0] = "waterfall";
		allAttacks[1] = "hydro pump";
		allAttacks[2] = "hydro cannon";
		allAttacks[3] = "thunderbolt";
	}

}
