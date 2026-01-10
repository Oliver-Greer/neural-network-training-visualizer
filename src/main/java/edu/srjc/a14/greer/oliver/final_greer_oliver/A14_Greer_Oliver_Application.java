package edu.srjc.a14.greer.oliver.final_greer_oliver;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    FXML application class for a neural network training sim.
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class A14_Greer_Oliver_Application extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(A14_Greer_Oliver_Application.class.getResource("A14_Greer_Oliver_View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Neural Network Trainer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}