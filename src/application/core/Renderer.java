package application.core;

import application.core.buffers.IndexBuffer;
import application.math.Mat33;
import application.math.Vec2;
import application.math.Vec3;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
    private static final int MAX_SPRITES = 60000;
    private static final int VERTEX_SIZE = (3 + 2) * Float.BYTES + Integer.BYTES;// Vec3 + Vec2 + int (location + texture coords + texture ID)
    private static final int SPRITE_SIZE = VERTEX_SIZE * 4;
    private static final int BUFFER_SIZE = SPRITE_SIZE * MAX_SPRITES;
    private static final int INDICES_SIZE = MAX_SPRITES * 6;

    public static final int SHADER_VERTEX_INDEX = 0;
    public static final int SHADER_UV_INDEX = 1;
    public static final int SHADER_TID_INDEX = 2;

    private int VAO; //Vertex Array Object pointer;
    private IndexBuffer IBO; //Index Buffer Object
    private int indexCount;
    private int VBO; //Vertex Buffer Object
    private FloatBuffer vertexData;
    private ArrayList<Integer> textureSlots;

    public Renderer() {
        textureSlots = new ArrayList<>();
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();

        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, BUFFER_SIZE, GL_DYNAMIC_DRAW);

        glEnableVertexAttribArray(SHADER_VERTEX_INDEX);
        glEnableVertexAttribArray(SHADER_UV_INDEX);
        glEnableVertexAttribArray(SHADER_TID_INDEX);

        glVertexAttribPointer(SHADER_VERTEX_INDEX, 3/*Vec3*/, GL_FLOAT, false, VERTEX_SIZE, 0);
        glVertexAttribPointer(SHADER_UV_INDEX, 2/*Vec2*/, GL_FLOAT, false, VERTEX_SIZE, 3 * Float.BYTES/*Vec3.BYTES*/);
        glVertexAttribPointer(SHADER_TID_INDEX, 1/*int*/, GL_FLOAT, false, VERTEX_SIZE, (2 + 3) * Float.BYTES);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        int[] indices = new int[INDICES_SIZE];
        for (int i = 0, offset = 0; i < INDICES_SIZE; i += 6, offset += 4) {
            indices[i] = offset;
            indices[i + 1] = offset + 1;
            indices[i + 2] = offset + 2;
            indices[i + 3] = offset + 2;
            indices[i + 4] = offset + 3;
            indices[i + 5] = offset;
        }
        IBO = new IndexBuffer(indices);
    }

    public void begin() {
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        vertexData = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY).asFloatBuffer();
    }

    public void submit(Sprite sprite) {
        float ts = 0;
        int tID = sprite.getTexture().getID();
        if (tID > 0) {
            boolean found = false;
            for (int i = 0; i < textureSlots.size(); i++) {
                if (textureSlots.get(i) == tID) {
                    ts = i + 1;
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (textureSlots.size() >= 32) {
                    end();
                    flush();
                    begin();
                }
                textureSlots.add(tID);
                ts = textureSlots.size();
            }
        }

        Mat33 sT = sprite.getTransform().getMat();//sprite transform
        addVertex(sT.mul(new Vec3(0, 0, 0)), new Vec2(0, 0), ts);
        addVertex(sT.mul(new Vec3(0, 1, 0)), new Vec2(0, 1), ts);
        addVertex(sT.mul(new Vec3(1, 1, 0)), new Vec2(1, 1), ts);
        addVertex(sT.mul(new Vec3(1, 0, 0)), new Vec2(1, 0), ts);
        indexCount += 6;
    }

    private void addVertex(Vec3 vertex, Vec2 uv, float tID) {
        vertexData.put(vertex.x);
        vertexData.put(vertex.y);
        vertexData.put(vertex.z);
        vertexData.put(uv.x);
        vertexData.put(uv.y);
        vertexData.put(tID);
    }

    public void end() {
        vertexData.flip();
        glUnmapBuffer(GL_ARRAY_BUFFER);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void flush() {
        glPolygonMode(GL_FRONT, GL_FILL);

        for (int i = 0; i < textureSlots.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, textureSlots.get(i));
        }

        glBindVertexArray(VAO);
        IBO.bind();
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, NULL);
        IBO.unbind();
        glBindVertexArray(0);
        indexCount = 0;
    }

}
