package com.krzem.image_editor;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import javax.imageio.ImageIO;



public class ImageEditorWindow extends Constants{
	public Main cls;
	public static double MIN_ZOOM=1;
	public static double MAX_ZOOM;
	public static Rectangle DEFAULT_RECT;
	public String fn;
	public BufferedImage img;
	public double zoom=1;
	public Vector pan;
	public ColorPalette cp;
	public Toolbar tb;



	public ImageEditorWindow(Main cls,String fn){
		this.cls=cls;
		this.fn=fn;
		this.pan=new Vector(0,0);
		this.img=this._load();
		this.MAX_ZOOM=this._max_zoom();
		this.DEFAULT_RECT=this._get_screen_rect();
		this.cp=new ColorPalette(this.cls,this);
		this.tb=new Toolbar(this.cls,this);
	}



	public void save(){
		if (this.img==null){
			return;
		}
		try{
			ImageIO.write(this.img,this.fn.split(".")[1],new File(this.fn));
		}
		catch (IOException e){
		}
	}



	public void update(){
		this.zoom=Math.min(Math.max(this.zoom+IMAGE_ZOOM_INCREMENT*this.cls.SCROLL_D,MIN_ZOOM),MAX_ZOOM);
		Rectangle r=this._get_screen_rect();
		if (this.cls.KEYBOARD.pressed(37)){
			this.pan.x-=IMAGE_PAN_INCREMENT;
		}
		if (this.cls.KEYBOARD.pressed(38)){
			this.pan.y-=IMAGE_PAN_INCREMENT;
		}
		if (this.cls.KEYBOARD.pressed(39)){
			this.pan.x+=IMAGE_PAN_INCREMENT;
		}
		if (this.cls.KEYBOARD.pressed(40)){
			this.pan.y+=IMAGE_PAN_INCREMENT;
		}
		if (r.w>=WINDOW_SIZE.width*IMAGE_PAN_PROC_WIDTH){
			this.pan.x=Math.min(Math.max(this.pan.x,-(r.w/2-(int)(WINDOW_SIZE.width*IMAGE_PAN_PROC_WIDTH)/2)),r.w/2-(int)(WINDOW_SIZE.width*IMAGE_PAN_PROC_WIDTH)/2);
		}
		else{
			this.pan.x=0;
		}
		if (r.h>=WINDOW_SIZE.height*IMAGE_PAN_PROC_HEIGHT){
			this.pan.y=Math.min(Math.max(this.pan.y,-(r.h/2-(int)(WINDOW_SIZE.height*IMAGE_PAN_PROC_HEIGHT)/2)),r.h/2-(int)(WINDOW_SIZE.height*IMAGE_PAN_PROC_HEIGHT)/2);
		}
		else{
			this.pan.y=0;
		}
		this.cp.update();
		this.tb.update();
	}



	public void draw(Graphics2D g){
		Rectangle r=this._get_screen_rect();
		double pxs=this._get_pixel_size();
		double sx=r.x+0;
		double sy=r.y+0;
		double ew=0;
		double eh=0;
		for (double y=0;y<this.img.getHeight();y++){
			eh+=pxs%1;
			for (double x=0;x<this.img.getWidth();x++){
				int c=this.img.getRGB((int)x,(int)y);
				ew+=pxs%1;
				g.setColor(new Color((c&0x00ff0000)>>16,(c&0x0000ff00)>>8,c&0x000000ff));
				g.fillRect((int)(sx),(int)(sy),(int)pxs+(int)ew,(int)pxs+(int)eh);
				sx+=(int)pxs+(int)ew;
				if (ew>=1){
					ew=ew%1;
				}
			}
			sx=r.x+0;
			sy+=(int)pxs+(int)eh;
			if (eh>=1){
				eh=eh%1;
			}
		}
		if (this._get_pixel_size_proc()>=IMAGE_MIN_GRID_SIZE_PROC){
			g.setColor(IMAGE_GRID_COLOR);
			for (double x=r.x+pxs;x<r.x+r.w;x+=pxs){
				g.drawLine((int)x,r.y,(int)x,r.y+r.h-1);
			}
			for (double y=r.y+pxs;y<r.y+r.h;y+=pxs){
				g.drawLine(r.x,(int)y,r.x+r.w-1,(int)y);
			}
		}
		this.cp.draw(g);
		this.tb.draw(g);
	}



	public Vector _get_screen_pixel(int x,int y){
		Rectangle r=this._get_screen_rect();
		if (x<r.x-1||x>r.x+r.w||y<r.y-1||y>r.y+r.h||x<TOOLBAR_WIDTH+TOOLBAR_OUTLINE_THICKNESS||x>=WINDOW_SIZE.width-COLOR_PALETTE_WIDTH-COLOR_PALETTE_OUTLINE_THICKNESS){
			return new Vector(-1,-1);
		}
		return new Vector((int)Math.min(Math.max(this._map(x,r.x,r.x+r.w,0,this.img.getWidth()),-2),this.img.getWidth()+1),(int)Math.min(Math.max(this._map(y,r.y,r.y+r.h,0,this.img.getHeight()),-2),this.img.getHeight()+1));
	}



	public Vector _get_screen_pixel_nocheck(int x,int y){
		Rectangle r=this._get_screen_rect();

		return new Vector((int)Math.min(Math.max(this._map(x,r.x,r.x+r.w,0,this.img.getWidth()),-2),this.img.getWidth()+1),(int)Math.min(Math.max(this._map(y,r.y,r.y+r.h,0,this.img.getHeight()),-2),this.img.getHeight()+1));
	}



	public Rectangle _get_screen_rect(){
		int x;
		int y;
		int w;
		int h;
		int ow=this.img.getWidth();
		int oh=this.img.getHeight();
		double sc;
		if (ow>oh){
			sc=(WINDOW_SIZE.width*IMAGE_DEFAULT_SCREEN_SIZE_PROC)/(double)(ow);
		}
		else{
			sc=(WINDOW_SIZE.height*IMAGE_DEFAULT_SCREEN_SIZE_PROC)/(double)(oh);
		}
		w=(int)((double)(ow)*sc*this.zoom);
		h=(int)((double)(oh)*sc*this.zoom);
		x=WINDOW_SIZE.width/2-w/2-this.pan.x;
		y=WINDOW_SIZE.height/2-h/2-this.pan.y;
		return new Rectangle(x,y,w,h);
	}



	private BufferedImage _load(){
		try{
			return ImageIO.read(new File(this.fn));
		}
		catch (IOException e){
		}
		return null;
	}



	private double _map(double v,double aa,double ab,double ba,double bb){
		return (v-aa)/(ab-aa)*(bb-ba)+ba;
	}



	private double _max_zoom(){
		this.zoom=1;
		while (true){
			if (this._get_pixel_size_proc()>=IMAGE_MAX_GRID_SIZE_PROC){
				double r=this.zoom+0;
				this.zoom=this.MIN_ZOOM+0;
				return r;
			}
			this.zoom+=IMAGE_ZOOM_INCREMENT;
		}
	}



	private double _get_pixel_size(){
		return (double)(this._get_screen_rect().w)/this.img.getWidth();
	}



	private double _get_pixel_size_proc(){
		return this._get_pixel_size()/(double)(WINDOW_SIZE.height);
	}
}