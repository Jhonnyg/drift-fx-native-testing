package com.driftfxnative.application;

/*
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.eclipse.fx.drift.DriftFXSurface;
*/

public class App
{
    static
    {
        System.loadLibrary("native-dynamic");
    }

    private native void nativeInit();

    public static void main( String[] args )
    {
        System.out.println("Hello from java!");

        new App().nativeInit();
    }
}
