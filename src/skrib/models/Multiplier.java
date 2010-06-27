package skrib.models;

public class Multiplier{

    enum MultiplierType{//{{{
        NONE(false, 1),
        DOUBLE_LETTER(false, 2),//("AFEEEE");
        DOUBLE_WORD(true, 2),//("FFC0CB"),
        TRIPLE_LETTER(false, 3),//("4169E1"),
        TRIPLE_WORD(true, 3);//("FF0000");
		private final boolean isWordMult;
		private final int factor;
		MultiplierType(boolean isWordMult, int factor){
			this.isWordMult = isWordMult;
			this.factor = factor;
		}

		public int getFactor(){    return factor;  }
		public boolean getIsWordMult(){    return isWordMult;  }
    };//}}}

    private static MultiplierType multiplierGrid[][] = new MultiplierType[Board.GRID_SIZE][Board.GRID_SIZE];

	static {//{{{
		for( int i=0; i < multiplierGrid.length; i++ ){
			for( int j=0; j < multiplierGrid[i].length; j++ ){
				multiplierGrid[i][j] = MultiplierType.NONE;
			}
		}
		setMultiplierAt(3,0,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(11,0,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(6,2,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(8,2,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(0,3,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(7,3,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(14,3,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(2, 6,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(6,6,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(8,6,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(12,6,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(3, 7,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(11,7,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(2, 8,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(6,8,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(8,8,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(12,8,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(0,11,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(7,11,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(14,11,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(6,12,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(8,12,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(3,14,MultiplierType.DOUBLE_LETTER);
		setMultiplierAt(11,14,MultiplierType.DOUBLE_LETTER);
		/////////////
		setMultiplierAt(1,1,MultiplierType.DOUBLE_WORD) ;
		setMultiplierAt(13,1,MultiplierType.DOUBLE_WORD);
		setMultiplierAt(2,2,MultiplierType.DOUBLE_WORD) ;
		setMultiplierAt(12,2,MultiplierType.DOUBLE_WORD);
		setMultiplierAt(3,3,MultiplierType.DOUBLE_WORD) ;
		setMultiplierAt(11,3,MultiplierType.DOUBLE_WORD);
		setMultiplierAt(4,4,MultiplierType.DOUBLE_WORD) ;
		setMultiplierAt(10,4,MultiplierType.DOUBLE_WORD);
		setMultiplierAt(7,7,MultiplierType.DOUBLE_WORD);
		setMultiplierAt(1,13,MultiplierType.DOUBLE_WORD) ;
		setMultiplierAt(13,13,MultiplierType.DOUBLE_WORD);
		setMultiplierAt(2,12,MultiplierType.DOUBLE_WORD) ;
		setMultiplierAt(12,12,MultiplierType.DOUBLE_WORD);
		setMultiplierAt(3,11,MultiplierType.DOUBLE_WORD) ;
		setMultiplierAt(11,11,MultiplierType.DOUBLE_WORD);
		setMultiplierAt(4,10,MultiplierType.DOUBLE_WORD) ;
		setMultiplierAt(10,10,MultiplierType.DOUBLE_WORD);
		/////////////
		setMultiplierAt(5,1,MultiplierType.TRIPLE_LETTER) ;
		setMultiplierAt(9,1,MultiplierType.TRIPLE_LETTER);
		setMultiplierAt(1,5,MultiplierType.TRIPLE_LETTER) ;
		setMultiplierAt(5,5,MultiplierType.TRIPLE_LETTER) ;
		setMultiplierAt(9,5,MultiplierType.TRIPLE_LETTER) ;
		setMultiplierAt(13,5,MultiplierType.TRIPLE_LETTER);
		setMultiplierAt(1,9,MultiplierType.TRIPLE_LETTER) ;
		setMultiplierAt(5,9,MultiplierType.TRIPLE_LETTER) ;
		setMultiplierAt(9,9,MultiplierType.TRIPLE_LETTER) ;
		setMultiplierAt(13,9,MultiplierType.TRIPLE_LETTER);
		setMultiplierAt(5,13,MultiplierType.TRIPLE_LETTER) ;
		setMultiplierAt(9,13,MultiplierType.TRIPLE_LETTER);
		//////////////
		setMultiplierAt(0,0,MultiplierType.TRIPLE_WORD) ;
		setMultiplierAt(7,0,MultiplierType.TRIPLE_WORD) ;
		setMultiplierAt(14,0,MultiplierType.TRIPLE_WORD);
		setMultiplierAt(0,7,MultiplierType.TRIPLE_WORD) ;
		setMultiplierAt(14,7,MultiplierType.TRIPLE_WORD);
		setMultiplierAt(0,14,MultiplierType.TRIPLE_WORD) ;
		setMultiplierAt(7,14,MultiplierType.TRIPLE_WORD) ;
		setMultiplierAt(14,14,MultiplierType.TRIPLE_WORD);
	}//}}}

	private static void setMultiplierAt(int row, int col, MultiplierType mult){//{{{
		multiplierGrid[row][col] = mult;
	}//}}}

	 public static MultiplierType getMultiplierAt(int row, int col){//{{{
		return multiplierGrid[row][col];
	}//}}}

		
}
