package skrib.models;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public class Move{

	private final List<TilePlacement> tilePositions;

	public Move(List<TilePlacement> tilePositions){ this.tilePositions = tilePositions; }

	public boolean isValid(){//{{{
		HashSet<Integer> rows = Sets.newHashSetWithExpectedSize(this.tilePositions.size());
		HashSet<Integer> cols = Sets.newHashSetWithExpectedSize(this.tilePositions.size());
		HashSet<GridPosition> positions = new HashSet<GridPosition>();
		for( TilePlacement tp : this.tilePositions ){
			positions.add( tp.getGridPosition() );
			rows.add( tp.getGridPosition().getRow() );
			cols.add( tp.getGridPosition().getCol() );
		}
		if( positions.size() != tilePositions.size() ); // ensure all tiles are on different squares
		if( rows.size() > 1 && cols.size() > 1 ) return false;
		return true;
	}//}}}

	public List<TilePlacement> getTilePositions(){ return this.tilePositions; }
	public TilePlacement getTileAt(GridPosition gp){//{{{
		//TODO linear scan - too slow?
		for( TilePlacement tp : this.tilePositions ){
			if( gp.equals( tp.getGridPosition() ) ){
				return tp;
			}
		}
		return null;
	}//}}}

}
