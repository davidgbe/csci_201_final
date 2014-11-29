import javax.swing.ImageIcon;


public class Pikachu extends Pokemon{
	public Pikachu(){
		super("pikachu", 400, new ImageIcon("images/pikachu.png"), 77.3);
		allAttacks[0] = "thunder punch";
		allAttacks[1] = "thunderbolt";
		allAttacks[2] = "volt tackle";
		allAttacks[3] = "bolt strike";
	}
}
