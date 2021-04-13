package com.krzem.image_editor;



import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;



public class Constants{
	public static final int DISPLAY_ID=0;
	public static final GraphicsDevice DEVICE=GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[DISPLAY_ID];
	public static final Rectangle WINDOW_SIZE=DEVICE.getDefaultConfiguration().getBounds();
	public static final int MAX_FPS=60;

	public static final Color BG_COLOR=new Color(230,230,230);

	public static final double IMAGE_ZOOM_INCREMENT=0.5;
	public static final int IMAGE_PAN_INCREMENT=5;
	public static final double IMAGE_DEFAULT_SCREEN_SIZE_PROC=0.5;
	public static final double IMAGE_MAX_GRID_SIZE_PROC=0.025;
	public static final double IMAGE_MIN_GRID_SIZE_PROC=0.01;
	public static final Color IMAGE_GRID_COLOR=new Color(45,45,45);
	public static final double IMAGE_PAN_PROC_WIDTH=0.8;
	public static final double IMAGE_PAN_PROC_HEIGHT=0.75;

	public static final int COLOR_PALETTE_WIDTH=60;
	public static final int COLOR_PALETTE_ELEM_SIZE=40;
	public static final int COLOR_PALETTE_HOVER_ELEM_SIZE=50;
	public static final double COLOR_PALETTE_ELEM_SIZE_INC=1.5;
	public static final double COLOR_PALETTE_ELEM_SIZE_DINC=2.5;
	public static final Color COLOR_PALETTE_BG_COLOR=new Color(35,35,35);
	public static final Color COLOR_PALETTE_OUTLINE_COLOR=new Color(15,15,15);
	public static final int COLOR_PALETTE_OUTLINE_THICKNESS=10;
	public static final int COLOR_PALLETTE_CUT_BORDER_SIZE=8;
	public static final String COLOR_PALETTE_KEY_MOD="control";
	public static final String COLOR_PALETTE_KEY_RESET="0";
	public static final String COLOR_PALETTE_KEY_CHARS="123456789abcdefghijklmnopqrstuvwxyz";
	public static final String COLOR_PALETTE_FG_KEY="shift";
	public static final String COLOR_PALETTE_KEY_OPEN="o";

	public static final Color GUI_BG_COLOR=new Color(0,0,0,100);
	public static final Color GUI_FRAME_BG_COLOR=new Color(45,45,45);
	public static final Color GUI_FRAME_OUTLINE_COLOR=new Color(15,15,15);
	public static final int GUI_FRAME_OUTLINE_THICKNESS=10;

	public static final Rectangle COLOR_PALETTE_GUI_SIZE=new Rectangle(0,0,800,600);
	public static final int COLOR_PALETTE_GUI_ELEM_SIZE=40;
	public static final int COLOR_PALETTE_GUI_ELEM_BORDER_SIZE=10;
	public static final int COLOR_PALETTE_GUI_ELEM_INSERT_RECT_SIZE=7;
	public static final Color COLOR_PALETTE_GUI_ELEM_INSERT_RECT_COLOR=new Color(80,80,80);
	public static final int COLOR_PALLETTE_GUI_CUT_BORDER_SIZE=2;

	public static final Rectangle COLOR_PICKER_GUI_SIZE=new Rectangle(0,0,600,500);
	public static final Color COLOR_PICKER_GUI_DEFAULT_COLOR=new Color(255,0,0,255);
	public static final Rectangle COLOR_PICKER_GUI_COLOR_RECT_FRAME=new Rectangle(50,50,300,300);
	public static final Rectangle COLOR_PICKER_GUI_HUE_SLIDER_FRAME=new Rectangle(400,50,50,300);
	public static final Rectangle COLOR_PICKER_GUI_ALPHA_SLIDER_FRAME=new Rectangle(500,50,50,300);
	public static final Rectangle COLOR_PICKER_GUI_PREVIEW_FRAME=new Rectangle(50,400,500,50);
	public static final Color COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_A=new Color(204,204,204);
	public static final Color COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_B=new Color(255,255,255);
	public static final Color COLOR_PICKER_GUI_SOLID_COLOR=new Color(255,255,255);
	public static final Rectangle COLOR_PICKER_GUI_TRANSPARENT_COLOR_TILE_SIZE=new Rectangle(0,0,5,5);
	public static final int COLOR_PICKER_GUI_FRAME_INNER_RECT_OUTLINE_THICKNESS=6;
	public static final Color COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE=new Color(200,200,200);
	public static final Rectangle COLOR_PICKER_GUI_RECT_SELECT_HANDLE_SIZE=new Rectangle(0,0,16,16);
	public static final Rectangle COLOR_PICKER_GUI_SLIDER_SELECT_HANDLE_SIZE=new Rectangle(0,0,50,16);
	public static final int COLOR_PICKER_GUI_SELECT_HANDLE_OUTLINE_THICKNESS=6;

	public static final int TOOLBAR_WIDTH=60;
	public static final int TOOLBAR_ELEM_SIZE=40;
	public static final int TOOLBAR_HOVER_ELEM_SIZE=50;
	public static final int TOOLBAR_OUTLINE_THICKNESS=10;
	public static final double TOOLBAR_ELEM_SIZE_INC=1.5;
	public static final double TOOLBAR_ELEM_SIZE_DINC=2.5;
	public static final Color TOOLBAR_BG_COLOR=new Color(45,45,45);
	public static final Color TOOLBAR_OUTLINE_COLOR=new Color(15,15,15);
	public static final Color TOOLBAR_DEFAULT_BG_COLOR=new Color(255,255,255,255);
	public static final Color TOOLBAR_DEFAULT_FG_COLOR=new Color(0,0,0,255);
	public static final Color TOOLBAR_ELEM_BG_COLOR=new Color(200,200,200);
	public static final Color TOOLBAR_ELEM_ICON_COLOR=new Color(45,45,45);
	public static final String TOOLBAR_KEY_MOD="alt";
	public static final String TOOLBAR_KEY_RESET="0";
	public static final String TOOLBAR_KEY_CHARS="123456789abcdefghijklmnopqrstuvwxyz";

	public static final String TOOL_DIR="./com/krzem/image_editor/tools";

	public static final int TOOLGROUP_WINDOW_ELEM_SIZE=40;
	public static final int TOOLGROUP_WINDOW_ELEM_HOVER_SIZE=50;
	public static final int TOOLGROUP_WINDOW_ELEM_TOTAL_SIZE=60;
	public static final Color TOOLGROUP_WINDOW_BG_COLOR=new Color(45,45,45);
	public static final Color TOOLGROUP_WINDOW_OUTLINE_COLOR=new Color(15,15,15);
	public static final int TOOLGROUP_WINDOW_OUTLINE_THICKNESS=10;
	public static final String TOOLGROUP_KEY_MOD="shift";

	public static final int PEN_TOOL_SIZE=2;
	public static final double PEN_TOOL_ICON_SCALE=0.2;

	public static final double RECT_TOOL_ICON_SIZE_PROC=0.6;
	public static final Color RECT_TOOL_OUTLINE_COLOR=new Color(0,0,0);

	public static final double LINE_TOOL_ICON_SIZE_PROC=0.6;
	public static final Color LINE_TOOL_OUTLINE_COLOR=new Color(0,0,0);

	public static final double CIRCLE_TOOL_ICON_SIZE_PROC=0.8;
	public static final Color CIRCLE_TOOL_OUTLINE_COLOR=new Color(0,0,0);
}
