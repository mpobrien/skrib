package skrib.util;
import skrib.models.Tiles.LetterTile;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import skrib.models.*;


public class TileBag{

	private final Multiset<Tile> tiles;

	public TileBag(){//{{{
		this.tiles = HashMultiset.create();
		for( LetterTile lt : Tiles.LetterTile.class.getEnumConstants() ){
			tiles.add(lt, lt.getFrequency());
		}
		BlankTile bt = new BlankTile();
		tiles.add(bt, bt.getFrequency());
	}//}}}

	//TODO not needed?
	//public void subtract(Board b){//{{{
		//for(int i=0;i<Board.GRID_SIZE;i++){
		    //for(int j=0;j<Board.GRID_SIZE;j++){
				//Tile t = b.getTileAt(j,i);
				//if( t != null ){
					//if( t.blank() ){
						//this.tiles.remove(new BlankTile());
					//}else{
						//this.tiles.remove(t);
					//}
				//}
			//}
		//}
	//}//}}}

	public void subtract(Tile t){//{{{
		if( t == null ) return;
		if( t.blank() ){
			this.tiles.remove(new BlankTile());
		}else{
			this.tiles.remove(t);
		}
	}//}}}

    public int numTilesLeft(){
		return this.tiles.size();
	}

	public List<Tile> takeRandomTiles(int numTiles){//{{{
		ArrayList<Tile> result = new ArrayList<Tile>();
		for(int i=0;i<numTiles;i++){
			Tile t = takeRandomTile();
			if( t == null ) break;
			result.add( t );
		}
		return result;
	}//}}}

	public Tile takeRandomTile(){//{{{
		if( this.tiles.size() == 0 ) return null;
		Random random = new Random();
		int tileNum = random.nextInt(this.tiles.size());
		int i=0;
		for(Multiset.Entry<Tile> tileSet : this.tiles.entrySet()){
			i += tileSet.getCount();
			if( i > tileNum ){
				Tile t = tileSet.getElement();
				this.tiles.remove(t);
				return t;
			}
		}
		return null;
	}//}}}

}
