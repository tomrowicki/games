package com.chess;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.chess.gui.Table;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JChess extends Application {

	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream input = null;
		String propFilename = "./res/application.properties";

		try {
			prop.load(new FileInputStream(propFilename));
			String runMode = prop.getProperty("app.mode");
			if (runMode.equals("fx")) {
				runFX();
			} else {
				runSwing();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void runFX() {
		System.out.println("running fx!");
		launch();
	}

	private static void runSwing() {
		Table.get().show();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		primaryStage.setTitle("JChess");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(640, 480);
		return root;
	}
}
