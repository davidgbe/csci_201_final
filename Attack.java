public class Attack extends Message {
	private int pokID;
	private String name;
	public Attack(int pokID, String name){
		this.pokID = pokID;
		this.name = name;
	}
	public int getPokemonID(){
		return this.pokID;
	}
	public String getName(){
		return this.name;
	}
}