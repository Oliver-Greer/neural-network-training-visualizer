package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Activations;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Static class that returns ActivationFunction classes with the appropriate name.
    Used to change the neural network settings easily.
*/

public class ActivationRetriever
{
    public static final String defaultActivation = "Sigmoid";

    public static IActivationFunction getActivationFunction(String functionName)
    {
        if(functionName == null)
        {
            functionName = defaultActivation;
        }
        return switch (functionName)
        {
            case "LReLU" -> new LReLU();
            case "ReLU" -> new ReLU();
            case "Sigmoid" -> new Sigmoid();
            case "Tanh" -> new Tanh();
            case "Softmax" -> new Softmax();
            default -> null;
        };
    }
}
