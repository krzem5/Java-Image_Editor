import com.krzem.image_editor.*;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.lang.Math;



public class LineTool extends Constants{
	public Main cls;
	public ImageEditorWindow i;
	public Vector s=null;
	public Vector e=null;



	public LineTool(Main cls,ImageEditorWindow i){
		this.cls=cls;
		this.i=i;
	}



	public void draw(Graphics2D g,Rectangle r){
		g.setColor(TOOLBAR_ELEM_ICON_COLOR);
		g.setStroke(new BasicStroke(4));
		g.drawLine((int)(r.x+r.w*(1-LINE_TOOL_ICON_SIZE_PROC)/2)-2,(int)(r.y+r.h*(1-LINE_TOOL_ICON_SIZE_PROC)/2)-2,(int)(r.x+r.w*(LINE_TOOL_ICON_SIZE_PROC+(1-LINE_TOOL_ICON_SIZE_PROC)/2)),(int)(r.y+r.h*(LINE_TOOL_ICON_SIZE_PROC+(1-LINE_TOOL_ICON_SIZE_PROC)/2)));
		if (this.s!=null&&this.e!=null){
			Rectangle ir=this.i._get_screen_rect();
			g.setColor(LINE_TOOL_OUTLINE_COLOR);
			g.setStroke(new BasicStroke(2));
			int ax=(int)this._map(this.s.x+0.5,0,this.i.img.getWidth(),ir.x,ir.x+ir.w);
			int ay=(int)this._map(this.s.y+0.5,0,this.i.img.getHeight(),ir.y,ir.y+ir.h);
			int bx=(int)this._map(this.e.x+0.5,0,this.i.img.getWidth(),ir.x,ir.x+ir.w);
			int by=(int)this._map(this.e.y+0.5,0,this.i.img.getHeight(),ir.y,ir.y+ir.h);
			g.drawLine(ax,ay,bx,by);
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
		else if(this.s!=null&&this.e!=null){
			Graphics2D g=this.i.img.createGraphics();
			g.setColor(this.i.tb.fgc);
			this._line(g,this.s.x,this.s.y,this.e.x,this.e.y);
			g.dispose();
			this.s=null;
			this.e=null;
		}
		else{
			this.s=null;
			this.e=null;
		}
	}



	public void start(){

	}



	public void click(){

	}



	public void cancel(){
		this.s=null;
		this.e=null;
	}



	private void _line(Graphics2D g,int sx,int sy,int ex,int ey){
		g.setStroke(new BasicStroke(PEN_TOOL_SIZE));
		g.drawLine(sx,sy,ex,ey);
	}



	private double _map(double v,double aa,double ab,double ba,double bb){
		return (v-aa)/(ab-aa)*(bb-ba)+ba;
	}
}