package skrib.models.dao;
import skrib.models.*;
import skrib.util.TileBag;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.code.morphia.query.*;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.*;
import com.google.inject.*;
import java.util.*;

public class GameDAO extends DAO<Game,String>{

    @Inject
    public GameDAO(Morphia morphia, Mongo mongo){
        super(Game.class, mongo, morphia, "games");
    }

	public Game createNewGame(User creator, Integer numPlayers){//{{{
		Game g = new Game();
		g.setNumPlayers(numPlayers);
		Board b = new Board();
		TileBag tb = new TileBag();
		g.setBoardState(b);
		g.setTurnNumber(0);
		g.setNextTurnPlayerNum(0);

		HashMap<Integer,Multiset<Tile>> playerTiles = new HashMap<Integer, Multiset<Tile>>();
		for( int i=0; i<numPlayers;i++){
			List<Tile> playerTileList = tb.takeRandomTiles(7);
			Multiset<Tile> playerTileListMulti = HashMultiset.create();
			for(Tile t: playerTileList){
				playerTileListMulti.add(t);
			}
			playerTiles.put(i, playerTileListMulti);
		}
		g.setPlayerTiles(playerTiles);
		save(g);
		return g;
	}//}}}

    public void postMoveUpdate(Game g){//{{{
		Query<Game> q1 = createQuery().filter("_id =", g.getId());
		UpdateOperations uops = createUpdateOperations()
			                          .set("boardState", g.getBoardState())
									  .set("turnNumber", g.getTurnNumber())
									  .set("nextTurnPlayerNum", g.getNextTurnPlayerNum());
		ds.update(q1, uops);
	}//}}}

}
