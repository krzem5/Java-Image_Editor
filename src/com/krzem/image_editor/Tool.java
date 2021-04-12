package com.krzem.image_editor;



import java.awt.Graphics2D;
import java.lang.reflect.Method;



public class Tool extends Constants{
	public Main cls;
	public Toolbar tb;
	public Object t_obj;
	public boolean active=false;
	public double s=TOOLBAR_ELEM_SIZE;



	public Tool(Main cls,Toolbar tb,Object t_obj){
		this.cls=cls;
		this.tb=tb;
		this.t_obj=t_obj;
	}



	public void draw(Graphics2D g,Rectangle r){
		try{
			this._run_method("draw").invoke(this.t_obj,g,r);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}



	public void update(){
		try{
			this._run_method("update").invoke(this.t_obj);
		}
		catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}



	public void _click(){
		for (Tool t:this.tb.tools){
			t._cancel();
		}
		this.active=true;
		try{
			this._run_method("click").invoke(this.t_obj);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}



	public void _cancel(){
		this.active=false;
		try{
			this._run_method("cancel").invoke(this.t_obj);
		}
		catch (Exception e){
			e.printStackTrace();
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
}