import com.krzem.image_editor.*;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.lang.Math;



public class RectOutlineTool extends Constants{
	public Main cls;
	public ImageEditorWindow i;
	public Vector s=null;
	public Vector e=null;



	public RectOutlineTool(Main cls,ImageEditorWindow i){
		this.cls=cls;
		this.i=i;
	}



	public void draw(Graphics2D g,Rectangle r){
		g.setColor(TOOLBAR_ELEM_ICON_COLOR);
		g.setStroke(new BasicStroke(4));
		int[] xp={(int)(r.x+r.w*(1-RECT_TOOL_ICON_SIZE_PROC)/2)-1,(int)(r.x+r.w*(1-RECT_TOOL_ICON_SIZE_PROC)/2)-1,(int)(r.x+r.w*(RECT_TOOL_ICON_SIZE_PROC+(1-RECT_TOOL_ICON_SIZE_PROC)/2)),(int)(r.x+r.w*(RECT_TOOL_ICON_SIZE_PROC+(1-RECT_TOOL_ICON_SIZE_PROC)/2))};
		int[] yp={(int)(r.y+r.h*(1-RECT_TOOL_ICON_SIZE_PROC)/2)-1,(int)(r.y+r.h*(RECT_TOOL_ICON_SIZE_PROC+(1-RECT_TOOL_ICON_SIZE_PROC)/2)),(int)(r.y+r.h*(RECT_TOOL_ICON_SIZE_PROC+(1-RECT_TOOL_ICON_SIZE_PROC)/2)),(int)(r.y+r.h*(1-RECT_TOOL_ICON_SIZE_PROC)/2)-1};
		g.drawPolygon(xp,yp,4);
		if (this.s!=null&&this.e!=null){
			Rectangle ir=this.i._get_screen_rect();
			g.setColor(RECT_TOOL_OUTLINE_COLOR);
			g.setStroke(new BasicStroke(2));
			int ax=(int)this._map(this.s.x+(this.e.x<this.s.x?1:0),0,this.i.img.getWidth(),ir.x,ir.x+ir.w);
			int ay=(int)this._map(this.s.y+(this.e.y<this.s.y?1:0),0,this.i.img.getHeight(),ir.y,ir.y+ir.h);
			int bx=(int)this._map(this.e.x+(this.s.x<=this.e.x?1:0),0,this.i.img.getWidth(),ir.x,ir.x+ir.w);
			int by=(int)this._map(this.e.y+(this.s.y<=this.e.y?1:0),0,this.i.img.getHeight(),ir.y,ir.y+ir.h);
			g.drawRect(Math.min(ax,bx),Math.min(ay,by),Math.abs(bx-ax)+1,Math.abs(by-ay));
		}
	}



	public void update(){
		if (this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1){
			Vector p=this.i._get_screen_pixel_nocheck(this.cls.MOUSE_POS.x,this.cls.MOUSE_POS.y);
			if (p.x==-1&&p.y==-1){
				this.s=null;
				this.e=null;
			}
			else{
				if (this.s==null){
					this.s=p;
				}
				this.e=p;
			}
		}
		else if (this.s!=null&&this.e!=null){
			Graphics2D g=this.i.img.createGraphics();
			g.setColor(this.i.tb.fgc);
			g.drawRect(Math.min(this.s.x,this.e.x),Math.min(this.s.y,this.e.y),Math.abs(this.e.x-this.s.x)+1,Math.abs(this.e.y-this.s.y)+1);
			g.dispose();
			this.s=null;
			this.e=null;
		}
		else{
			this.s=null;
			this.e=null;
		}
	}



	public void click(){

	}



	public void cancel(){
		this.s=null;
		this.e=null;
	}



	private double _map(double v,double aa,double ab,double ba,double bb){
		return (v-aa)/(ab-aa)*(bb-ba)+ba;
	}
}