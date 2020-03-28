package com.krzem.image_editor;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;



public class Toolbar extends Constants{
	public Main cls;
	public ImageEditorWindow i;
	public Color bgc;
	public Color fgc;
	public ArrayList<Tool> tools;
	public ArrayList<ToolGroup> tool_g;



	public Toolbar(Main cls,ImageEditorWindow i){
		this.cls=cls;
		this.i=i;
		this.bgc=new Color(TOOLBAR_DEFAULT_BG_COLOR.getRed(),TOOLBAR_DEFAULT_BG_COLOR.getGreen(),TOOLBAR_DEFAULT_BG_COLOR.getBlue(),TOOLBAR_DEFAULT_BG_COLOR.getAlpha());
		this.fgc=new Color(TOOLBAR_DEFAULT_FG_COLOR.getRed(),TOOLBAR_DEFAULT_FG_COLOR.getGreen(),TOOLBAR_DEFAULT_FG_COLOR.getBlue(),TOOLBAR_DEFAULT_FG_COLOR.getAlpha());
		this.tools=new ToolClassLoader().load_tools(this,TOOL_DIR);
		this.tool_g=new ToolClassLoader().load_tool_groups(this,TOOL_DIR);
	}



	public void draw(Graphics2D g){
		Rectangle r=this._get_full_rect();
		g.setColor(TOOLBAR_BG_COLOR);
		g.fillRect(r.x,r.y,r.w,r.h);
		g.setColor(TOOLBAR_OUTLINE_COLOR);
		g.setStroke(new BasicStroke(TOOLBAR_OUTLINE_THICKNESS));
		g.drawLine(TOOLBAR_WIDTH+TOOLBAR_OUTLINE_THICKNESS/2-1,0,TOOLBAR_WIDTH+TOOLBAR_OUTLINE_THICKNESS/2-1,WINDOW_SIZE.height);
		int i=0;
		for (ToolGroup t:this.tool_g){
			Rectangle er=this._get_rect(i);
			g.setColor(TOOLBAR_ELEM_BG_COLOR);
			g.fillRect(er.x,er.y,er.w,er.h);
			t.draw(g,this._get_default_rect(i));
			i++;
		}
	}



	public void update(){
		boolean c=false;
		for (int i=0;i<this.tool_g.size();i++){
			Rectangle r=this._get_rect(i);
			if (this.cls.MOUSE_POS.x>=r.x&&this.cls.MOUSE_POS.x<=r.x+r.w&&this.cls.MOUSE_POS.y>=r.y&&this.cls.MOUSE_POS.y<=r.y+r.h){
				this.tool_g.get(i).s=Math.min(this.tool_g.get(i).s+TOOLBAR_ELEM_SIZE_INC,TOOLBAR_HOVER_ELEM_SIZE);
				if (this.cls.MOUSE==3&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1){
					this.tool_g.get(i)._click();
					c=true;
				}
			}
			else{
				if (this.tool_g.get(i).active==false){
					this.tool_g.get(i).s=Math.max(this.tool_g.get(i).s-TOOLBAR_ELEM_SIZE_DINC,TOOLBAR_ELEM_SIZE);
				}
				else{
					this.tool_g.get(i).s=Math.min(this.tool_g.get(i).s+TOOLBAR_ELEM_SIZE_INC,TOOLBAR_HOVER_ELEM_SIZE);
				}
			}
		}
		Rectangle r=this._get_full_rect();
		if (c==false&&this.cls.MOUSE==3&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1&&this.cls.MOUSE_POS.x>=r.x&&this.cls.MOUSE_POS.x<=r.x+r.w&&this.cls.MOUSE_POS.y>=r.y&&this.cls.MOUSE_POS.y<=r.y+r.h){
			for (ToolGroup t:this.tool_g){
				t._cancel();
			}
		}
		try{
			if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+TOOLBAR_KEY_MOD.toUpperCase()).getInt(null))){
				String sl=TOOLBAR_KEY_CHARS.toUpperCase();
				if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+TOOLBAR_KEY_RESET).getInt(null))){
					for (ToolGroup t:this.tool_g){
						t._cancel();
					}
					sl="";
				}
				for (int i=0;i<sl.length();i++){
					if (i>=this.tool_g.size()){
						break;
					}
					int n=KeyEvent.class.getField("VK_"+Character.toString(sl.charAt(i))).getInt(null);
					if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+Character.toString(sl.charAt(i))).getInt(null))){
						this.tool_g.get(i)._click();
						break;
					}
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		for (ToolGroup tg:this.tool_g){
			tg.update();
		}
	}



	public void set_bg(Color c){
		this.bgc=c;
	}



	public void set_fg(Color c){
		this.fgc=c;
	}



	private Rectangle _get_rect(int i){
		double w=this.tool_g.get(i).s;
		return new Rectangle((int)(TOOLBAR_WIDTH/2-w/2),(int)(TOOLBAR_WIDTH*i+TOOLBAR_WIDTH/2-w/2),(int)w,(int)w);
	}



	private Rectangle _get_default_rect(int i){
		return new Rectangle((int)(TOOLBAR_WIDTH/2-TOOLBAR_ELEM_SIZE/2),(int)(TOOLBAR_WIDTH*i+TOOLBAR_WIDTH/2-TOOLBAR_ELEM_SIZE/2),(int)TOOLBAR_ELEM_SIZE,(int)TOOLBAR_ELEM_SIZE);
	}



	private Rectangle _get_full_rect(){
		return new Rectangle(0,0,TOOLBAR_WIDTH,WINDOW_SIZE.height);
	}
}