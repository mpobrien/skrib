package skrib.ctrlrs;
import com.mob.web.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.*;
import skrib.models.*;
import skrib.models.dao.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.inject.*;

@At("^/game/(\\w+)/$")
public class GameController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

    @Inject
	GameDAO games;
	@Inject
	UserProvider up;

    @Override
    public WebResponse get(WebHit hit){//{{{
		String id = args.get(0);
		User u = up.get();
		if( u.getIsLoggedIn() == false ) 
			return responses.reverseRedirect(LoginController.class);
		Game g = games.get(id);
		Integer playerNum = g.getPlayerIds().indexOf( u.getId() );
		//TODO check that playernum is not null!
		Multiset<Tile> myTiles = g.getPlayerTiles().get(playerNum);
		ImmutableMap m = new ImmutableMap.Builder()
							.put("board", g.getBoardState().toHtml())
							.put("user", u)
							.put("mytiles", myTiles)
							.build();
        return responses.render("board.html", m);
    }//}}}

    @Override
    public WebResponse post(WebHit hit){//{{{
		return get(hit);
	}//}}}

}

