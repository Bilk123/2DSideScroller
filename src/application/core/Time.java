package application.core;

public class Time {
    public static final int SECOND = 1000000000;
    public static final int MILLISECOND = 1000000;
    public static float deltaTime =0;

    public static void setDeltaTime(float deltaTime) {
        Time.deltaTime = deltaTime;
    }

    public static long getTime(){
        return System.nanoTime();
    }
}
