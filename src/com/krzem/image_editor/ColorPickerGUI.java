package com.krzem.image_editor;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.lang.Math;



public class ColorPickerGUI extends GUI{
	public Main cls;
	public ColorPaletteGUI cpg;
	public Color C;
	public int drag=0;
	public int idx=-1;



	public ColorPickerGUI(Main cls,ColorPaletteGUI cpg){
		this.cls=cls;
		this.cpg=cpg;
		this._set(COLOR_PICKER_GUI_DEFAULT_COLOR.getRed(),COLOR_PICKER_GUI_DEFAULT_COLOR.getGreen(),COLOR_PICKER_GUI_DEFAULT_COLOR.getBlue(),COLOR_PICKER_GUI_DEFAULT_COLOR.getAlpha());
	}



	public ColorPickerGUI(Main cls,ColorPaletteGUI cpg,Color c,int i){
		this.cls=cls;
		this.cpg=cpg;
		this.idx=i;
		this._set(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
	}



	public void close(){
		this.close_gui();
		this.cpg.add(this.idx,this.C);
		this.cpg.closed_gui=true;
	}



	public void close_gui(){
		this.cpg.GUI=null;
	}



	@Override
	public void draw(Graphics2D g){
		this._draw(g);
		Rectangle r=this._get_frame_rect();
		Rectangle cr=this._get_color_rect();
		Rectangle sr=this._get_slider_rect();
		Rectangle asr=this._get_alpha_slider_rect();
		Rectangle pr=this._get_preview_rect();
		Color COL=this._rgb_to_hsv(this.C);
		g.setColor(GUI_FRAME_BG_COLOR);
		g.fillRect(r.x,r.y,r.w,r.h);
		double xstep=(double)(cr.w)/100;
		double ystep=(double)(cr.h)/100;
		double xo=0;
		double yo=0;
		double cy=cr.y+0;
		while (cy<cr.y+cr.h){
			double cx=cr.x+0;
			while (cx<cr.x+cr.w){
				g.setColor(this._hsv_to_rgb(COL.getRed()/255f,(float)this._map(cx,cr.x,cr.x+cr.w,0,1),(float)this._map(cy,cr.y,cr.y+cr.h,1,0),1.0f));
				g.fillRect((int)cx,(int)cy,(int)xstep+(int)xo,(int)ystep+(int)yo);
				xo+=xstep;
				xo=xo%1;
				cx+=(int)xstep+(int)xo;
			}
			yo+=ystep;
			yo=yo%1;
			cy+=(int)ystep+(int)yo;
		}
		double step=(double)(sr.h)/360;
		double o=0;
		double hy=sr.y+0;
		while (hy<sr.y+sr.h){
			g.setColor(this._hsv_to_rgb((float)this._map(hy,sr.y,sr.y+sr.h,0,1),1f,1f,1f));
			g.fillRect(sr.x,(int)hy,sr.w,(int)step+(int)o);
			o=o%1;
			o+=step;
			hy+=(int)step+(int)o;
		}
		double s=0;
		double gs=0;
		for (double y=asr.y;y<=asr.y+asr.h;y+=COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.height){
			s=gs+0;
			gs=(gs+1)%2;
			for (double x=asr.x;x<=asr.x+asr.w;x+=COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.width){
				if (s==0){
					g.setColor(COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_A);
				}
				else{
					g.setColor(COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_B);
				}
				g.fillRect((int)x,(int)y,(int)COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.width,(int)COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.height);
				s=(s+1)%2;
			}
		}
		step=(double)(asr.h)/255;
		o=0;
		double ay=asr.y+0;
		while (ay<asr.y+asr.h){
			g.setColor(this._get_with_alpha((int)this._map(ay,asr.y,asr.y+asr.h,0,255)));
			g.fillRect(asr.x,(int)ay,asr.w,(int)step+(int)o);
			o+=step;
			o=o%1;
			ay+=(int)step+(int)o;
		}
		s=0;
		gs=0;
		for (double y=pr.y;y<=pr.y+pr.h;y+=COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.height){
			s=gs+0;
			gs=(gs+1)%2;
			for (double x=pr.x;x<=pr.x+pr.w/2;x+=COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.width){
				if (s==0){
					g.setColor(COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_A);
				}
				else{
					g.setColor(COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_B);
				}
				g.fillRect((int)x,(int)y,(int)COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.width,(int)COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.height);
				s=(s+1)%2;
			}
		}
		g.setColor(COLOR_PICKER_GUI_SOLID_COLOR);
		g.fillRect(pr.x+pr.w/2,pr.y,pr.w/2,pr.h);
		g.setColor(this.C);
		g.fillRect(pr.x,pr.y,pr.w,pr.h);
		g.setColor(GUI_FRAME_OUTLINE_COLOR);
		g.setStroke(new BasicStroke(COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS));
		g.drawRect(cr.x-COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS/2,cr.y-COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS/2,cr.w+COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS-1,cr.h+COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS-1);
		g.drawRect(sr.x-COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS/2,sr.y-COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS/2,sr.w+COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS-1,sr.h+COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS-1);
		g.drawRect(asr.x-COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS/2,asr.y-COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS/2,asr.w+COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS-1,asr.h+COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS-1);
		g.drawRect(pr.x-COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS/2,pr.y-COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS/2,pr.w+COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS-1,pr.h+COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS-1);
		g.setStroke(new BasicStroke(GUI_FRAME_OUTLINE_THICKNESS));
		g.drawRect(r.x-GUI_FRAME_OUTLINE_THICKNESS/2,r.y-GUI_FRAME_OUTLINE_THICKNESS/2,r.w+GUI_FRAME_OUTLINE_THICKNESS-1,r.h+GUI_FRAME_OUTLINE_THICKNESS-1);
		double xp=this._map(COL.getGreen()/255f,0,1,cr.x,cr.x+cr.w);
		double yp=this._map(COL.getBlue()/255f,1,0,cr.y,cr.y+cr.h);
		g.setColor(this._get_with_alpha(255));
		g.fillRect((int)xp-COLOR_PICKER_GUI_RECT_SELECT_HANDLE_SIZE.width/2,(int)yp-COLOR_PICKER_GUI_RECT_SELECT_HANDLE_SIZE.height/2,COLOR_PICKER_GUI_RECT_SELECT_HANDLE_SIZE.width,COLOR_PICKER_GUI_RECT_SELECT_HANDLE_SIZE.height);
		g.setColor(COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE);
		g.setStroke(new BasicStroke(COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS));
		g.drawRect((int)xp-COLOR_PICKER_GUI_RECT_SELECT_HANDLE_SIZE.width/2-COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS/2,(int)yp-COLOR_PICKER_GUI_RECT_SELECT_HANDLE_SIZE.height/2-COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS/2,COLOR_PICKER_GUI_RECT_SELECT_HANDLE_SIZE.width+COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS-1,COLOR_PICKER_GUI_RECT_SELECT_HANDLE_SIZE.height+COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS-1);
		double hsx=sr.x+sr.w/2;
		double hsy=this._map(COL.getRed()/255f,0,1,sr.y,sr.y+sr.h);
		g.setColor(this._hsv_to_rgb(COL.getRed()/255f,1,1,1));
		g.fillRect((int)hsx-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width/2,(int)hsy-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height/2,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height);
		g.setColor(COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE);
		g.drawRect((int)hsx-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width/2-COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS/2,(int)hsy-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height/2-COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS/2,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width+COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS-1,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height+COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS-1);
		double asx=asr.x+asr.w/2;
		double asy=this._map(COL.getAlpha()/255f,0,1,asr.y,asr.y+asr.h);
		Rectangle asrr=new Rectangle((int)asx-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width/2,(int)asy-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height/2,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height);
		s=0;
		gs=0;
		for (double y=asrr.y;y<=asrr.y+asrr.h;y+=COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.height){
			s=gs+0;
			gs=(gs+1)%2;
			for (double x=asrr.x;x<=asrr.x+asrr.w;x+=COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.width){
				if (s==0){
					g.setColor(COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_A);
				}
				else{
					g.setColor(COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_B);
				}
				g.fillRect((int)x,(int)y,(int)COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.width,(int)COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.height);
				s=(s+1)%2;
			}
		}
		g.setColor(this.C);
		g.fillRect((int)asx-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width/2,(int)asy-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height/2,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height);
		g.setColor(COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE);
		g.drawRect((int)asx-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width/2-COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS/2,(int)asy-COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height/2-COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS/2,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.width+COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS-1,COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE.height+COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS-1);
	}



	@Override
	public void update(){
		if (this.cls.KEYBOARD.pressed(27)){
			this.close_gui();
			return;
		}
		if (this.cls.KEYBOARD.pressed(10)){
			this.close();
			return;
		}
		if (this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1){
			Color COL=this._rgb_to_hsv(this.C);
			int h=(int)(COL.getRed()/255f*360);
			int s=(int)(COL.getGreen()/255f*100);
			int v=(int)(COL.getBlue()/255f*100);
			int a=COL.getAlpha();
			if (this.drag==0){
				Rectangle cr=this._get_color_rect();
				if (this.cls.MOUSE_POS.x>=cr.x&&this.cls.MOUSE_POS.x<=cr.x+cr.w&&this.cls.MOUSE_POS.y>=cr.y&&this.cls.MOUSE_POS.y<=cr.y+cr.h){
					this.drag=1;
				}
				Rectangle sr=this._get_slider_rect();
				if (this.cls.MOUSE_POS.x>=sr.x&&this.cls.MOUSE_POS.x<=sr.x+sr.w&&this.cls.MOUSE_POS.y>=sr.y&&this.cls.MOUSE_POS.y<=sr.y+sr.h){
					this.drag=2;
				}
				Rectangle asr=this._get_alpha_slider_rect();
				if (this.cls.MOUSE_POS.x>=asr.x&&this.cls.MOUSE_POS.x<=asr.x+asr.w&&this.cls.MOUSE_POS.y>=asr.y&&this.cls.MOUSE_POS.y<=asr.y+asr.h){
					this.drag=3;
				}
			}
			if (this.drag==1){
				Rectangle cr=this._get_color_rect();
				s=(int)Math.min(Math.max(this._map(this.cls.MOUSE_POS.x,cr.x,cr.x+cr.w,0,99),0),99);
				v=(int)Math.min(Math.max(this._map(this.cls.MOUSE_POS.y,cr.y,cr.y+cr.h,99,0),0),99);
			}
			if (this.drag==2){
				Rectangle sr=this._get_slider_rect();
				h=(int)Math.min(Math.max(this._map(this.cls.MOUSE_POS.y,sr.y,sr.y+sr.h,0,359),0),359);
			}
			if (this.drag==3){
				Rectangle asr=this._get_alpha_slider_rect();
				a=(int)Math.min(Math.max(this._map(this.cls.MOUSE_POS.y,asr.y,asr.y+asr.h,0,255),0),255);
			}
			this.C=this._hsv_to_rgb(h/360f,s/100f,v/100f,a/255f);
		}
		else{
			this.drag=0;
			if (this.cls.KEYBOARD.pressed(67)){
				this._copy_color();
			}
			if (this.cls.KEYBOARD.pressed(86)){
				this._paste_color();
			}
		}
	}



	private Rectangle _get_frame_rect(){
		return new Rectangle(WINDOW_SIZE.width/2-COLOR_PICKER_GUI_SIZE.width/2,WINDOW_SIZE.height/2-COLOR_PICKER_GUI_SIZE.height/2,COLOR_PICKER_GUI_SIZE.width,COLOR_PICKER_GUI_SIZE.height);
	}



	private Rectangle _get_color_rect(){
		Rectangle r=this._get_frame_rect();
		return new Rectangle(r.x+COLOR_PICKER_GUI_COLOR_RECT_FRAME.x,r.y+COLOR_PICKER_GUI_COLOR_RECT_FRAME.y,COLOR_PICKER_GUI_COLOR_RECT_FRAME.width,COLOR_PICKER_GUI_COLOR_RECT_FRAME.height);
	}



	private Rectangle _get_slider_rect(){
		Rectangle r=this._get_frame_rect();
		return new Rectangle(r.x+COLOR_PICKER_GUI_HUE_SLIDER_FRAME.x,r.y+COLOR_PICKER_GUI_HUE_SLIDER_FRAME.y,COLOR_PICKER_GUI_HUE_SLIDER_FRAME.width,COLOR_PICKER_GUI_HUE_SLIDER_FRAME.height);
	}



	private Rectangle _get_alpha_slider_rect(){
		Rectangle r=this._get_frame_rect();
		return new Rectangle(r.x+COLOR_PICKER_GUI_ALPHA_SLIDER_FRAME.x,r.y+COLOR_PICKER_GUI_ALPHA_SLIDER_FRAME.y,COLOR_PICKER_GUI_ALPHA_SLIDER_FRAME.width,COLOR_PICKER_GUI_ALPHA_SLIDER_FRAME.height);
	}



	private Rectangle _get_preview_rect(){
		Rectangle r=this._get_frame_rect();
		return new Rectangle(r.x+COLOR_PICKER_GUI_PREVIEW_FRAME.x,r.y+COLOR_PICKER_GUI_PREVIEW_FRAME.y,COLOR_PICKER_GUI_PREVIEW_FRAME.width,COLOR_PICKER_GUI_PREVIEW_FRAME.height);
	}



	private double _map(double v,double aa,double ab,double ba,double bb){
		return (v-aa)/(ab-aa)*(bb-ba)+ba;
	}



	private String _get_col_name(){
		return String.format("%d,%d,%d,%d",this.C.getRed(),this.C.getGreen(),this.C.getBlue(),this.C.getAlpha());
	}



	private Color _get_with_alpha(int a){
		return new Color(this.C.getRed(),this.C.getGreen(),this.C.getBlue(),a);
	}



	private void _set(int r,int g,int b,int a){
		this.C=new Color(r,g,b,a);
	}



	private Color _rgb_to_hsv(Color c){
		float r=(c.getRed()/255f);
		float g=(c.getGreen()/255f);
		float b=(c.getBlue()/255f);
		float min=Math.min(r,Math.min(g,b));
		float max=Math.max(r,Math.max(g,b));
		float d=max-min;
		float h=-1;
		float s=d/max;
		float v=max+0;
		if (d==0){
			h=0;
			s=0;
		}
		else{
			float d_r=(((max-r)/6f)+(d/2f))/d;
			float d_g=(((max-g)/6f)+(d/2f))/d;
			float d_b=(((max-b)/6f)+(d/2f))/d;
			if (r==max){
				h=d_b-d_g;
			}
			else if (g==max){
				h=(1/3f)+d_r-d_b;
			}
			else if (b==max){
				h=(2/3f)+d_g-d_r;
			}
			if (h<0){
				h++;
			}
			if (h>1){
				h--;
			}
		}
		return new Color(Math.round(h*360)/360f,Math.round(s*100)/100f,Math.round(v*100)/100f,c.getAlpha()/255f);
	}



	private Color _hsv_to_rgb(float _h,float s,float v,float a){
		int h=(int)(_h*6);
		float f=_h*6-h;
		float p=v*(1-s);
		float q=v*(1-f*s);
		float t=v*(1-(1-f)*s);
		switch (h){
			case 0:
				return new Color(v,t,p,a);
			case 1:
				return new Color(q,v,p,a);
			case 2:
				return new Color(p,v,t,a);
			case 3:
				return new Color(p,q,v,a);
			case 4:
				return new Color(t,p,v,a);
			case 5:
				return new Color(v,p,q,a);
		}
		return null;
	}



	private void _copy_color(){
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this._get_col_name()),null);
	}



	private void _paste_color(){
		try{
			String cs=(String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			String[] s=cs.split(",");
			try{
				if (s.length==3){
					this.C=new Color(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2]),255);
				}
				else{
					this.C=new Color(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2]),Integer.parseInt(s[3]));
				}
			}
			catch (Exception e){
				System.out.println("Unable to Paste: "+cs);
			}
		}
		catch (Exception e){
		}
	}
}
