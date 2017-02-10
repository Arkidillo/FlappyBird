package com.Arkidillo.Rougelike.level;

import com.Arkidillo.Rougelike.graphics.Texture;
import com.Arkidillo.Rougelike.graphics.VertexArray;
import com.Arkidillo.Rougelike.math.Matrix4f;
import com.Arkidillo.Rougelike.math.Vector3f;

/**
 * Created by Devin on 2/9/2017.
 */
public class Pipe {
    private Vector3f position = new Vector3f();
    private Matrix4f ml_matrix;
    private int top;

    private static float width = 1.5f, height = 8.0f;//Height of screen = 20?
    private static Texture texture;
    private static VertexArray mesh;

    public static void create(){
        float[] vertices = new float[]{
                0.0f, 0.0f, 0.1f,
                0.0f, height, 0.1f,
                width, height, 0.1f,
                width, 0.0f, 0.1f
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


    public Pipe(float x, float y){
        position.x = x;
        position.y = y;
        ml_matrix = Matrix4f.translate(position);
    }

    public Matrix4f getModelMatrix(){
        return ml_matrix;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public static VertexArray getMesh(){
        return mesh;
    }

    public static Texture getTexture(){
        return texture;
    }

    public static float getWidth(){
        return width;
    }

    public static float getHeight(){
        return height;
    }


}
