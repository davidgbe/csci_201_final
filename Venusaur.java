import javax.swing.ImageIcon;

public class Venusaur extends Pokemon{
	public Venusaur(){
		super("venusaur", 1000, new ImageIcon("images/venosaur.png"), 50.0);
		allAttacks[0] = "waterfall";
		allAttacks[1] = "thunderbolt";
		allAttacks[2] = "headbutt";
		allAttacks[3] = "earthquake";
	}
}
