package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Activations;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Outline of what an activation function should have. A function and a function derivative.
*/

public interface IActivationFunction
{
    double activate(double[] inputs, int index);

    double activateDerivative(double[] inputs, int index);
}
