package skrib.models.dao;
import skrib.models.*;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.*;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.*;
import com.google.inject.*;

public class BoardDAO extends DAO<Board,String>{

    @Inject
    public BoardDAO(Morphia morphia, Mongo mongo){
        super(Board.class, mongo, morphia, "boards");
    }

}
