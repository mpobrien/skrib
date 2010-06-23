package skrib.models;
import com.google.code.morphia.annotations.*;
import com.mongodb.*;

public class Board{

	@Transient
    public static final int GRID_SIZE = 15;

	@Transient
	public Tile grid[][] = new Tile[GRID_SIZE][GRID_SIZE];
	
	@Id
	private String id;

	private String gridData;

	public Board(){ }

	public Tile getTileAt(int row, int col){//{{{
		return grid[row][col];
	}//}}}

	public void setTileAt(Tile t, int row, int col){//{{{
		grid[row][col] = t;
	}//}}}

	public void makeMove(Move m){//{{{
		if(m.isValid()){
			for( TilePlacement tp : m.getTilePositions() ){
				this.setTileAt(tp.getTile(), tp.getRow(), tp.getCol());
			}
		}
	}//}}}

	public String toHtml(){//{{{
		StringBuilder result = new StringBuilder();
		result.append("<table class=\"board\" cellspacing=\"0\" cellpadding=\"0\" id=\"mainboard\">");
		for( int i=0;i<=GRID_SIZE-1;i++){
			result.append("<tr>");
			for( int j=0; j<=GRID_SIZE-1; j++){
				Multiplier.MultiplierType mult = Multiplier.getMultiplierAt(j,i);
				String id = "c" + Integer.toString(i) + "-" + Integer.toString(j);
				Tile t = getTileAt(i,j);
				String htmlClass = mult.toString();
				if( t != null ) htmlClass += " occupied";
				if( mult == Multiplier.MultiplierType.NONE ){
					result.append("<td id=\""+ id + "\">");
				}else{
					if( i == 7 && j == 7){
						htmlClass += " gridCenter";
					}
					result.append("<td id=\""+ id + "\" class=\"" + htmlClass + "\">");
				}
				if( t == null ){
					result.append("&nbsp;");
				}else{
					result.append(t.toHtml());
				}
				result.append("</td>");
			}
			result.append("</tr>");
		}
		result.append("</table>");
		return result.toString();
	}//}}}

	public String encode(){//{{{
		StringBuilder result = new StringBuilder( GRID_SIZE * GRID_SIZE );
		int index = 0;
		for( int i=0; i<GRID_SIZE; i++){
			for( int j=0; j<GRID_SIZE; j++){
				Character ch = ' ';
				if( getTileAt(i,j) != null ){
					ch = getTileAt(i,j).encode();
				}
				result.append(ch);
				index++;
			}
		}
		return result.toString();
	}//}}}

	public static Board decode(String encoded){//{{{

		if( encoded == null || encoded.length() != GRID_SIZE * GRID_SIZE ){
			return null;
		}

		Board b = new Board();
		b.reset(encoded);
		return b;
	}//}}}

    public void reset(String encoded){//{{{
		int index = 0;
		for( int i=0; i<GRID_SIZE; i++){
			for( int j=0; j<GRID_SIZE; j++){
				//TODO watch out for bogus data here?
				Tile t = Tiles.decode( encoded.charAt(index) );
				this.setTileAt(t, i, j);
				index++;
			}
		}
	}//}}}

	@PrePersist
	public void prePersist(DBObject db){//{{{
		this.gridData = encode();
		db.put("gridData", this.gridData);
	}//}}}

	@PostLoad
	public void postLoad(){//{{{
		reset( this.gridData );
	}//}}}

	
	public String getId(){    return id;  }
	public void setId(String id){    this.id = id;  }

	public String getGridData(){    return gridData;  }
	public void setGridData(String gridData){    this.gridData = gridData;  }

}
