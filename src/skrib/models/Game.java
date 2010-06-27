package skrib.models;
import skrib.util.*;
import java.util.*;
import com.google.code.morphia.annotations.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import org.apache.log4j.*;
import com.mongodb.*;

public class Game{

	private static final Logger log = Logger.getLogger( Game.class );

	@Id
	private String id;

	@Embedded
	private Board boardState;

	private Date ctime;
	private Date mtime;

	private int numPlayers;

	private List<String> playerIds;

	@Transient
    private Map<Integer,Integer> playerScores;

	@Transient
	private Map<Integer,Multiset<Tile>> playerTiles;
	private Map<Integer,String> playerTilesMap;

	@Transient 
	private TileBag tileBag;

	private int turnNumber;
	private int nextTurnPlayerNum;

    public Game(){}

    public Game(User creator, int numPlayers){//{{{
		this.numPlayers = numPlayers;
		this.boardState = new Board();
		this.tileBag = new TileBag();
		this.turnNumber = 0;
		this.nextTurnPlayerNum = 0;
        this.playerIds = new ArrayList<String>();
		this.playerIds.add( creator.getId() );
		this.playerScores = new HashMap<Integer,Integer>();
		for( int i=0;i<this.numPlayers;i++){ playerScores.put(i, 0); }
		this.playerTiles = new HashMap<Integer, Multiset<Tile>>();
		for( int i=0; i<numPlayers;i++){
			List<Tile> playerTileList = tileBag.takeRandomTiles(7);
			Multiset<Tile> playerTileListMulti = HashMultiset.create();
			for(Tile t: playerTileList){
				playerTileListMulti.add(t);
			}
			playerTiles.put(i, playerTileListMulti);
		}
		this.ctime = new Date();
		this.mtime = new Date();
	}//}}}

	/* Getters + Setters *///{{{
	
	public void setId(String id){    this.id = id;  }
	public String getId(){    return id;  }

	public List<String> getPlayerIds(){ return this.playerIds; }
	public void setPlayerIds(List<String> playerIds){ this.playerIds = playerIds; }

	public Map<Integer,Multiset<Tile>> getPlayerTiles(){ return this.playerTiles; }
	public void setPlayerTiles(Map<Integer,Multiset<Tile>> playerTiles){ this.playerTiles = playerTiles; }

	public Map<Integer,String> getPlayerTilesMap(){ return this.playerTilesMap; }
	//public void setPlayerTiles(Map<Integer,Multiset<Tile>> playerTiles){ this.playerTiles = playerTiles; }

	public int getNumPlayers(){    return numPlayers;  }
	public void setNumPlayers(int numPlayers){    this.numPlayers = numPlayers;  }
	
	public Board getBoardState(){    return boardState;  }
	public void setBoardState(Board boardState){    this.boardState = boardState;  }
    
    public Map<Integer,Integer> getPlayerScores(){    return playerScores;  }
    public void setPlayerScores(Map<Integer,Integer> playerScores){    this.playerScores = playerScores;  }
	
	public int getTurnNumber(){    return turnNumber;  }
	public void setTurnNumber(int turnNumber){    this.turnNumber = turnNumber;  }
	
	public int getNextTurnPlayerNum(){    return nextTurnPlayerNum;  }
	public void setNextTurnPlayerNum(int nextTurnPlayerNum){    this.nextTurnPlayerNum = nextTurnPlayerNum;  }

	public Date getCtime(){    return ctime;  }
	public void setCtime(Date ctime){    this.ctime = ctime;  }

	public Date getMtime(){    return mtime;  }
	public void setMtime(Date mtime){    this.mtime = mtime;  }
 //}}}

