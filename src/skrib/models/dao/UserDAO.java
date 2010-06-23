package skrib.models.dao;
import skrib.models.*;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.*;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.*;
import com.google.inject.*;

public class UserDAO extends DAO<User,String>{

    @Inject
    public UserDAO(Morphia morphia, Mongo mongo){
        super(User.class, mongo, morphia, "mydb");
    }

	public User getByUsername(String username){
		return findOne(createQuery().filter("username =", username));
	}

	public User getByAuth(String auth){
		return findOne(createQuery().filter("auth =", auth));
	}

}
