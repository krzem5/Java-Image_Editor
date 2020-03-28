import com.krzem.image_editor.*;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.lang.Math;



public class CircleTool extends Constants{
	public Main cls;
	public ImageEditorWindow i;
	public Vector s=null;
	public Vector r=null;



	public CircleTool(Main cls,ImageEditorWindow i){
		this.cls=cls;
		this.i=i;
	}



	public void draw(Graphics2D g,Rectangle r){
		g.setColor(TOOLBAR_ELEM_ICON_COLOR);
		g.setStroke(new BasicStroke(4));
		g.fillOval(r.x+(r.w-(int)(r.w*CIRCLE_TOOL_ICON_SIZE_PROC))/2,r.y+(r.h-(int)(r.h*CIRCLE_TOOL_ICON_SIZE_PROC))/2,(int)(r.w*CIRCLE_TOOL_ICON_SIZE_PROC),(int)(r.h*CIRCLE_TOOL_ICON_SIZE_PROC));
		if (this.s!=null&&this.r!=null){
			Rectangle ir=this.i._get_screen_rect();
			g.setColor(CIRCLE_TOOL_OUTLINE_COLOR);
			g.setStroke(new BasicStroke(2));
			int ax=(int)this._map(this.s.x+0.5,0,this.i.img.getWidth(),ir.x,ir.x+ir.w);
			int ay=(int)this._map(this.s.y+0.5,0,this.i.img.getHeight(),ir.y,ir.y+ir.h);
			int bx=(int)this._map(this.r.x+0.5,0,this.i.img.getWidth(),ir.x,ir.x+ir.w);
			int by=(int)this._map(this.r.y+0.5,0,this.i.img.getHeight(),ir.y,ir.y+ir.h);
			g.drawLine(ax,ay,bx,by);
			int cr=(int)Math.sqrt((bx-ax)*(bx-ax)+(by-ay)*(by-ay));
			g.drawOval(ax-cr,ay-cr,cr*2,cr*2);
		}
	}



	public void update(){
		if (this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1){
			Vector p=this.i._get_screen_pixel_nocheck(this.cls.MOUSE_POS.x,this.cls.MOUSE_POS.y);
			if (p.x==-1&&p.y==-1){
				this.s=null;
				this.r=null;
			}
			else{
				if (this.s==null){
					this.s=p;
				}
				this.r=p;
			}
		}
		else if(this.s!=null&&this.r!=null){
			Graphics2D g=this.i.img.createGraphics();
			g.setColor(this.i.tb.bgc);
			int r=(int)Math.sqrt((this.r.x-this.s.x)*(this.r.x-this.s.x)+(this.r.y-this.s.y)*(this.r.y-this.s.y));
			g.fillOval(this.s.x-r,this.s.y-r,r*2,r*2);
			g.dispose();
			this.s=null;
			this.r=null;
		}
		else{
			this.s=null;
			this.r=null;
		}
	}



	public void start(){

	}



	public void click(){

	}



	public void cancel(){
		this.s=null;
		this.r=null;
	}



	private double _map(double v,double aa,double ab,double ba,double bb){
		return (v-aa)/(ab-aa)*(bb-ba)+ba;
	}
}