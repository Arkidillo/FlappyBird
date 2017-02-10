package com.Arkidillo.Rougelike.Input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;


public class Input extends GLFWKeyCallback{

	public static boolean[] keys = new boolean[66536];	//Too much memory used here
	
	public void invoke(long window, int key, int scancode, int action, int mods){
		keys[key] = action != GLFW.GLFW_RELEASE;
	}

	public static boolean isKeyDown(int keyCode){
		return keys[keyCode];
	}
}
