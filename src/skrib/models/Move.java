package skrib.models;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public class Move{

	private final List<TilePlacement> tilePositions;

	public Move(List<TilePlacement> tilePositions){
		this.tilePositions = tilePositions;
	}

	public boolean isValid(){//{{{
		HashSet<Integer> rows = Sets.newHashSetWithExpectedSize(this.tilePositions.size());
		HashSet<Integer> cols = Sets.newHashSetWithExpectedSize(this.tilePositions.size());
		for( TilePlacement tp : this.tilePositions ){
			rows.add( tp.getRow() );
			cols.add( tp.getCol() );
		}
		if( rows.size() > 1 && cols.size() > 1 ) return false;
		return true;
	}//}}}

	public List<TilePlacement> getTilePositions(){
		return this.tilePositions;
	}

}
