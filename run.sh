#!/bin/bash

JAVA_EXPORTS='--add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED
	--add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED
	--add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED
	--add-exports javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED
	--add-exports javafx.graphics/com.sun.javafx.geom=ALL-UNNAMED
	--add-exports javafx.graphics/com.sun.javafx.geom.transform=ALL-UNNAMED
	--add-exports javafx.graphics/com.sun.javafx.scene.text=ALL-UNNAMED
	--add-exports javafx.graphics/com.sun.javafx.font=ALL-UNNAMED
	--add-exports javafx.graphics/com.sun.prism.paint=ALL-UNNAMED
	--add-opens   javafx.graphics/com.sun.prism=ALL-UNNAMED
	--add-opens   javafx.graphics/com.sun.prism.es2=ALL-UNNAMED
	--add-opens   javafx.graphics/com.sun.prism.impl=ALL-UNNAMED'

JAVA_MODULES='javafx.controls,javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web'

#echo $JAVA_EXPORTS

java --module-path $JAVA_FX --add-modules $JAVA_MODULES $JAVA_EXPORTS -Djava.library.path="/Users/jhonny/dev/drift-fx-native-lib/native/build"  -cp "target/drift-fx-native-lib-1.0-SNAPSHOT.jar:/Users/jhonny/.m2/repository/org/eclipse/fx/org.eclipse.fx.drift/1.0.0/org.eclipse.fx.drift-1.0.0.jar"   com.driftfxnative.application.App
