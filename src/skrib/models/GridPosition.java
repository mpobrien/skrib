package skrib.models;
public class GridPosition{

	private final int row;
	private final int col;
	
	public GridPosition(int row, int col){//{{{
		this.row = row;
		this.col = col;
	}//}}}
	
	public int getRow(){    return row;  }
	public int getCol(){    return col;  }

	public int hashCode(){
		final int prime = 31;
		return (this.row * prime) ^ this.col;
	}

	public boolean onLeftSide(){ return this.col == 0; }
	public boolean onRightSide(){ return this.col == Board.GRID_SIZE-1; }
	public boolean onTop(){ return this.row == 0; }
	public boolean onBottom(){ return this.row == Board.GRID_SIZE-1; }

	public GridPosition moveLeft(){ 
		return onLeftSide() ? null : new GridPosition(this.row, this.col-1);
	}

	public GridPosition moveRight(){
		return onRightSide() ? null : new GridPosition(this.row, this.col+1);
	}

	public GridPosition moveDown(){ 
		return onBottom() ? null : new GridPosition(this.row+1, this.col);
	}

	public GridPosition moveUp(){
		return onTop() ? null : new GridPosition(this.row-1, this.col);
	}

	public GridPosition move(int dx, int dy){
		int new_x = this.col + dx;
		int new_y = this.row + dy;
		if( new_y < 0 || new_y >= Board.GRID_SIZE ) return null;
		if( new_x < 0 || new_x >= Board.GRID_SIZE ) return null;
		return new GridPosition(new_y, new_x);
	}


	public boolean equals(Object o){//{{{
		if( o == null || !(o instanceof GridPosition) ) return false;
		return this.getRow() == ((GridPosition)o).getRow() &&
				this.getCol() == ((GridPosition)o).getCol();
	}//}}}

}
