package com.krzem.image_editor;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Class;
import java.lang.ClassLoader;
import java.util.ArrayList;



public class ToolClassLoader extends ClassLoader{
	@Override
	public Class<?> findClass(String nm) throws ClassNotFoundException{
		try{
			byte[] b=this.load_(nm);
			return this.defineClass(nm.substring(nm.lastIndexOf("\\")+1,nm.length()-5),b,0,b.length);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	private byte[] load_(String fn) throws FileNotFoundException,IOException,InterruptedException{
		ProcessBuilder b=new ProcessBuilder("cmd.exe","/c","cd \""+fn.substring(0,fn.lastIndexOf("\\"))+"\"&&javac -sourcepath "+fn.substring(0,fn.lastIndexOf("\\com\\"))+" "+fn.substring(fn.lastIndexOf("\\")+1));
		Process p=b.start();
		p.waitFor();
		InputStream inpS=new FileInputStream(new File(fn.replace(".java",".class")));
		ByteArrayOutputStream bS=new ByteArrayOutputStream();
		int nv=0;
		try{
			while ((nv=inpS.read())!=-1){
				bS.write(nv);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return bS.toByteArray();
	}



	public ArrayList<ToolGroup> load_tool_groups(Toolbar tb,String dir){
		try{
			ArrayList<ToolGroup> tool_g=new ArrayList<ToolGroup>();
			for (File f:new File(dir).listFiles()){
				if (f.isFile()&&f.getName().endsWith("ToolGroup.java")){
					Class<?> o=this.loadClass(f.getAbsolutePath());
					if (o!=null){
						Object t=o.getConstructor(Main.class).newInstance(tb.cls);
						tool_g.add(new ToolGroup(tb.cls,tb,t));
					}
				}
			}
			return tool_g;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	public ArrayList<Tool> load_tools(Toolbar tb,String dir){
		try{
			ArrayList<Tool> tools=new ArrayList<Tool>();
			for (File f:new File(dir).listFiles()){
				if (f.isFile()&&f.getName().endsWith("Tool.java")){
					Class<?> o=this.loadClass(f.getAbsolutePath());
					if (o!=null){
						Object t=o.getConstructor(Main.class,ImageEditorWindow.class).newInstance(tb.cls,tb.i);
						tools.add(new Tool(tb.cls,tb,t));
					}
				}
			}
			return tools;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}