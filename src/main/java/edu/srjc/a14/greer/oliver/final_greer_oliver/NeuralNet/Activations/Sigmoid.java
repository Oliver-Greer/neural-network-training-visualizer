package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Activations;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    The Sigmoid function is also called the logistic function and has output bounded between 1 and 0.
*/

public class Sigmoid implements IActivationFunction
{
    @Override
    public double activate(double[] inputs, int index)
    {
        return 1 / (1 + Math.exp(-inputs[index]));
    }

    @Override
    public double activateDerivative(double[] inputs, int index)
    {
        double activationValue = activate(inputs, index);
        return activationValue * (1 - activationValue);
    }
}
