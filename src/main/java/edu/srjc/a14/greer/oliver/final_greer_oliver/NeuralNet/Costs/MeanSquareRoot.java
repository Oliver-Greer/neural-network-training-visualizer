package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Costs;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Mean Square Root is a simple function that squares the difference between the predicted and expected outputs,
    then takes the average. This means that larger differences are accentuated, telling the network to adjust faster.
*/

public class MeanSquareRoot implements ICostFunction
{
    @Override
    public double calculateCost(double[] predictedOutputs, double[] expectedOutputs)
    {
        double totalCost = 0;
        for(int i = 0; i < predictedOutputs.length; i++)
        {
            double error = expectedOutputs[i] - predictedOutputs[i];
            totalCost += error * error;
        }
        return totalCost * 0.5;
    }

    @Override
    public double calculateCostDerivative(double predictedOutput, double expectedOutput)
    {
        return (predictedOutput - expectedOutput);
    }
}
