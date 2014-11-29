import javax.swing.ImageIcon;

public class Pokemon{
	private String name;
	private int healthPoints;
	private int totalHealthPoints; // NEVER CHANGES (used to find percentage)
	protected String[] allAttacks = new String[4];
	private ImageIcon pokemonImage;
	private double strength;
	
	public Pokemon(String name, int healthPoints, ImageIcon image, double strength) {
		this.name = name;
		this.healthPoints = healthPoints;
		this.totalHealthPoints = healthPoints;
		this.pokemonImage = image;
		this.strength = strength;
	}
	public String[] getAttacks(){
		return allAttacks;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getTotalHealthPoints() {
		return totalHealthPoints;
	}
	public void setTotalHealthPoints(int totalHealthPoints) {
		this.totalHealthPoints = totalHealthPoints;
	}

	public static Pokemon getPokemonObjectFromName(String name){
		// For now function is only used to get total health of a pokemon based on name,
		// so that it doesn't need to be passed between client/server since it never changes
		
		if(name.equals("venusaur")){return new Venusaur();}
		else if(name.equals("blastoise")){return new Blastoise();}
		else if(name.equals("charizard")){return new Charizard();}
		else if(name.equals("dragonite")){return new Dragonite();}
		else if(name.equals("gyarados")){return new Gyarados();}
		else if(name.equals("hitmonchan")){return new Hitmonchan();}
		else if(name.equals("lapras")){return new Lapras();}
		else if(name.equals("mewtwo")){return new Mewtwo();}
		else if(name.equals("onix")){return new Onix();}
		else if(name.equals("pidgeot")){return new Pidgeot();}
		else if(name.equals("pikachu")){return new Pikachu();}
		else if(name.equals("rapidash")){return new Rapidash();}
		else if(name.equals("rhydon")){return new Rhydon();}
		else if(name.equals("scyther")){return new Scyther();}
		else if(name.equals("snorlax")){return new Snorlax();}
		else{return new Snorlax();}



	}

	
}