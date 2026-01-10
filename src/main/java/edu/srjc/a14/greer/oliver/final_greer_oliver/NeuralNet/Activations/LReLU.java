package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Activations;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    LReLU is damped linear for all inputs <= 0 and linear for all inputs > 0;
*/

public class LReLU implements IActivationFunction
{
    @Override
    public double activate(double[] inputs, int index)
    {
        return Math.max(0.5 * inputs[index], inputs[index]);
    }

    @Override
    public double activateDerivative(double[] inputs, int index)
    {
        if(inputs[index] < 0)
        {
            return 0.5;
        }
        else if (inputs[index] == 0)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
