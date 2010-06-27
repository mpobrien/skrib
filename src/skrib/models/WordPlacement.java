package skrib.models;
import java.util.*;

public class WordPlacement{

    enum Direction{ HORIZONTAL, VERTICAL };
    private final List<TilePlacement> tilePlacements;
    private final Direction direction;
    private final String word;
	private final int score;

    public WordPlacement(List<TilePlacement> tilePlacements, Direction direction ){
        this.tilePlacements = tilePlacements;
        this.direction = direction;
        String tempWord = "";
        for( TilePlacement tp : tilePlacements){
            Tile t = tp.getTile();
            tempWord += t.getCharacter();
        }
        this.word = tempWord;
		this.score = calculateScore();
    }
    
    public List<TilePlacement> getTilePlacements(){ return tilePlacements;  }

    public GridPosition getStartPosition(){
        return this.tilePlacements.get(0).getGridPosition();
    }

    public Direction getDirection(){ return direction;  }

    public int hashCode(){
        int hash = getDirection().hashCode();
        for( TilePlacement tp : this.tilePlacements){
            hash = 37 * hash + tp.hashCode();
        }
        return hash;
    }

    public boolean equals(Object o){
        if( o==null || !(o instanceof WordPlacement) ) return false;
        WordPlacement wp = (WordPlacement)o;
        if( !this.getStartPosition().equals( wp.getStartPosition() ) ) return false;
        if( !this.getDirection().equals( wp.getDirection() ) ) return false;

        if( wp.getTilePlacements().size() != this.getTilePlacements().size() ) return false;
        for( int i =0; i < tilePlacements.size(); i++){
            TilePlacement tp = this.tilePlacements.get(i);
            if( !tp.equals( wp.getTilePlacements().get(i) ) ) return false;
        }
        return true; 
    }
    
    public String toString(){
        return String.format("%s at %s (%s): %d pts",
                             this.direction.toString(),
                             this.getStartPosition().toString(),
                             this.word,
                             this.getScore());
    }

    public int getScore(){ return this.score; }

    private Integer calculateScore(){
        Integer wordMultiplier = 1;
        Integer letterScore = 0;
        int numOffBoardTiles = 0;
        for( TilePlacement tp : tilePlacements){
            if( !tp.onBoard() ){
                numOffBoardTiles += 1;
                Multiplier.MultiplierType mult = 
                    Multiplier.getMultiplierAt(tp.getGridPosition().getRow(),
                                               tp.getGridPosition().getCol());
                if( mult.getIsWordMult() ){
                    wordMultiplier *= mult.getFactor();
                    letterScore += tp.getTile().getPoints();
                }else{
                    letterScore += tp.getTile().getPoints() * mult.getFactor();
                }
            }else{
                letterScore += tp.getTile().getPoints();
            }
            Tile t = tp.getTile();
        }
        //TODO bingo bonus
        return letterScore * wordMultiplier;
    }

    public String getWord(){    return word;  }
}
