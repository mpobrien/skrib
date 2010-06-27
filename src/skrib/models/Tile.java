package skrib.models;

public interface Tile{

	public Character getCharacter();
	public int getPoints();
	public int getFrequency();
	public boolean blank();
	public Character encode();
	public String getHtml();

}
