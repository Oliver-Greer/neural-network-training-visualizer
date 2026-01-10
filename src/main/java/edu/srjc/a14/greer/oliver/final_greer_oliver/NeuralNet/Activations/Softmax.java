package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Activations;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Softmax gives outputs that all sum to 1, representing confidence that a particular answer is correct.
    This includes a normalizing operation that slows the function down but should provide numeric stability,
    especially when used with ReLU hidden layer activations.
*/

public class Softmax implements IActivationFunction
{
    @Override
    public double activate(double[] inputs, int index)
    {
        double exponentSum = 0;
        double maxInput = inputs[0];
        for(double input : inputs)
        {
            if(input > maxInput)
            {
                maxInput = input;
            }
        }
        for (double input : inputs)
        {
            exponentSum += Math.exp(input - maxInput);
        }

        return Math.exp(inputs[index] - maxInput) / exponentSum;
    }

    @Override
    public double activateDerivative(double[] inputs, int index)
    {
        double exponentSum = 0;
        double maxInput = inputs[0];
        for(double input : inputs)
        {
            if(input > maxInput)
            {
                maxInput = input;
            }
        }
        for (double input : inputs)
        {
            exponentSum += Math.exp(input - maxInput);
        }

        double exp = Math.exp(inputs[index] - maxInput);

        return ((exp * exponentSum) - (exp * exp)) / (exponentSum * exponentSum);
    }
}
