package com.driftfxnative.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.eclipse.fx.drift.DriftFXSurface;
import org.eclipse.fx.drift.GLRenderer;
import org.eclipse.fx.drift.Renderer;
import org.eclipse.fx.drift.StandardTransferTypes;
import org.eclipse.fx.drift.TransferType;

public class App extends Application
{
    //////////////////
    // Native JNI glue
    static
    {
        System.loadLibrary("native-dynamic");
    }

    private native void nativeInit(ClassLoader classLoader);
    private native long nativeOnStart(Renderer renderer, TransferType transferType);
    private native long nativeOnRender(long nativeCtx);
    //////////////////

    private Renderer       renderer;
    private DriftFXSurface surface;
    private long           ctx;
    private long           nativeCtx;
    private Thread         renderThread;
    private boolean        running;

    @Override
    public void start(Stage stage)
    {
        nativeInit(Application.class.getClassLoader());

        ctx = org.eclipse.fx.drift.internal.GL.createSharedCompatContext(0);
        org.eclipse.fx.drift.internal.GL.makeContextCurrent(ctx);
        System.err.println("Context is " + ctx);

        surface = new DriftFXSurface();

        String javaVersion   = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l              = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        StackPane pane = new StackPane();

        pane.getChildren().add(surface);
        pane.getChildren().add(l);

        Scene scene = new Scene(pane, 640, 480);
        stage.setScene(scene);
        stage.show();

        // Start render thread
        renderThread = new Thread(this::run);
        renderThread.setDaemon(true);
        renderThread.start();
    }

    @Override
    public void stop()
    {
        running = false;
        System.err.println("Exiting!");
    }

    void run() {
        if (running) {
            return;
        }
        running = true;

        org.eclipse.fx.drift.internal.GL.makeContextCurrent(ctx);
        nativeCtx = nativeOnStart(GLRenderer.getRenderer(surface), StandardTransferTypes.IOSurface);

        try {
            while(running)
            {
                org.eclipse.fx.drift.internal.GL.makeContextCurrent(ctx);

                nativeOnRender(nativeCtx);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch();
    }
}