	public Set<WordPlacement> makeMove(User movingUser, Move move, TrieNode dictionary) throws Exception{ //TODO better exceptions
		if( move == null ) throw new Exception("bad move found"); 
		if( !move.isValid() ) throw new Exception("all letters must be within the same row or column");
		//TODO check that player number matches player making the move
		Integer userIndex = this.playerIds.indexOf(movingUser.getId());
		if( userIndex == null ) throw new Exception("This user isn't in the game!");
		if( userIndex != this.nextTurnPlayerNum ) throw new Exception("It's not this user's turn");
		
		//TODO check that the tiles in this move agree with the player's tile set
		Multiset<Tile> movingPlayerTiles = this.playerTiles.get(nextTurnPlayerNum);
		Multiset<Tile> moveTilesMap = move.mapify();
		for(Multiset.Entry<Tile> tilesentry : moveTilesMap.entrySet()){
			if( movingPlayerTiles.count( tilesentry.getElement() ) < tilesentry.getCount() ){
				throw new Exception("insufficient tiles!");
			}
		}

		//if this is the first move make sure that the center tile is used
		if( this.turnNumber == 0 && move.getTileAt(new GridPosition(7,7) )==null){
			throw new Exception("first move must include center square.");
		}
		
		//check that tile positions don't overlap with previously occupied squares on grid
		for(TilePlacement tp : move.getTilePositions()){
			GridPosition gp = tp.getGridPosition();
			int row = gp.getRow();
			int col = gp.getCol();
			if( this.boardState.getTileAt(row, col) != null ){
				throw new Exception("letter can't be placed on an occupied square");
			}
		}

		//TODO check that at least one tile is adjacent to an existing tile, unless it's the 1st turn
		
		//Scan and pull out a list of words
		HashSet<WordPlacement> allWords = new HashSet<WordPlacement>();
		for(TilePlacement tp : move.getTilePositions()){
			//List<TilePlacement> horizontalWord = scanLeftOf(tp.getGridPosition(), move);
			List<TilePlacement> horizontalWord = scan(tp.getGridPosition(), -1, 0, true, move);
			horizontalWord.add(tp);
			//horizontalWord.addAll( scanRightOf(tp.getGridPosition(), move) );
			horizontalWord.addAll( scan(tp.getGridPosition(), 1, 0, false, move) );
			if( horizontalWord.size() > 1){
				WordPlacement wp = new WordPlacement(horizontalWord, WordPlacement.Direction.HORIZONTAL);
				allWords.add( wp );
			}

			//List<TilePlacement> verticalWord = scanUpOf(tp.getGridPosition(), move);
			List<TilePlacement> verticalWord = scan(tp.getGridPosition(), 0, -1, true, move);
			verticalWord.add(tp);
			//verticalWord.addAll( scanDownOf(tp.getGridPosition(), move) );
			verticalWord.addAll( scan(tp.getGridPosition(), 0, 1, false, move) );

			if( verticalWord.size() > 1){
				WordPlacement wp = new WordPlacement(verticalWord, WordPlacement.Direction.VERTICAL);
				allWords.add( wp );
			}
		}

		//check that all words occur in dictionary
		for(WordPlacement wp : allWords){
			if( !dictionary.lookUp(wp.getWord()) ){
				throw new Exception(wp.getWord() + ": not in dictionary");
			}
		}

		//Multiset<Tile> movingPlayerTiles = this.playerTiles.get(nextTurnPlayerNum);
		////// OK it looks good, update shit
		for(WordPlacement wp : allWords){
			for(TilePlacement tp : wp.getTilePlacements() ){
				if( !tp.onBoard() ){
					this.boardState.setTileAt(tp.getTile(), tp.getGridPosition());
					movingPlayerTiles.remove(tp.getTile());
				}
			}
		}

		for( Tile t : this.tileBag.takeRandomTiles(7-movingPlayerTiles.size()) ){
			movingPlayerTiles.add(t);
		}

		this.turnNumber++;
		this.nextTurnPlayerNum = ( this.nextTurnPlayerNum + 1 ) % this.numPlayers;
		return allWords;
	}

	@PrePersist
	public void prePersist(DBObject db){//{{{
		Map<Integer,String> mapshit =  encodePlayerTiles(this.playerTiles);
		db.put("playerTilesMap", mapshit);
	}//}}}

	@PostLoad
	public void postLoad(){//{{{
		//TODO operate directly on dbobject
		this.playerTiles = decodePlayerTiles(this.playerTilesMap);
		this.tileBag = boardState.getTilesBag();
	}//}}}

	public static Map<Integer,String> encodePlayerTiles(Map<Integer,Multiset<Tile>> playerTiles){//{{{
		HashMap<Integer, String> tileSetsEncoded = new HashMap<Integer, String>();
		for(Map.Entry<Integer,Multiset<Tile>> entry : playerTiles.entrySet()){
			StringBuilder tilesString = new StringBuilder();
			for(Multiset.Entry<Tile> tilesentry : entry.getValue().entrySet()){
				for(int i=0;i<tilesentry.getCount();i++){
					tilesString.append(tilesentry.getElement().encode());
				}
			}
			tileSetsEncoded.put(entry.getKey(), tilesString.toString());
		}
		return tileSetsEncoded;
	}//}}}
	
