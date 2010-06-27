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
		Game g = new Game(creator, numPlayers);
		save(g);
		return g;
	}//}}}

    public void postMoveUpdate(Game g){//{{{
		Query<Game> q1 = createQuery().filter("_id =", g.getId());
		UpdateOperations uops = createUpdateOperations()
			                          .set("boardState", g.getBoardState())
									  .set("turnNumber", g.getTurnNumber())
									  .set("nextTurnPlayerNum", g.getNextTurnPlayerNum())
									  .set("playerTilesMap", g.encodePlayerTiles(g.getPlayerTiles()) )
									  .set("mtime", g.getMtime());
		ds.update(q1, uops);
	}//}}}

}
