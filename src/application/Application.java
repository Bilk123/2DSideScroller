package application;

import application.core.Time;
import application.core.Window;

import static org.lwjgl.opengl.GL11.*;

public class Application {

    private static int ups = 60;

    public static void main(String[] args) {

        Window window = new Window(1200, 720, "2D game");

        int frames = 0;
        long timer = System.currentTimeMillis();
        long lastTime = Time.getTime();
        final double ns = Time.SECOND / ups;
        double delta = 0;
        int updates = 0;
        float x = 0;
        float c = 0.01f;
        while (!window.shouldClose()) {
            window.clear();

            long now = Time.getTime();
            delta += (now - lastTime) / ns;
            Time.setDeltaTime((float) (delta / ups));
            lastTime = now;

            while (delta >= 1) {
                delta--;

                x+=c;
                if((x>=1||x<=0)){
                    c*=-1;
                }
                //update in this loop
                updates++;
            }
            //render here
            glBegin(GL_TRIANGLES);
            glVertex2f(-0.5f, -0.5f);
            glColor3f(1-x,0,0);
            glVertex2f(0.5f, -0.5f);
            glColor3f(0,x,0);
            glVertex2f(0.0f, 0.5f);
            glColor3f(0,0,1-x);
            glEnd();


            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("fps | " + frames + " || ups | " + updates);

                frames = 0;
                updates = 0;
            }
            window.update();
        }
        window.dispose();
    }
}
