import javax.swing.ImageIcon;

public class Pokemon{
	private String name;
	private int healthPoints;
	private Attack[] allAttacks = new Attack[4];
	private ImageIcon pokemonImage;
	private double strength;
	
	public Pokemon(String name, int healthPoints, ImageIcon image, double strength) {
		this.name = name;
		this.healthPoints = healthPoints;
		this.pokemonImage = image;
		this.strength = strength;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Attack[] getAllAttacks() {
		return allAttacks;
	}
	public void setAllAttacks(Attack[] allAttacks) {
		this.allAttacks = allAttacks;
	}
	public ImageIcon getPokemonImage() {
		return pokemonImage;
	}
	public void setPokemonImage(ImageIcon pokemonImage) {
		this.pokemonImage = pokemonImage;
	}
	public double getStrength() {
		return strength;
	}
	public void setStrength(double strength) {
		this.strength = strength;
	}
	public int getHealthPoints() {
		return healthPoints;
	}
	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	
}