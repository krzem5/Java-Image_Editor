package com.krzem.image_editor;



import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import javax.swing.JComponent;



public class Canvas extends JComponent{
	Main _p_cls;



	public Canvas(Main p){
		this._p_cls=p;
	}



	@Override
	public void paintComponent(Graphics _g){
		Graphics2D g=(Graphics2D) _g.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		this._p_cls.draw(g);
		g.dispose();
	}



	@Override
	public void addNotify(){
		super.addNotify();
		this.requestFocus();
	}
}
