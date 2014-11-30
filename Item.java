public class Item extends Message {
	private String type;
	private String pokemon;
	
	public Item(String type) {
		this.type = type;
	}
	
	public Item(String type, String pokemon) {
		this.type = type;
		this.pokemon = pokemon;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getPokemon() {
		return this.pokemon;
	}
	
}