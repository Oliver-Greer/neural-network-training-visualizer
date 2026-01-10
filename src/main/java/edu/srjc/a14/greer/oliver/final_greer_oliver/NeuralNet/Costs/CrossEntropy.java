package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Costs;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Cross entropy is a difference of logs that is used mostly for classification.
    A correct guess contributes 0 to the cost, resulting in faster convergence.
*/

public class CrossEntropy implements ICostFunction
{
    @Override
    public double calculateCost(double[] predictedOutputs, double[] expectedOutputs)
    {
        double cost = 0;
        for(int output = 0; output < predictedOutputs.length; output++)
        {
            double result;
            if(expectedOutputs[output] == 1)
            {
                result = -Math.log(predictedOutputs[output]);
            }
            else
            {
                result = -Math.log(1 - predictedOutputs[output]);
            }
            //test for valid log
            if(Double.isNaN(result))
            {
                cost += 0;
            }
            else
            {
                cost += result;
            }
        }
        return cost;
    }

    @Override
    public double calculateCostDerivative(double predictedOutput, double expectedOutput)
    {
        if(predictedOutput == 0 || predictedOutput == 1)
        {
            return 0;
        }

        return ((1 - expectedOutput) / (1 - predictedOutput)) - (expectedOutput / predictedOutput);
    }
}
