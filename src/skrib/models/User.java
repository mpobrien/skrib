package skrib.models;
import com.google.code.morphia.*;
import com.mob.web.*;
import java.util.*;
import com.google.code.morphia.annotations.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.Arrays;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

@Entity
public class User implements SiteUser{
	
	@Id
	private String id;
    private String username;
    private String passwordHash;
    private String salt;
	private String auth;

    public String getUsername(){    return username;  }
    public void setUsername(String username){    this.username = username;  }
    
    public String getPasswordHash(){    return passwordHash;  }
    public void setPasswordHash(String passwordHash){    this.passwordHash = passwordHash;  }
    
    public String getSalt(){    return salt;  }
    public void setSalt(String salt){    this.salt = salt;  }

	public String getAuth(){    return auth;  }
	public void setAuth(String auth){    this.auth = auth;  }

	public byte[] getHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {//{{{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
		return digest.digest(password.getBytes("UTF-8"));
	}//}}}

	public void resetPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{//{{{
    	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] bSalt = new byte[8];
        random.nextBytes(bSalt);
		String newSalt = Base64.encodeBase64String(bSalt);
        String newPasswordHash = Base64.encodeBase64String( getHash(password, bSalt) );
		this.setSalt( newSalt );
		this.setPasswordHash( newPasswordHash );
	}//}}}
	
	public boolean checkPassword(String password){//{{{
		try{
			byte[] bSalt = Base64.decodeBase64( this.salt );
			String generatedPasswordHash = Base64.encodeBase64String( getHash( password, bSalt ) );
			return generatedPasswordHash.equals( this.passwordHash );
		}catch(Exception e){
			return false;
		}
	}//}}}

	public boolean getIsLoggedIn(){ return true; }
	
	public String getId(){    return id;  }
	public void setId(String id){    this.id = id;  }

}
