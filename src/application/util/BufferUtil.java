package application.util;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {
    public static FloatBuffer createBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static int[] toIntArray(Integer[] a){
        int [] temp = new int[a.length];
        for(int i = 0;i<a.length;i++){
            temp[i] = a[i];
        }
        return temp;
    }

    public static float[] toFloatArray(Float[] a) {
        float [] temp = new float[a.length];
        for(int i = 0;i<a.length;i++){
            temp[i] = a[i];
        }
        return temp;

    }
}
