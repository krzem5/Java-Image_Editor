import com.krzem.image_editor.*;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.lang.Math;



public class PenTool extends Constants{
	public Main cls;
	public ImageEditorWindow i;
	public Vector last=null;



	public PenTool(Main cls,ImageEditorWindow i){
		this.cls=cls;
		this.i=i;
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



	public void update(){
		if (this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1){
			Vector p=this.i._get_screen_pixel(this.cls.MOUSE_POS.x,this.cls.MOUSE_POS.y);
			if (p.x==-1&&p.y==-1){
				this.last=null;
			}
			else{
				Graphics2D g=this.i.img.createGraphics();
				g.setColor(this.i.tb.fgc);
				if (last==null){
					g.fillRect(p.x-PEN_TOOL_SIZE/2,p.y-PEN_TOOL_SIZE/2,PEN_TOOL_SIZE,PEN_TOOL_SIZE);
				}
				else{
					this._line(g,p.x,p.y,this.last.x,this.last.y);
				}
				this.last=p;
				g.dispose();
			}
		}
		else{
			this.last=null;
		}
	}



	public void start(){

	}



	public void click(){

	}



	public void cancel(){
		this.last=null;
	}



	private void _line(Graphics2D g,int sx,int sy,int ex,int ey){
		g.setStroke(new BasicStroke(PEN_TOOL_SIZE));
		g.drawLine(sx,sy,ex,ey);
	}
}