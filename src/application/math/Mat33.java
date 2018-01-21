package application.math;

import com.sun.javafx.binding.StringFormatter;

public class Mat33 {
    private static final int SIZE = 3;
    private static final int SIZE2 = SIZE*SIZE;

    private float[] mat;

    public Mat33() {
        this(0);
    }

    public Mat33(float diagonal) {
        mat = new float[SIZE2];
        for (int i = 0; i < SIZE; i++) {
            setElement(i, i, diagonal);
        }
    }

    public Mat33(float[] mat) {
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

    public Mat33 mul(Mat33 m){
        //TODO: implement Mat33 mul Mat33
        return null;
    }

    public Mat33 mul(float scl){
        //TODO: implement Mat33 mul float
        return null;
    }

    public Vec2 mul(Vec2 vec){
        //TODO: implement Mat33 mul Vec2
        return null;
    }

    public Vec3 mul(Vec3 vec){
        //TODO: implement Mat33 mul Vec3
        return null;
    }

    public Mat33 transpose(){
        //TODO: implement Mat33 transpose
        return null;
    }

    public float determinant(){
        //TODO: implement Mat33 determinant
        return 0;
    }

    public Mat22 minor(int row, int col){
        //TODO: implement Mat33 minor function
        return null;
    }

    public Mat33 inverse(){
        //TODO: implement Mat33 inverse
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
