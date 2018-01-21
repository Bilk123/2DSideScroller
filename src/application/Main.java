package application;

import core.Time;
import core.Window;

public class Main {

    private static int ups = 60;

    public static void main(String[] args) {

        Window window = new Window(1200, 720, "2D game");

        int frames = 0;
        long timer = System.currentTimeMillis();
        long lastTime = Time.getTime();
        final double ns = Time.SECOND / ups;
        double delta = 0;
        int updates = 0;

        while (!window.shouldClose()) {
            window.clear();

            long now = Time.getTime();
            delta += (now - lastTime) / ns;
            Time.setDeltaTime((float) (delta / ups));
            lastTime = now;
            float dt = Time.deltaTime;
            while (delta >= 1) {
                delta--;
                updates++;
            }

            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("fps | " + frames + " || ups | " + updates);
                frames=0;
                updates=0;
            }
            window.update();
        }
        window.dispose();
    }
}
