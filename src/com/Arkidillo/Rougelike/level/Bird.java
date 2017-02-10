package com.Arkidillo.Rougelike.level;


import com.Arkidillo.Rougelike.Input.Input;
import com.Arkidillo.Rougelike.graphics.Shader;
import com.Arkidillo.Rougelike.graphics.Texture;
import com.Arkidillo.Rougelike.graphics.VertexArray;
import com.Arkidillo.Rougelike.math.Matrix4f;
import com.Arkidillo.Rougelike.math.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Devin on 2/9/2017.
 */
public class Bird {

    private float SIZE = 1.0f;
    private VertexArray mesh;
    private Texture texture;

    private Vector3f position = new Vector3f();
    private float rot;
    private float deltaY = 0.0f;

    public Bird(){
        float[] vertices = new float[]{
                -SIZE / 2.0f, -SIZE / 2.0f, 0.2f,
                -SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
                 SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
                 SIZE / 2.0f, -SIZE / 2.0f, 0.2f
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

        texture = new Texture("resources/testImg.png");
        mesh = new VertexArray(vertices, indices, tcs);
    }

    public void update(){
        position.y -= deltaY;
        if(Input.isKeyDown(GLFW_KEY_SPACE) && Level.control){
            deltaY = -0.15f;
        }else{
            deltaY += 0.01f;
        }

        rot = -deltaY * 90.0f;

    }

    public void fall(){
        deltaY = -0.15f;
    }

    public void render(){
        Shader.BIRD.enable();
        Shader.BIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(position).mutliply(Matrix4f.rotate(rot)));
        texture.bind();
        mesh.render();
        Shader.BIRD.disable();
        texture.unbind();
    }
    
    public float getY(){
    	return position.y;
    }
    
    public float getSize(){
    	return SIZE;
    }
}
