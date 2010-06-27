package skrib.models;

public class BlankTile implements Tile{

	public static final Character BLANK_ENCODING = '-';
    private final Character ch;

	public BlankTile(Character ch){ this.ch = Character.toUpperCase(ch); }

	public BlankTile(){ this.ch = null; }

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
	public String getHtml(){//{{{
		return "<div class=\"tile blankTile\"><span class=\"letter\">"
			+ (this.ch != null ? Character.toUpperCase( getCharacter() ) : "&nbsp;" ) + "</span>"
			 + "<span class=\"score\">&nbsp;</span>"
			 + "</div>";
	}//}}}

	public String toString(){//{{{
		if( this.ch != null ){
			return Character.toString(Character.toUpperCase(this.ch));
		}else{
			return ":";
		}
	}//}}}

	public int hashCode(){//{{{
		if( this.ch == null ) return 0;
		return this.ch.hashCode();
	}//}}}

	public boolean equals(Object o){//{{{
		if( o == null || !(o instanceof BlankTile)) return false;
		BlankTile bt = (BlankTile)o;
		//TODO clean this up
		if( this.getCharacter()==null && bt.getCharacter()==null) return true;
		if( this.getCharacter()==null && bt.getCharacter()!=null) return false;
		if( this.getCharacter()!=null && bt.getCharacter()==null) return false;
		if(!this.getCharacter().equals(bt.getCharacter())) return false;
		return true;
	}//}}}

}
