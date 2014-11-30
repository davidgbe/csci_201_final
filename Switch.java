
public class Switch extends Message {
	private String newPokemon;
	
	public Switch(String pokemon) {
		this.newPokemon = pokemon;
	}
	
	public String getNewPokemon() {
		return this.newPokemon;
	}
}