	public static Map<Integer,Multiset<Tile>> decodePlayerTiles( Map<Integer,String> playerTilesMap ){//{{{
		HashMap<Integer,Multiset<Tile>> tileSetsEncoded = new HashMap<Integer,Multiset<Tile>>();
		for(Map.Entry<Integer,String> entry : playerTilesMap.entrySet()){
			HashMultiset<Tile> plTiles = HashMultiset.create();
			String tileSet = entry.getValue();
			for(int i=0;i<tileSet.length();i++){
				plTiles.add( Tiles.decode( tileSet.charAt(i) ) );
			}
			tileSetsEncoded.put(entry.getKey(), plTiles);
		}
		return tileSetsEncoded;
	}//}}}

	public ArrayList<TilePlacement> scan(GridPosition startPos, int dx, int dy, boolean prepend, Move m){//{{{
		ArrayList<TilePlacement> result = new ArrayList<TilePlacement>();
		GridPosition nextPos = startPos.move(dx, dy);
		while( nextPos != null ){
			Tile t = boardState.getTileAt(nextPos);
			if( t != null ){
				result.add( prepend ? 0 : result.size(), new TilePlacement(t, nextPos, true));
			}else{ 
				TilePlacement moveTile = m.getTileAt(nextPos);
				if( moveTile != null ){
					result.add( prepend ? 0 : result.size(), new TilePlacement(t, nextPos, true));
				} else {
					break;
				}
			}
			nextPos = nextPos.move(dx, dy); 
		}
		return result;
	}//}}}

	// SCAN FUNCS//{{{
	public ArrayList<TilePlacement> scanLeftOf(GridPosition startPos, Move m){//{{{
		ArrayList<TilePlacement> result = new ArrayList<TilePlacement>();
		GridPosition nextPos = startPos.moveLeft();
		while( nextPos != null ){
			Tile t = boardState.getTileAt(nextPos);
			if( t != null ){
				result.add(0, new TilePlacement(t, nextPos, true));
			}else{ 
				TilePlacement moveTile = m.getTileAt(nextPos);
				if( moveTile != null ) result.add(0, moveTile );
				else break;
			}
			nextPos = nextPos.moveLeft();
		}
		return result;
	}//}}}

	public List<TilePlacement> scanRightOf(GridPosition startPos, Move m){//{{{
		ArrayList<TilePlacement> result = new ArrayList<TilePlacement>();
		GridPosition nextPos = startPos.moveRight();
		while( nextPos != null ){
			Tile t = boardState.getTileAt(nextPos);
			if( t != null ){
				result.add(new TilePlacement(t, nextPos, true));
			}else{ 
				TilePlacement moveTile = m.getTileAt(nextPos);
				if( moveTile != null ) result.add(moveTile );
				else break;
			}
			nextPos = nextPos.moveRight();
		}
		return result;
	}//}}}
	
	public List<TilePlacement> scanUpOf(GridPosition startPos, Move m){//{{{
		ArrayList<TilePlacement> result = new ArrayList<TilePlacement>();
		GridPosition nextPos=startPos.moveUp();
		while( nextPos != null ){
			Tile t = boardState.getTileAt(nextPos);
			if( t != null ){
				result.add(0, new TilePlacement(t, nextPos, true));
			}else{ 
				TilePlacement moveTile = m.getTileAt(nextPos);
				if( moveTile != null ) result.add(0, moveTile );
				else break;
			}
			nextPos = nextPos.moveUp();
		}
		return result;
	}//}}}

	public List<TilePlacement> scanDownOf(GridPosition startPos, Move m){//{{{
		ArrayList<TilePlacement> result = new ArrayList<TilePlacement>();
		GridPosition nextPos = startPos.moveDown();
		while( nextPos != null ){
			Tile t = boardState.getTileAt(nextPos);
			if( t != null ){
				result.add(new TilePlacement(t, nextPos, true));
			}else{ 
				TilePlacement moveTile = m.getTileAt(nextPos);
				if( moveTile != null ) result.add(moveTile );
				else break;
			}
			nextPos = nextPos.moveDown();
		}
		return result;
	}//}}}//}}}

	public TileBag getTileBag(){    return tileBag;  }
	
	public void setTileBag(TileBag tileBag){    this.tileBag = tileBag;  }
}

