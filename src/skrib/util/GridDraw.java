package skrib.util;
import com.google.common.base.*;
import com.google.common.collect.*;
import org.apache.commons.lang.StringUtils;
import skrib.models.GridPosition;

public class GridDraw{

	private final int cols, rows, cellWidth;

	public static final String BOX_UPPERLEFT = "\u250c";//{{{
	public static final String BOX_HORIZONTAL = "\u2500";
	public static final String BOX_VERTICAL = "\u2502";
	public static final String BOX_UPPERRIGHT = "\u2510";
	public static final String BOX_LOWERLEFT = "\u2514";
	public static final String BOX_LOWERRIGHT = "\u2519";
	public static final String BOX_CROSS = "\u253c";
	public static final String BOX_TOP_TEE= "\u252c";
	public static final String BOX_BOTTOM_TEE = "\u2534";
	public static final String BOX_LEFT_TEE = "\u251c";
	public static final String BOX_RIGHT_TEE = "\u2524";//}}}

	public GridDraw(int rows, int cols, int cellWidth){//{{{
		this.cols = cols;
		this.rows = rows;
		this.cellWidth = cellWidth;
	}//}}}

    public String getGridString(Function<GridPosition, String> gridFunc){//{{{
		StringBuilder result = new StringBuilder();
		String cellSpacer = StringUtils.repeat(BOX_HORIZONTAL, this.cellWidth);
		for(int i=0;i<cols;i++){
			result.append( i==0 ? BOX_UPPERLEFT : "" )
				  .append( cellSpacer )
				  .append( i==cols - 1 ? BOX_UPPERRIGHT + "\n" : BOX_TOP_TEE  );
		}
		String interstitial = "";
		interstitial += BOX_LEFT_TEE;
		for(int j=0;j<cols;j++){
			interstitial += cellSpacer + (j==cols-1 ? BOX_RIGHT_TEE : BOX_CROSS);
		}

		for(int j=0;j<rows;j++){
			for(int i=0;i<cols;i++){
				GridPosition gp = new GridPosition(i,j);
				result.append( i==0 ? BOX_VERTICAL : "" )
					  .append( StringUtils.center(gridFunc.apply(gp), this.cellWidth) + BOX_VERTICAL );
			}
			result.append( "\n" + (j!=rows-1 ? interstitial + "\n" : ""));
		}

		for(int i=0;i<cols;i++){
			result.append( i==0 ? BOX_LOWERLEFT : "" )
				  .append( cellSpacer )
				  .append( i==cols-1 ? BOX_LOWERRIGHT : BOX_BOTTOM_TEE  );
		}
		return result.toString();
	}//}}}

}
