package com.Arkidillo.Rougelike.level;

import com.Arkidillo.Rougelike.graphics.Shader;
import com.Arkidillo.Rougelike.graphics.VertexArray;

/**
 * Created by Devin on 2/7/2017.
 */
public class Level {
    private VertexArray background;

    public Level() {
        float[] vertices = new float[]{
                -10.0f, -10.f * 9.0f / 16.0f, 0.0f,
                -10.0f, 10.f * 9.0f / 16.0f, 0.0f,
                10.0f, 10.f * 9.0f / 16.0f, 0.0f,
                10.0f, -10.f * 9.0f / 16.0f, 0.0f
        };

        byte[] indicies = new byte[]{   //each number here uses the corresponding vertex of the ^ vertex array (1 here  = vertices[1])
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[]{    //same thing applies as for indicies ^
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f
        };

        background = new VertexArray(vertices, indicies, tcs);
    }

    public void render() {
        Shader.BG.enable();
        background.render();
        Shader.BG.disable();
    }
}
