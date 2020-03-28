package com.krzem.image_editor;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.Math;
import java.util.ArrayList;
import javax.swing.JFrame;



public class Main extends Constants{
	public static void main(String[] args){
		new Main(args[0]);
	}



	public float FPS=1;
	public int MOUSE=0;
	public int MOUSE_COUNT=0;
	public int MOUSE_BUTTON=0;
	public Vector MOUSE_POS=new Vector(0,0);
	public Keyboard KEYBOARD;
	public int SCROLL_D=0;
	public GUI GUI=null;
	public JFrame frame;
	public Canvas canvas;
	private int _mouse;
	private int _mouseC;
	private int _mouseB;
	private MouseEvent _mouseM;
	private int _sc;
	public ImageEditorWindow w;



	public Main(String fn){
		this.frame_init();
		this.init(fn);
		this.run();
	}



	public void init(String fn){
		this.w=new ImageEditorWindow(this,fn);
		this.KEYBOARD=new Keyboard(this);
	}
	public void frame_init(){
		this.frame=new JFrame("Image Editor");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setUndecorated(true);
		this.frame.setResizable(false);
		DEVICE.setFullScreenWindow(this.frame);
		this.canvas=new Canvas(this);
		this.canvas.setSize(WINDOW_SIZE.width,WINDOW_SIZE.height);
		this.canvas.setPreferredSize(new Dimension(WINDOW_SIZE.width,WINDOW_SIZE.height));
		Main cls=this;
		this.canvas.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				cls._mouse=1;
				cls._mouseC=e.getClickCount();
				cls._mouseB=e.getButton();
			}
			public void mouseReleased(MouseEvent e){
				cls._mouse=2;
				cls._mouseC=e.getClickCount();
				cls._mouseB=e.getButton();
			}
			public void mouseClicked(MouseEvent e){
				cls._mouse=3;
				cls._mouseC=e.getClickCount();
				cls._mouseB=e.getButton();
			}
		});
		this.canvas.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e){
				cls._mouseM=e;
			}
			public void mouseDragged(MouseEvent e){
				cls._mouseM=e;
			}
		});
		this.canvas.addMouseWheelListener(new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent e){
				if (e.getWheelRotation()<0){
					cls._sc=1;
				}
				else{
					cls._sc=-1;
				}
			}
		});
		this.canvas.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if (cls.KEYBOARD==null){
					return;
				}
				cls.KEYBOARD.down(e);
			}
			public void keyReleased(KeyEvent e){
				if (cls.KEYBOARD==null){
					return;
				}
				cls.KEYBOARD.up(e);
			}
			public void keyTyped(KeyEvent e){
				if (cls.KEYBOARD==null){
					return;
				}
				cls.KEYBOARD.press(e);
			}
		});
		this.frame.setContentPane(this.canvas);
		this.canvas.requestFocus();
	}



	public void run(){
		Main cls=this;
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (true){
					Long s=System.currentTimeMillis();
					cls.update_events();
					cls.update();
					cls.canvas.repaint();
					Long d=System.currentTimeMillis()-s;
					if (d==0){
						d=1L;
					}
					float FPS=(float)Math.floor(1/(float)d*100000)/100;
					if (FPS>cls.MAX_FPS){
						try{
							Thread.sleep((long)(1/(float)cls.MAX_FPS*1000)-d);
						}
						catch (InterruptedException e){}
					}
					cls.FPS=(float)Math.floor(1/(float)(System.currentTimeMillis()-s)*100000)/100;
				}
			}
		}).start();
	}



	public void update_events(){
		this.MOUSE=this._mouse+0;
		this.MOUSE_COUNT=this._mouseC+0;
		this.MOUSE_BUTTON=this._mouseB+0;
		if (this._mouse!=1){
			this._mouse=0;
			this._mouseC=0;
			this._mouseB=0;
		}
		if (this._mouseM!=null){
			this.MOUSE_POS=new Vector(this._mouseM.getPoint().x,this._mouseM.getPoint().y);
			this._mouseM=null;
		}
		this.SCROLL_D=this._sc+0;
		this._sc=0;
	}



	public void update(){
		if (this.GUI!=null){
			this.GUI.update();
		}
		else if (this.w!=null){
			this.w.update();
		}
		this.KEYBOARD.update();
	}



	public void draw(Graphics2D g){
		if (this.w==null){
			return;
		}
		g.setColor(BG_COLOR);
		g.fillRect(0,0,this.WINDOW_SIZE.width,this.WINDOW_SIZE.height);
		this.w.draw(g);
		if (this.GUI!=null){
			this.GUI.draw(g);
		}
	}
}