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
		Game g = games.get(id);
        return responses.render("board.html", 
				ImmutableMap.of("board", g.getBoardState().toHtml(), "user", up.get()) );
    }//}}}

    @Override
    public WebResponse post(WebHit hit){//{{{
		return get(hit);
	}//}}}

}

