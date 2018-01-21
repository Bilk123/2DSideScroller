package core;


import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    /**
     * a 'pointer' for the window
     */
    private long window;

    /**
     * the dimension variables for the window
     */
    private int width, height;

    /**
     * The title of the window
     */
    private String title;

    /**
     * Creates a window of specified dimensions, centered in the center of the primary monitor.
     * Also sets the current context to the created window.
     *
     * @param width  is the width of the window.
     * @param height is the height of the window.
     * @param title  is the title of the window.
     */
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        System.out.println("Version: LWJGL "+Version.getVersion());

        init();
    }


    /**
     * Checks for errors whilst creating the new window.
     * Sets the window to a fixed size.
     */
    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("unable to initialise GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("Failed to create glfw window");


        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);

        //enable/disable V-SYNC.
        glfwSwapInterval(0);

        glfwShowWindow(window);

        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * clears the screen buffer, should be called at the start of every frame.
     */
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Checks for openGL errors, the swaps the buffers and checks for input events.
     */
    public void update() {
        int error = glGetError();
        if (error != GL_NO_ERROR) {
            System.out.println("OpenGL error: 0x" + Integer.toHexString(error));
        }
        glfwSwapBuffers(window);

        glfwPollEvents();
    }


    /**
     * Checks if the window should close.
     *
     * @return whether or not the window should close or stay open.
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }


    /**
     * Correctly removes the window from the GPU's memory
     */
    public void dispose() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
