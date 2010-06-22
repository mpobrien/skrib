package skrib.models;

public class TilePlacement{

	private final Tile tile;
	private final int row;
	private final int col;

	public TilePlacement(Tile tile, int row, int col){
		this.tile = tile;
		this.row = row;
		this.col = col;
	}

	public Tile getTile(){    return tile;  }
	public int getRow(){    return row;  }
	public int getCol(){    return col;  }

}
