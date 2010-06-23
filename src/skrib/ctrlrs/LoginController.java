package skrib.ctrlrs;
//import skrib.forms.*;
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
import skrib.models.dao.*;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

@At("^/login/?$")
public class LoginController extends Controller{

	private static final Logger log = Logger.getLogger( LoginController.class );
	public static final String TEMPLATE = "login.html";
	public static final String LOGIN_COOKIE_NAME = "auth";

	@Inject
    UserDAO users;

    @Override
    public WebResponse get(WebHit hit){//{{{
		LoginForm form = new LoginForm();
		return responses.render(TEMPLATE, ImmutableMap.of("form", form));
	}//}}}

    @Override
    public WebResponse post(WebHit hit){//{{{
		LoginForm form = new LoginForm();
		form.bind(hit.getRequest());
		form.validate();
		Map context = ImmutableMap.of("form", form);
		if( form.hasErrors() ){
			return responses.render(TEMPLATE, context);
		}else{
			User testUser = users.getByUsername( form.getUsername().getValue() );
			if( testUser == null ){
				form.error("db", "Username doesn't exist");
				return responses.render(TEMPLATE, context);
			}else{
				if( !testUser.checkPassword( form.getPassword().getValue() ) ){
					form.error(form.getPassword(), "Invalid password");
					return responses.render(TEMPLATE, context);
				}else{
					log.error("successful log in");
					login(testUser, hit.getResponse());
					return responses.reverseRedirect(HomeController.class);
				}
			}
		}
    }//}}}

	public static void login(User user, HttpServletResponse res){//{{{
		Cookie c = new Cookie(LOGIN_COOKIE_NAME, user.getAuth() );
		c.setPath("/");
		c.setMaxAge(60*60*24*14);
		res.addCookie( c );
	}//}}}

    class LoginForm extends AbstractForm{//{{{
        private final StringField username;
        private final StringField password;

        public LoginForm(){
            this.username = string("username");
            this.password = string("password");
			this.password.setWidget( WidgetGenerator.password(this.password) );
        }
		
		public void validate(){
		}

        public StringField getUsername(){    return username;  }
        public StringField getPassword(){    return password;  }

    }//}}}
        
}
