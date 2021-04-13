package com.krzem.image_editor;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.ArrayList;



public class ColorPalette extends Constants{
	public Main cls;
	public ImageEditorWindow i;
	public ArrayList<Color> cl;
	public ArrayList<Double> hsl;



	public ColorPalette(Main cls,ImageEditorWindow i){
		this.cls=cls;
		this.i=i;
		this.cl=new ArrayList<Color>();
		this.hsl=new ArrayList<Double>();
		this._load(this.i.img);
	}



	public void update(){
		for (int i=0;i<this.hsl.size();i++){
			Rectangle r=this._get_rect(i);
			if (this.cls.MOUSE_POS.x>=r.x&&this.cls.MOUSE_POS.x<=r.x+r.w&&this.cls.MOUSE_POS.y>=r.y&&this.cls.MOUSE_POS.y<=r.y+r.h){
				this.hsl.set(i,Math.min(this.hsl.get(i)+COLOR_PALETTE_ELEM_SIZE_INC,COLOR_PALETTE_HOVER_ELEM_SIZE));
				if (this.cls.MOUSE==3&&this.cls.MOUSE_COUNT==1&&(this.cls.MOUSE_BUTTON==1||this.cls.MOUSE_BUTTON==3)){
					if (this.cls.MOUSE_BUTTON==1){
						this.i.tb.set_bg(this.cl.get(i));
					}
					else{
						this.i.tb.set_fg(this.cl.get(i));
					}
				}
			}
			else{
				this.hsl.set(i,Math.max(this.hsl.get(i)-COLOR_PALETTE_ELEM_SIZE_DINC,COLOR_PALETTE_ELEM_SIZE));
			}
		}
		try{
			if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+COLOR_PALETTE_KEY_MOD.toUpperCase()).getInt(null))){
				String sl=COLOR_PALETTE_KEY_CHARS.toUpperCase();
				if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+COLOR_PALETTE_KEY_RESET).getInt(null))){
					if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+COLOR_PALETTE_FG_KEY.toUpperCase()).getInt(null))){
						this.i.tb.set_fg(new Color(TOOLBAR_DEFAULT_FG_COLOR.getRed(),TOOLBAR_DEFAULT_FG_COLOR.getGreen(),TOOLBAR_DEFAULT_FG_COLOR.getBlue(),TOOLBAR_DEFAULT_FG_COLOR.getAlpha()));
					}
					else{
						this.i.tb.set_bg(new Color(TOOLBAR_DEFAULT_BG_COLOR.getRed(),TOOLBAR_DEFAULT_BG_COLOR.getGreen(),TOOLBAR_DEFAULT_BG_COLOR.getBlue(),TOOLBAR_DEFAULT_BG_COLOR.getAlpha()));
					}
					sl="";
				}
				for (int i=0;i<sl.length();i++){
					if (i>=this.cl.size()){
						break;
					}
					if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+Character.toString(sl.charAt(i))).getInt(null))){
						if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+COLOR_PALETTE_FG_KEY.toUpperCase()).getInt(null))){
							this.i.tb.set_fg(this.cl.get(i));
						}
						else{
							this.i.tb.set_bg(this.cl.get(i));
						}
						break;
					}
				}
			}
			Rectangle r=this._get_full_rect();
			if ((this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+COLOR_PALETTE_KEY_MOD.toUpperCase()).getInt(null))&&this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+COLOR_PALETTE_KEY_OPEN.toUpperCase()).getInt(null)))||(this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==2&&this.cls.MOUSE_BUTTON==1&&this.cls.MOUSE_POS.x>=r.x&&this.cls.MOUSE_POS.x<=r.x+r.w&&this.cls.MOUSE_POS.y>=r.y&&this.cls.MOUSE_POS.y<=r.y+r.h)){
				this.cls.GUI=new ColorPaletteGUI(this.cls,this);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}



	public void draw(Graphics2D g){
		g.setColor(COLOR_PALETTE_BG_COLOR);
		Rectangle r=this._get_full_rect();
		g.fillRect(r.x,r.y,r.w,r.h);
		int i=0;
		for (Color e:this.cl){
			Rectangle cr=this._get_rect(i);
			double s=0;
			double gs=0;
			for (double cy=cr.y;cy<cr.y+cr.h;cy+=COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.height){
				s=gs+0;
				gs=(gs+1)%2;
				for (double cx=cr.x;cx<cr.x+cr.w;cx+=COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.width){
					if (s==0){
						g.setColor(COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_A);
					}
					else{
						g.setColor(COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_B);
					}
					g.fillRect((int)cx,(int)cy,(int)(Math.max(0,Math.min(COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.width,cr.x+cr.w-cx))),(int)(Math.max(0,Math.min(cy+COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE.height,cr.y+cr.h-cy))));
					s=(s+1)%2;
				}
			}
			g.setColor(e);
			g.fillRect(cr.x,cr.y,cr.w,cr.h);
			i++;
		}
		g.setColor(COLOR_PALETTE_OUTLINE_COLOR);
		g.setStroke(new BasicStroke(COLOR_PALETTE_OUTLINE_THICKNESS));
		g.drawLine(r.x-COLOR_PALETTE_OUTLINE_THICKNESS/2,0,r.x-COLOR_PALETTE_OUTLINE_THICKNESS/2,r.h);
	}



	private void _load(BufferedImage i){
		for (int y=0;y<i.getHeight();y++){
			for (int x=0;x<i.getWidth();x++){
				int c=i.getRGB(x,y);
				int a=(c>>24)&255;
				int r=(c>>16)&255;
				int g=(c>>8)&255;
				int b=c&255;
				Color cl=new Color(r,g,b,a);
				for (Color k:this.cl){
					if (k.getRed()==r&&k.getGreen()==g&&k.getBlue()==b&&k.getAlpha()==a){
						cl=null;
						break;
					}
				}
				if (cl!=null){
					this.cl.add(cl);
					this.hsl.add((double)COLOR_PALETTE_ELEM_SIZE);
				}
			}
		}
	}



	private Rectangle _get_rect(int i){
		double w=this.hsl.get(i);
		return new Rectangle((int)(WINDOW_SIZE.width-COLOR_PALETTE_WIDTH+COLOR_PALETTE_WIDTH/2-w/2),(int)(COLOR_PALETTE_WIDTH*i+COLOR_PALETTE_WIDTH/2-w/2),(int)w,(int)w);
	}



	private Rectangle _get_full_rect(){
		return new Rectangle(WINDOW_SIZE.width-COLOR_PALETTE_WIDTH,0,COLOR_PALETTE_WIDTH,WINDOW_SIZE.height);
	}
}
