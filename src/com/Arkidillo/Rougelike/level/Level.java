package com.Arkidillo.Rougelike.level;

import com.Arkidillo.Rougelike.graphics.Shader;
import com.Arkidillo.Rougelike.graphics.Texture;
import com.Arkidillo.Rougelike.graphics.VertexArray;
import com.Arkidillo.Rougelike.math.Matrix4f;
import com.Arkidillo.Rougelike.math.Vector3f;

import java.util.Random;

/**
 * Created by Devin on 2/7/2017.
 */
public class Level {
    private VertexArray background;
    private Texture bgTexture;

    private int xScroll = 0;
    private int map = 0;

    private Bird bird;

    private Pipe[] pipes = new Pipe[5 * 2];
    
    private Random random = new Random();

    private int index = 0;

    private float OFFSET = 5.0f;
    public static boolean control = true; 	//Whether or not the play is in control

    public Level() {
        float[] vertices = new float[]{
                -10.0f, -10.f * 9.0f / 16.0f, 0.0f,
                -10.0f, 10.f * 9.0f / 16.0f, 0.0f,
                0.0f, 10.f * 9.0f / 16.0f, 0.0f,
                0.0f, -10.f * 9.0f / 16.0f, 0.0f
        };

        byte[] indices = new byte[]{   //each number here uses the corresponding vertex of the ^ vertex array (1 here  = vertices[1])
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[]{    //same thing applies as for indicies ^
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        bgTexture = new Texture("resources/testImg.png");
        background = new VertexArray(vertices, indices, tcs);

        bird = new Bird();
        createPipes();
    }

    private void createPipes(){
    	Pipe.create();
        for (int i = 0; i < 5 * 2; i += 2) {
            pipes[i] = new Pipe(OFFSET + index * 3.0f, random.nextFloat() * 4.0f);    //top pipe
            pipes[i + 1] = new Pipe(pipes[i].getX(), pipes[i].getY() - 11.5f);
            index += 2;
        }
    }

    private void updatePipes(){
        pipes[index % 10] = new Pipe(OFFSET + index * 3.0f, random.nextFloat() * 4.0f);    //top pipe
        pipes[(index + 1) % 10] = new Pipe(OFFSET + index * 3.0f, pipes[index % 10].getY() - 11.5f);
        index += 2;
    }

    public void update(){
    	if (control){
    		xScroll--;
        	if(-xScroll % 335 == 0) map++;
        	if (-xScroll > 250 && -xScroll % 120 == 0) {  //once there have been 10 pipes
        		updatePipes();
        	}
    	}

    	bird.update();
        if(control && collision()){
        	bird.fall();
        	control = false;
        }
    }

    private void renderPipes(){
        Shader.PIPE.enable();
        Shader.PIPE.setUniformMat4f("vw_matrix",  Matrix4f.translate(new Vector3f(xScroll * 0.05f, 0.0f, 0.0f)));
        Pipe.getTexture().bind();
        Pipe.getMesh().bind();

        for(int i = 0; i < 5 * 2 ; i++){
            Shader.PIPE.setUniformMat4f("ml_matrix", pipes[i].getModelMatrix());
            Shader.PIPE.setUniform1i("top", i % 2 == 0 ? 1 : 0);
            Pipe.getMesh().draw();
        }

        Pipe.getTexture().unbind();
        Pipe.getMesh().unbind();
    }

    public boolean collision(){
    	for (int i = 0; i < 5 * 2; i++){
    		float bx = -xScroll * 0.05f;
    		float by = bird.getY();
    		float px = pipes[i].getX();
    		float py = pipes[i].getY();
    		
    		float bx0 = bx - bird.getSize() / 2.0f;
    		float bx1 = bx + bird.getSize() / 2.0f;
    		float by0 = by - bird.getSize() / 2.0f;
    		float by1 = by + bird.getSize() / 2.0f;
    		
    		float px0 = px;
    		float px1 = px + Pipe.getWidth();
    		float py0 = py;
    		float py1 = py + Pipe.getHeight();
    		
    		if(bx1 > px0 && bx0 < px1){
    			if(by1 > py0 && by0 < py1){
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    
    public void render() {
        Shader.BG.enable();
        bgTexture.bind();
        background.bind();
        for (int i = map; i < map + 4; i++){
            Shader.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
            background.draw();
        }
        renderPipes();
        Shader.BG.disable();
        bgTexture.unbind();
        bird.render();
    }
}
