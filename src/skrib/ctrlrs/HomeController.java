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
import java.util.*;

@At("^/$")
public class HomeController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

    @Inject
	GameDAO games;
	@Inject
	UserProvider up;
	@Inject
	FlashProvider flash;

    @Override
    public WebResponse get(WebHit hit){//{{{
		Flash fl = flash.get();
		String success = fl.get("success");
		List<Game> allGames = games.find().asList();
		boolean newUser = success != null && success.equals("new_user");
        return responses.render("home.html", 
								ImmutableMap.of("user", up.get(), 
								                "allgames", allGames,
												"newUser", newUser));
    }//}}}

    @Override
    public WebResponse post(WebHit hit){ return get(hit); }

}
