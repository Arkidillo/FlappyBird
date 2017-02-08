package com.Arkidillo.Rougelike;

import static org.lwjgl.glfw.GLFW.*;	//Imports all static methods in this class so we dont need to write GLFW.methodName()
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import com.Arkidillo.Rougelike.graphics.Shader;
import com.Arkidillo.Rougelike.level.Level;
import com.Arkidillo.Rougelike.math.Matrix4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.glfw.GLFWVidMode;

import com.Arkidillo.Rougelike.Input.Input;


public class MainFlappy implements Runnable{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private Thread thread;

	private Level level;
	
	private long window;	//long because it is just a window id. (pointer?)
	
	private void init(){
		if(!glfwInit()){
			throw new IllegalStateException("Failed to glfwInit");
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(WIDTH, HEIGHT, "Flappy", NULL, NULL);
		
		if(window == 0){
			throw new IllegalStateException("Failed to create window");
		}

		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor()); 
		glfwSetWindowPos(window, (videoMode.width() - WIDTH)/2, (videoMode.height() - HEIGHT) / 2);

		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		Shader.loadAll();
		
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f /16.0f, 10.0f * 9.0f/16.0f, -1.0f, 1.0f);	//Preserve 16:9 aspect ratio
		
		Shader.BG.enable();
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.disable();
		
		level = new Level();
	}
	
	public void run(){
		init();
		while(!glfwWindowShouldClose(window)){
			update();	//Delta time
			render();	//Leave render uncapped, but cap update at 60
		}
	}
	
	public void start(){
		thread = new Thread(this, "game");
		thread.start();
	}
	
	private void update(){
		glfwPollEvents();
		if(Input.keys[GLFW_KEY_SPACE]){
			System.out.println("Space pressed");
		}
	}
	
	private void render(){
		int error = glGetError();
		if (error != GL_NO_ERROR){
			System.out.println("error: " + error);
		}
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();
		glfwSwapBuffers(window);
	}

	public void MainFlappy(){

	}
	
	public static void main(String [] args){
		new MainFlappy().start();
	}

}
