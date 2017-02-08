package com.Arkidillo.Rougelike.math;

import java.nio.FloatBuffer;

import com.Arkidillo.Rougelike.utils.BufferUtils;

public class Matrix4f {

	public static final int SIZE = 4 * 4;
	public float[] elements = new float[SIZE];	//OPENGL likes 1d arrays
	
	public Matrix4f() {
			
	}
	
	public static Matrix4f identity(){	//Makes an identity matrix, because if you multiply any matrix by this, you get itself.
		Matrix4f result = new Matrix4f();
					//Row Column
		result.elements[0 + 0 * 4] = 1.0f;
		result.elements[1 + 1 * 4] = 1.0f;
		result.elements[2 + 2 * 4] = 1.0f;
		result.elements[3 + 3 * 4] = 1.0f;
		
		return result;
	}

	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far){	//Orthographic -> essentially a box. Used for 2d rendering. Things that are farther remain the same size.
		Matrix4f result = identity();
		
		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		
		result.elements[2 + 2 * 4] = 2.0f / (near - far);
		
		result.elements[0 + 3 * 4] = (left + right) / (left - right);
		result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.elements[2 + 3 * 4] = (far + near) / (far - near);
		
		return result;
	}
	
	public static Matrix4f translate(Vector3f vector){
		Matrix4f result = identity();
		result.elements[0 + 3 * 4] = vector.x;
		result.elements[1 + 3 * 4] = vector.y;
		result.elements[2 + 3 * 4] = vector.z;
		
		return result;
	}
	
	public static Matrix4f rotate(float angle){	//z axis rotation ONLY
		Matrix4f result = identity();
		
		float r = (float)Math.toRadians(angle);
		float cos = (float)Math.cos(r);
		float sin = (float)Math.sin(r);
		
		result.elements[0 + 0 * 4] = cos;
		result.elements[1 + 0 * 4] = sin;
		
		result.elements[0 + 1 * 4] = -sin;
		result.elements[1 + 1 * 4] = cos;
		
		return result;
	}
	
	public Matrix4f mutliply(Matrix4f matrix){
		Matrix4f result = new Matrix4f();
		
		for(int y = 0; y < 4; y++){
			for(int x = 0; x < 4; x++){
				float sum = 0.0f;
				for(int e = 0; e < 4; e++){
					sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
				}
				result.elements[x + y * 4] = sum;
			}
		}
		
		return result;
	}
	
	public FloatBuffer toFloatBuffer(){
		return BufferUtils.createFloatBuffer(elements);
	}
	
}
