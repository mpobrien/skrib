package skrib.ctrlrs;
import com.mob.web.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.*;
import skrib.models.*;
import com.google.common.base.*;
import com.google.common.collect.*;

@At("^/$")
public class HomeController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

    @Override
    public WebResponse get(WebHit hit){
		Board b = new Board();
        //return new StringWebResponse("Hello World!");
        return responses.render("home.html", ImmutableMap.of("board",b.toHtml()));
    }

    @Override
    public WebResponse post(WebHit hit){
		return get(hit);
    }
}
