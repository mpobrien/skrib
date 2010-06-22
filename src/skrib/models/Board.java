package skrib.models;

public class Board{

    public static final int GRID_SIZE = 15;

	public Tile grid[][] = new Tile[GRID_SIZE][GRID_SIZE];

	public Board(){ }

	public Tile getTileAt(int row, int col){//{{{
		return grid[row][col];
	}//}}}

	private void setTileAt(Tile t, int row, int col){//{{{
		grid[row][col] = t;
	}//}}}

	public void makeMove(Move m){//{{{
		if(m.isValid()){
			for( TilePlacement tp : m.getTilePositions() ){
				this.setTileAt(tp.getTile(), tp.getRow(), tp.getCol());
			}
		}
	}//}}}

	public String toHtml(){
		StringBuilder result = new StringBuilder("<table class=\"board\" cellspacing=\"0\" cellpadding=\"0\" id=\"mainboard\">");
		for( int i=0;i<=GRID_SIZE-1;i++){
			result.append("<tr>");
			for( int j=0; j<=GRID_SIZE-1; j++){
				Multiplier.MultiplierType mult = Multiplier.getMultiplierAt(j,i);
				String id = "c" + Integer.toString(i) + "-" + Integer.toString(j);
				if( mult == Multiplier.MultiplierType.NONE ){
					result.append("<td id=\""+ id + "\">&nbsp;");
				}else{
					String htmlClass = mult.toString();
					if( i == 7 && j == 7){
						htmlClass += " gridCenter";
					}
					result.append("<td id=\""+ id + "\" class=\"" + htmlClass + "\">&nbsp;");
				}
				result.append("</td>");
			}
			result.append("</tr>");
		}
		result.append("</table>");
		return result.toString();
	}

}
