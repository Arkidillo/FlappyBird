package com.Arkidillo.Rougelike.math;

public class Vector3f {

	public float x, y, z;	//z used for layering, not for 3d.
	
	public Vector3f(){
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	public Vector3f(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
