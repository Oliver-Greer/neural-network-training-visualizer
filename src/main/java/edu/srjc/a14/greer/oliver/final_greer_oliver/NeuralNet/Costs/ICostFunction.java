package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Costs;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Outline of what a cost function should have. A function and a function derivative.
    Only the derivative is actually used in back propagation but the original function is here for clarity.
*/

public interface ICostFunction
{
    double calculateCost(double[] predictedOutputs, double[] expectedOutputs);

    double calculateCostDerivative(double predictedOutput, double expectedOutput);
}
