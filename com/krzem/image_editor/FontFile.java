package com.krzem.image_editor;



import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.lang.Exception;



public class FontFile{
	public static Font load(String fn,int ft,int fs){
		try{
			Font f=Font.createFont(Font.TRUETYPE_FONT,new File(fn));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
			return new Font(fn.substring(fn.lastIndexOf("/")+1).split("\\.")[0].replace("-",""),ft,fs);
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}