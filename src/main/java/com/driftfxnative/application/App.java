package com.driftfxnative.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.eclipse.fx.drift.DriftFXSurface;

public class App extends Application
{
    static
    {
        System.loadLibrary("native-dynamic");
    }

    private native void nativeInit();

    private DriftFXSurface surface;

    @Override
    public void start(Stage stage) {
        /*
        final GLProfile profile     = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        */

        surface = new DriftFXSurface();

        String javaVersion   = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l              = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        StackPane pane = new StackPane(l);

        pane.getChildren().add(surface);

        Scene scene = new Scene(pane, 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
