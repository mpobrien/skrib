package skrib.ctrlrs;
import skrib.models.*;
import com.mob.web.*;
import com.mob.forms.*;
import com.mob.forms.widgets.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import org.apache.log4j.*;
import java.util.*;
import com.google.inject.*;
import com.google.common.collect.*;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

@At("^/logout/?$")
public class LogoutController extends Controller{

	private static final Logger log = Logger.getLogger( LogoutController.class );

    @Override
    public WebResponse get(WebHit hit){//{{{
		logout(hit.getResponse());
		return responses.reverseRedirect(LoginController.class);
	}//}}}

	public void logout(HttpServletResponse res){//{{{
		Cookie cookie = new Cookie( "auth", "" );
		cookie.setPath( "/" );
		res.addCookie( cookie );
	}//}}}

}
