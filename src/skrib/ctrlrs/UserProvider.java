package skrib.ctrlrs;
import com.google.inject.*;
import skrib.models.dao.*;
import skrib.models.*;
import com.google.inject.servlet.*;
import com.mob.web.*;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.regex.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.lang.reflect.*;
import org.apache.log4j.*;

@RequestScoped
public class UserProvider extends ContextProcessor implements Provider<User>{

	private static final Logger log = Logger.getLogger( UserProvider.class );
    private ServletRequest request;
	private UserDAO users;

	private User user = new AnonymousUser();
    
	@Inject
    public UserProvider(HttpServletRequest request, UserDAO users){//{{{
        this.request = request;
		this.users = users;
    }//}}}

	public void process(HttpServletRequest request, HttpServletResponse respone){//{{{
		Cookie[] cookies = request.getCookies();
		if( cookies == null || cookies.length == 0 ){
			return;
		}else{
			for( Cookie c : cookies ){
				if( (c.getName() + "").equals(LoginController.LOGIN_COOKIE_NAME) ){
					String auth = c.getValue() + "";
					if( auth.length() > 0 ){
						User u = users.getByAuth(auth);
						if( u == null ){
							u = new AnonymousUser();
						}
						this.user = u;
						return;
					}
				}
			}
			return;
		}
	}//}}}

	public User get(){//{{{
		return this.user;
	}//}}}

}
