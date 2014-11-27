import java.util.ArrayList;


public class PokemonUpdate extends Message {
	String[] pokemon;
	
	public PokemonUpdate(ArrayList<String> pokemon) {
		this.pokemon = new String[6];
		for(int i = 0; i < pokemon.size(); i++) {
			this.pokemon[i] = pokemon.get(i);
		}
	}
	
	public String[] getPokemon() {
		return this.pokemon;
	}
	
	
}
