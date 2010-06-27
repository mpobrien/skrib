package skrib.ctrlrs;
import com.mob.web.*;
import com.mob.forms.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.*;
import skrib.models.*;
import skrib.models.dao.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.inject.*;

@At("^/game/new/$")
public class CreateGameController extends Controller{
	Logger log = Logger.getLogger( HomeController.class );

    @Inject
	GameDAO games;
	@Inject
	UserProvider up;
	@Inject
	FlashProvider flash;

    @Override
    public WebResponse get(WebHit hit){//{{{
		CreateGameForm form = new CreateGameForm();
		return responses.render("newgame.html", ImmutableMap.of("form",form));
    }//}}}

    @Override
    public WebResponse post(WebHit hit){//{{{
		CreateGameForm form = new CreateGameForm();
		form.bind(hit.getRequest());
		form.validate();
		if( form.hasErrors() ){
			return responses.render("newgame.html", ImmutableMap.of("form",form));
		}else{
			User creator = up.get();
			Integer numPlayers = Integer.parseInt(form.getNumplayers().getValue());
			Game g = games.createNewGame(creator, numPlayers);
			return responses.reverseRedirect(GameController.class, g.getId() );
		}
	}//}}}

    class CreateGameForm extends AbstractForm{//{{{
        private final StringField gamename;
        private final SetField numplayers;

        public CreateGameForm(){
            this.gamename = string("gamename");
            this.numplayers = choices("numplayers", ImmutableSet.of("1","2","3","4"));
        }
		
		public void validate(){ }
        public SetField getNumplayers(){    return numplayers;  }
        public StringField getGamename(){    return gamename;  }

    }//}}}

}

