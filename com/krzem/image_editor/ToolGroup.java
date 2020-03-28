package com.krzem.image_editor;



import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.lang.Math;
import java.lang.reflect.Method;
import java.util.ArrayList;



public class ToolGroup extends Constants{
	public Main cls;
	public Toolbar tb;
	public Object t_obj;
	public ArrayList<Tool> tl;
	public boolean active=false;
	public double s=TOOLBAR_ELEM_SIZE;
	public Rectangle RECT;



	public ToolGroup(Main cls,Toolbar tb,Object t_obj){
		this.cls=cls;
		this.tb=tb;
		this.t_obj=t_obj;
		this.tl=new ArrayList<Tool>();
		this._load();
	}



	public void draw(Graphics2D g,Rectangle r){
		this.RECT=r;
		try{
			this._run_method("draw").invoke(this.t_obj,g,r);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		if (this.active==true){
			Rectangle wr=this._get_full_rect(this.RECT);
			Rectangle or=new Rectangle(wr.x-TOOLGROUP_WINDOW_OUTLINE_THICKNESS/2,wr.y-TOOLGROUP_WINDOW_OUTLINE_THICKNESS/2,wr.w+TOOLGROUP_WINDOW_OUTLINE_THICKNESS-1,wr.h+TOOLGROUP_WINDOW_OUTLINE_THICKNESS-1);
			g.setColor(TOOLGROUP_WINDOW_BG_COLOR);
			g.fillRect(wr.x,wr.y,wr.w,wr.h);
			g.setColor(TOOLGROUP_WINDOW_OUTLINE_COLOR);
			g.setStroke(new BasicStroke(TOOLGROUP_WINDOW_OUTLINE_THICKNESS));
			g.drawRect(or.x,or.y,or.w,or.h);
			int i=0;
			for (Tool t:this.tl){
				Rectangle tr=this._get_rect(wr,i);
				g.setColor(TOOLBAR_ELEM_BG_COLOR);
				g.fillRect(tr.x,tr.y,tr.w,tr.h);
				t.draw(g,this._get_default_rect(wr,i));
				i++;
			}
		}
	}



	public void update(){
		if (this.active==true){
			boolean c=false;
			for (int i=0;i<this.tl.size();i++){
				Rectangle r=this._get_rect(this._get_full_rect(this.RECT),i);
				if (this.cls.MOUSE_POS.x>=r.x&&this.cls.MOUSE_POS.x<=r.x+r.w&&this.cls.MOUSE_POS.y>=r.y&&this.cls.MOUSE_POS.y<=r.y+r.h){
					this.tl.get(i).s=Math.min(this.tl.get(i).s+TOOLBAR_ELEM_SIZE_INC,TOOLBAR_HOVER_ELEM_SIZE);
					if (this.cls.MOUSE==3&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1){
						this.tl.get(i)._click();
						c=true;
					}
				}
				else{
					if (this.tl.get(i).active==false){
						this.tl.get(i).s=Math.max(this.tl.get(i).s-TOOLBAR_ELEM_SIZE_DINC,TOOLBAR_ELEM_SIZE);
					}
					else{
						this.tl.get(i).s=Math.min(this.tl.get(i).s+TOOLBAR_ELEM_SIZE_INC,TOOLBAR_HOVER_ELEM_SIZE);
					}
				}
			}
			try{
				if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+TOOLBAR_KEY_MOD.toUpperCase()).getInt(null))&&this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+TOOLGROUP_KEY_MOD.toUpperCase()).getInt(null))){
					String sl=TOOLBAR_KEY_CHARS.toUpperCase();
					if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+TOOLBAR_KEY_RESET).getInt(null))){
						for (Tool t:this.tl){
							t._cancel();
						}
						sl="";
					}
					for (int i=0;i<sl.length();i++){
						if (i>=this.tl.size()){
							break;
						}
						int n=KeyEvent.class.getField("VK_"+Character.toString(sl.charAt(i))).getInt(null);
						if (this.cls.KEYBOARD.pressed(KeyEvent.class.getField("VK_"+Character.toString(sl.charAt(i))).getInt(null))){
							this.tl.get(i)._click();
							break;
						}
					}
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		for (Tool t:this.tl){
			if (t.active==true){
				t.update();
			}
		}
	}



	public void _click(){
		for (ToolGroup t:this.tb.tool_g){
			t._cancel();
		}
		this.active=true;
	}



	public void _cancel(){
		this.active=false;
		for (Tool t:this.tl){
			t._cancel();
		}
	}



	private Method _run_method(String nm){
		for (Method m:this.t_obj.getClass().getDeclaredMethods()){
			if (m.getName().equals(nm)){
				return m;
			}
		}
		return null;
	}



	private void _load(){
		try{
			String s=this._run_method("TOOL_NAMES").invoke(this.t_obj).toString();
			for (String tn:s.toString().split(",")){
				for (Tool t:this.tb.tools){
					if (t.t_obj.getClass().getName().substring(0,t.t_obj.getClass().getName().lastIndexOf("Tool")).toLowerCase().equals(tn.toLowerCase())){
						this.tl.add(t);
						break;
					}
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}



	private Rectangle _get_rect(Rectangle r,int i){
		double w=this.tl.get(i).s;
		return new Rectangle((int)(r.x+TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE/2-w/2),(int)(r.y+TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE*i+TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE/2-w/2),(int)w,(int)w);
	}



	private Rectangle _get_default_rect(Rectangle r,int i){
		return new Rectangle(r.x+TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE/2-TOOLGROUP_WINDOW_ELEM_SIZE/2,r.y+TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE*i+TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE/2-TOOLGROUP_WINDOW_ELEM_SIZE/2,TOOLGROUP_WINDOW_ELEM_SIZE,TOOLGROUP_WINDOW_ELEM_SIZE);
	}



	private Rectangle _get_full_rect(Rectangle r){
		return new Rectangle(TOOLBAR_WIDTH+TOOLBAR_OUTLINE_THICKNESS,r.y-1-Math.max(0,(r.y+this.tl.size()*TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE-WINDOW_SIZE.height)),TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE,this.tl.size()*TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE);
	}
}