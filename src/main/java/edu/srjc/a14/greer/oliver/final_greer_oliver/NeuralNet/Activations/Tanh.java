package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Activations;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Tanh is smoothly bounded between -1 and 1.
*/

public class Tanh implements IActivationFunction
{

    @Override
    public double activate(double[] inputs, int index)
    {
        return Math.tanh(inputs[index]);
    }

    @Override
    public double activateDerivative(double[] inputs, int index)
    {
        double tanh = activate(inputs, index);
        return 1 - (tanh * tanh);
    }
}
