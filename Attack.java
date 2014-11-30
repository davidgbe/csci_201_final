public class Attack extends Message {
	private int pokID;
	private String name;
	public Attack(String name){
		this.name = name;
	}
	public int getPokemonID(){
		return this.pokID;
	}
	public String getName(){
		return this.name;
	}
}