package application.math;

import com.sun.javafx.binding.StringFormatter;

public class Mat44 {
    private static final int SIZE = 4;
    private static final int SIZE2 = SIZE * SIZE;

    private float[] mat;

    public Mat44() {
        this(0);
    }

    public Mat44(float diagonal) {
        mat = new float[SIZE2];
        for (int i = 0; i < SIZE; i++) {
            setElement(i, i, diagonal);
        }
    }

    public Mat44(float[] mat) {
        this.mat = new float[SIZE2];
        if (mat.length >= SIZE2)
            System.arraycopy(mat, 0, this.mat, 0, SIZE2);
        else {
            System.arraycopy(mat, 0, this.mat, 0, mat.length);
            for (int i = mat.length; i < SIZE2; i++) {
                this.mat[i] = 0;
            }
        }
    }

    public Mat44 mul(Mat44 m){
        //TODO: implement Mat44 mul Mat44
        return null;
    }

    public Mat44 mul(float scl){
        //TODO: implement Mat44 mul float
        return null;
    }

    public Vec3 mul(Vec3 vec){
        //TODO: implement Mat44 mul Vec3
        return null;
    }

    public Vec4 mul(Vec4 vec){
        //TODO: implement Mat44 mul Vec4
        return null;
    }

    public Mat44 transpose(){
        //TODO: implement Mat44 transpose
        return null;
    }

    public float determinant(){
        //TODO: implement Mat44 determinant
        return 0;
    }

    public Mat33 minor(int row, int col){
        //TODO: implement mat44 minor function
        return null;
    }

    public Mat44 inverse(){
        //TODO: implement Mat44 inverse
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                sb.append(StringFormatter.format("%.1f",getElement(x, y)).getValue());
                if (x < SIZE-1) {
                    sb.append(", ");
                } else
                    sb.append("]\n");
            }
            if (y < SIZE-1) {
                sb.append("[");
            } else sb.append("\n");
        }
        return sb.toString();
    }

    public float getElement(int x, int y) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return mat[x + SIZE * y];
        } else return 0.0f;
    }

    public void setElement(int x, int y, float val) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            mat[x + y * SIZE] = val;
        }
    }
}
