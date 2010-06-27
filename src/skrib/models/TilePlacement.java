package skrib.models;

public class TilePlacement{

	private final Tile tile;
	private final GridPosition gridPosition;
	private final boolean onBoard;

	public TilePlacement(Tile tile, GridPosition gridPosition){//{{{
		this.tile = tile;
		this.gridPosition = gridPosition;
		this.onBoard = false;
	}//}}}

	public TilePlacement(Tile tile, GridPosition gridPosition, boolean onBoard){//{{{
		this.tile = tile;
		this.gridPosition = gridPosition;
		this.onBoard = onBoard;
	}//}}}

	public Tile getTile(){    return tile;  }
	public GridPosition getGridPosition(){    return gridPosition;  }
	public boolean onBoard(){ return this.onBoard; }

    public int hashCode(){
		final int prime = 17;
		return (tile.hashCode() * prime) ^ this.gridPosition.hashCode();
	}

	public boolean equals(Object o){//{{{
		if( o == null || !(o instanceof TilePlacement)) return false;
		TilePlacement tp = (TilePlacement)o;
		if( !tp.getGridPosition().equals( this.getGridPosition() ) ) return false;
		if( !this.tile.equals( tp.getTile() ) ) return false;
		if( !this.onBoard() == tp.onBoard() ) return false;
		return true;
	}//}}}

	public String toString(){//{{{
		return String.format("(%d,%d) %s",
				             this.gridPosition.getRow(),
							 this.gridPosition.getCol(),
							 this.tile.toString());
	}//}}}

}
