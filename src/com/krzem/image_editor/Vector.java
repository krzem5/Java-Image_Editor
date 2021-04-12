package com.krzem.image_editor;



public class Vector{
	public int x;
	public int y;



	public Vector(int x,int y){
		this.x=x;
		this.y=y;
	}



	public Vector clone(){
		return new Vector(this.x,this.y);
	}
}