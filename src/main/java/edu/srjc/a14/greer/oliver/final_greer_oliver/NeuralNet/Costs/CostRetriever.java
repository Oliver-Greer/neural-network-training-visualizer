package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Costs;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Static class that returns CostFunction classes with the appropriate name.
    Used to change the neural network settings easily.
*/

public class CostRetriever
{
    public static final String defaultCost = "CrossEntropy";

    public static ICostFunction getCostFunction(String name)
    {
        if(name == null)
        {
            name = defaultCost;
        }

        return switch (name)
        {
            case "MeanSquareRoot" -> new MeanSquareRoot();
            case "CrossEntropy" -> new CrossEntropy();
            default -> null;
        };
    }
}
