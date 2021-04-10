import com.krzem.image_editor.*;
import java.awt.BasicStroke;
import java.awt.Graphics2D;



public class ShapeToolGroup extends Constants{
	public Main cls;



	public static final String TOOL_NAMES(){
		return "rectoutline,rect,circleoutline,circle";
	}



	public ShapeToolGroup(Main cls){
		this.cls=cls;
	}



	public void draw(Graphics2D g,Rectangle r){
		g.setColor(TOOLBAR_ELEM_ICON_COLOR);
		g.setStroke(new BasicStroke(3));
		int[] txp={(int)(r.x+r.w*PEN_TOOL_ICON_SCALE),(int)(r.x+r.w*PEN_TOOL_ICON_SCALE*2),(int)(r.x+r.w*PEN_TOOL_ICON_SCALE*1.25)};
		int[] typ={(int)(r.y+r.h*PEN_TOOL_ICON_SCALE),(int)(r.y+r.h*PEN_TOOL_ICON_SCALE*1.25),(int)(r.y+r.h*PEN_TOOL_ICON_SCALE*2)};
		g.fillPolygon(txp,typ,3);
		g.drawPolygon(txp,typ,3);
		int[] bxp={(int)(r.x+r.w*PEN_TOOL_ICON_SCALE*2),(int)(r.x+r.w*PEN_TOOL_ICON_SCALE*1.25),(int)(r.x+r.w*PEN_TOOL_ICON_SCALE*3),(int)(r.x+r.w*PEN_TOOL_ICON_SCALE*3.75)};
		int[] byp={(int)(r.y+r.h*PEN_TOOL_ICON_SCALE*1.25),(int)(r.y+r.h*PEN_TOOL_ICON_SCALE*2),(int)(r.y+r.h*PEN_TOOL_ICON_SCALE*3.75),(int)(r.y+r.h*PEN_TOOL_ICON_SCALE*3)};
		g.drawPolygon(bxp,byp,4);
	}
}