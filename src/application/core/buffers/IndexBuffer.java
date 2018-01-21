package application.core.buffers;

import application.util.BufferUtil;

import static org.lwjgl.opengl.GL15.*;

public class IndexBuffer {
    private int IBO_ID;
    private int count;

    public IndexBuffer(int[] indices) {
        count = indices.length;
        IBO_ID = glGenBuffers();
        reSubmit(indices);
    }

    private void reSubmit(int[] indices) {
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtil.createBuffer(indices), GL_STATIC_DRAW);
        unbind();
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO_ID);
    }

    public int getCount() {
        return count;
    }
}
