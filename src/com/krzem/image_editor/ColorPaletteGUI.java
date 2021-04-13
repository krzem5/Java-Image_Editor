package com.krzem.image_editor;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;



public class ColorPaletteGUI extends GUI{
	public Main cls;
	public ColorPalette cp;
	public Color mc=null;
	public Vector mco=null;
	public Vector mcp=null;
	public int mci=0;
	public GUI GUI=null;
	public boolean closed_gui=false;
	public boolean can_delete=false;



	public ColorPaletteGUI(Main cls,ColorPalette cp){
		this.cls=cls;
		this.cp=cp;
	}



	public void close(){
		this.cls.GUI=null;
	}



	@Override
	public void draw(Graphics2D g){
		this._draw(g);
		Rectangle r=this._get_frame_rect();
		g.setColor(GUI_FRAME_BG_COLOR);
		g.fillRect(r.x,r.y,r.w,r.h);
		int x=0;
		int y=0;
		for (Color c:this.cp.cl){
			if (this.mc==null||(c.getRed()!=this.mc.getRed()||c.getGreen()!=this.mc.getGreen()||c.getBlue()!=this.mc.getBlue()||c.getAlpha()!=this.mc.getAlpha())){
				Rectangle cr=new Rectangle(r.x+x*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE,r.y+y*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE,COLOR_PALETTE_GUI_ELEM_SIZE,COLOR_PALETTE_GUI_ELEM_SIZE);
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
				g.setColor(c);
				g.fillRect(cr.x,cr.y,cr.w,cr.h);
				x++;
				if (x*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE>=r.w){
					x=0;
					y++;
				}
			}
		}
		if (this.mc!=null){
			x=0;
			y=0;
			for (int i=0;i<this.cp.cl.size();i++){
				Rectangle er=new Rectangle(r.x+x*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)-COLOR_PALETTE_GUI_ELEM_BORDER_SIZE-COLOR_PALETTE_GUI_ELEM_SIZE/2,r.y+y*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE,COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2,COLOR_PALETTE_GUI_ELEM_SIZE);
				if (this.cls.MOUSE_POS.x>=er.x&&this.cls.MOUSE_POS.x<=er.x+er.w&&this.cls.MOUSE_POS.y>=er.y&&this.cls.MOUSE_POS.y<=er.y+er.h){
					g.setColor(COLOR_PALETTE_GUI_ELEM_INSERT_RECT_COLOR);
					g.fillRect(r.x+x*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)-COLOR_PALETTE_GUI_ELEM_INSERT_RECT_SIZE,r.y+y*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE,COLOR_PALETTE_GUI_ELEM_INSERT_RECT_SIZE*2,COLOR_PALETTE_GUI_ELEM_SIZE);
					break;
				}
				x++;
				if (x*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE>=r.w){
					x=0;
					y++;
				}
			}
		}
		g.setColor(GUI_FRAME_OUTLINE_COLOR);
		g.setStroke(new BasicStroke(GUI_FRAME_OUTLINE_THICKNESS));
		g.drawRect(r.x-GUI_FRAME_OUTLINE_THICKNESS/2,r.y-GUI_FRAME_OUTLINE_THICKNESS/2,r.w+GUI_FRAME_OUTLINE_THICKNESS-1,r.h+GUI_FRAME_OUTLINE_THICKNESS-1);
		if (this.mc!=null){
			Rectangle cr=new Rectangle(this.mcp.x-this.mco.x,this.mcp.y-this.mco.y,COLOR_PALETTE_GUI_ELEM_SIZE,COLOR_PALETTE_GUI_ELEM_SIZE);
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
			g.setColor(this.mc);
			g.fillRect(cr.x,cr.y,cr.w,cr.h);
		}
		if (this.GUI!=null){
			this.GUI.draw(g);
		}
	}



	@Override
	public void update(){
		if (this.GUI!=null){
			this.GUI.update();
		}
		else{
			if (this.closed_gui==false&&this.cls.KEYBOARD.pressed(27)){
				this.close();
				return;
			}
			if (!this.cls.KEYBOARD.pressed(27)){
				this.closed_gui=false;
			}
			Rectangle r=this._get_frame_rect();
			if (this.mc==null){
				int x=0;
				int y=0;
				int i=0;
				for (Color c:this.cp.cl){
					Rectangle er=new Rectangle(r.x+x*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE,r.y+y*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE,COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2,COLOR_PALETTE_GUI_ELEM_SIZE);
					if (this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1&&this.cls.MOUSE_POS.x>=er.x&&this.cls.MOUSE_POS.x<=er.x+er.w&&this.cls.MOUSE_POS.y>=er.y&&this.cls.MOUSE_POS.y<=er.y+er.h){
						this.mc=c;
						this.mco=new Vector(this.cls.MOUSE_POS.x-er.x,this.cls.MOUSE_POS.y-er.y);
						this.mcp=this.cls.MOUSE_POS.clone();
						this.mci=i;
						return;
					}
					else if (this.cls.MOUSE==3&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==3&&this.cls.MOUSE_POS.x>=er.x&&this.cls.MOUSE_POS.x<=er.x+er.w&&this.cls.MOUSE_POS.y>=er.y&&this.cls.MOUSE_POS.y<=er.y+er.h){
						this.GUI=new ColorPickerGUI(this.cls,this,c,i);
						return;
					}
					else if (this.can_delete==true&&this.cls.KEYBOARD.pressed(127)&&this.cls.MOUSE_POS.x>=er.x&&this.cls.MOUSE_POS.x<=er.x+er.w&&this.cls.MOUSE_POS.y>=er.y&&this.cls.MOUSE_POS.y<=er.y+er.h){
						this.cp.cl.remove(i);
						this.cp.hsl.remove(i);
						this.can_delete=false;
						return;
					}
					if (!this.cls.KEYBOARD.pressed(127)){
						this.can_delete=true;
					}
					x++;
					if (x*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE>=r.w){
						x=0;
						y++;
					}
					i++;
				}
				if (this.mc==null&&this.cls.MOUSE==0&&this.cls.KEYBOARD.pressed(78)){
					this.GUI=new ColorPickerGUI(this.cls,this);
					return;
				}
			}
			else{
				this.mcp=this.cls.MOUSE_POS.clone();
				if (this.cls.MOUSE==0){
					int x=0;
					int y=0;
					boolean f=false;
					int i=0;
					for (;i<this.cp.cl.size();i++){
						Rectangle er=new Rectangle(r.x+x*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)-COLOR_PALETTE_GUI_ELEM_BORDER_SIZE-COLOR_PALETTE_GUI_ELEM_SIZE/2,r.y+y*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE,COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2,COLOR_PALETTE_GUI_ELEM_SIZE);
						if (this.cls.MOUSE_POS.x>=er.x&&this.cls.MOUSE_POS.x<=er.x+er.w&&this.cls.MOUSE_POS.y>=er.y&&this.cls.MOUSE_POS.y<=er.y+er.h){
							f=true;
							break;
						}
						x++;
						if (x*(COLOR_PALETTE_GUI_ELEM_SIZE+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE*2)+COLOR_PALETTE_GUI_ELEM_BORDER_SIZE>=r.w){
							x=0;
							y++;
						}
					}
					if (f==true){
						this.cp.cl=this._move(this.cp.cl,this.mci,i);
					}
					this.mc=null;
					this.mco=null;
					this.mcp=null;
					this.mci=0;
				}
			}
		}
	}



	public void add(int idx,Color nc){
		if (idx==-1){
			this.cp.cl.add(nc);
			this.cp.hsl.add((double)COLOR_PALETTE_ELEM_SIZE);
		}
		else{
			ArrayList<Color> nm=new ArrayList<Color>();
			int i=0;
			for (Color c:this.cp.cl){
				if (i==idx){
					nm.add(nc);
				}
				else{
					nm.add(c);
				}
				i++;
			}
			this.cp.cl=nm;
		}
	}



	private Rectangle _get_frame_rect(){
		return new Rectangle(WINDOW_SIZE.width/2-COLOR_PALETTE_GUI_SIZE.width/2,WINDOW_SIZE.height/2-COLOR_PALETTE_GUI_SIZE.height/2,COLOR_PALETTE_GUI_SIZE.width,COLOR_PALETTE_GUI_SIZE.height);
	}



	private ArrayList<Color> _move(ArrayList<Color> m,int s,int e){
		if (s==e){
			return m;
		}
		int i=0;
		Color el=null;
		ArrayList<Color> nm=new ArrayList<Color>();
		for (Color o:m){
			if (i==s){
				el=o;
				break;
			}
			i++;
		}
		if (el==null){
			return m;
		}
		if (s<e){
			e++;
		}
		i=0;
		for (Color o:m){
			if (i==s){
				i++;
				continue;
			}
			if (i==e){
				nm.add(el);
			}
			nm.add(o);
			i++;
		}
		if (i==e){
			nm.add(el);
		}
		return nm;
	}
}
