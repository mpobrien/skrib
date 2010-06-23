package skrib.models;

public class BlankTile implements Tile{

	public static final Character BLANK_ENCODING = '-';
    private final Character ch;

	public BlankTile(Character ch){
		this.ch = Character.toUpperCase(ch);
	}

	public BlankTile(){
		this.ch = null;
	}

	@Override
	public Character getCharacter(){//{{{
		return this.ch;
	}//}}}

	@Override
	public int getPoints(){//{{{
		return 0;
	}//}}}

	@Override
	public int getFrequency(){//{{{
		return 2;
	}//}}}

	@Override
	public boolean blank(){//{{{
		return true;
	}//}}}

	@Override
	public Character encode(){//{{{
		if( this.ch != null ){
			return Character.toUpperCase(this.ch);
		}else{
			return BLANK_ENCODING;
		}
	}//}}}

	@Override
	public String toHtml(){
		return "<div class=\"tile blankTile\"><span class=\"letter\">"
			+ (this.ch != null ? Character.toUpperCase( getCharacter() ) : "&nbsp;" ) + "</span>"
			 + "<span class=\"score\">&nbsp;</span>"
			 + "</div>";
	}
}
