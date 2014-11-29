import javax.swing.ImageIcon;



public class Dragonite extends Pokemon{
	public Dragonite(){
		super("dragonite", 800, new ImageIcon("images/dragonite.png"), 70.0);
		allAttacks[0] = "sheer cold";
		allAttacks[1] = "hydro pump";
		allAttacks[2] = "headbutt";
		allAttacks[3] = "earthquake";
	}
}
