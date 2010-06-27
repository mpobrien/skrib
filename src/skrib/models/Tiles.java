package skrib.models;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public class Tiles{

	public enum LetterTile implements Tile{//{{{
		A('a',9,1),
		B('b',2,3),
		C('c',2,3),
		D('d',4,2),
		E('e',12,1),
		F('f',2,4),
		G('g',3,2),
		H('h',2,4),
		I('i',9,1),
		J('j',1,8),
		K('k',1,5),
		L('l',4,1),
		M('m',2,3),
		N('n',6,1),
		O('o',8,1),
		P('p',2,3),
		Q('q',1,10),
		R('r',6,1),
		S('s',4,1),
		T('t',6,1),
		U('u',4,1),
		V('v',2,4),
		W('w',2,4),
		X('x',1,8),
		Y('y',2,4),
		Z('z',1,10);
		private Character ch;
		private final int freq;
		private final int points;
		LetterTile(Character ch, int freq, int points){
			this.ch = ch;
			this.points = points;
			this.freq = freq;
		}

		public Character getCharacter(){
			return this.ch;
		}

		public int getFrequency(){
			return this.freq;
		}

		public int getPoints(){
			return this.points;
		}

		public boolean blank(){
			return false;
		}

		public Character encode(){
			return this.ch;
		}

		public String toHtml(){
			return "<div class=\"tile\"><span class=\"letter\">"
				 + Character.toUpperCase( getCharacter() ) + "</span>"
				 + "<span class=\"score\">" + getPoints() + "</span>"
				 + "</div>";
		}
	}//}}}

	public static final Map<Character, LetterTile> charMaps = //{{{
		Maps.uniqueIndex(
					new Iterable<LetterTile>(){
						public Iterator<LetterTile> iterator(){
							return Iterators.forArray(LetterTile.class.getEnumConstants());
						}
					},
					
					new Function<LetterTile,Character>(){
						public Character apply(LetterTile l){
							return l.getCharacter();
						}
					}
				);
//}}}

	public static Tile decode(Character c){//{{{
		if( charMaps.containsKey( c ) ){
			return charMaps.get( c );
		}else{
			if( Character.isUpperCase(c) ){
				return new BlankTile( c );
			}else if( c.equals( BlankTile.BLANK_ENCODING ) ){
				return new BlankTile();
			}else{
				return null;
			}
		}
	}//}}}

}

