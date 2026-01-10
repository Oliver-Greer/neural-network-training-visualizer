package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Class that represents a layer within a pass forward neural network. Initializes weights in a normalized distribution.
    Stores inputs at each computation stage for backpropagation. Provides functions for calculating and updating
    gradients.
*/

import edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Activations.IActivationFunction;
import edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Costs.ICostFunction;
import java.util.Random;

public class Layer
{
    private ICostFunction costFunction;
    private IActivationFunction activationFunction;

    private final int inNodeCount;
    private final int outNodeCount;

    private double[][] costWithRespectToWeightGradients;
    private double[] costWithRespectToBiasGradients;
    private double[] weightedInputs;
    private double[] activatedInputs;

    private double[][] weights;
    private double[] biases;
    private double[] inputs;

    public Layer(int inNodes, int outNodes)
    {
        this.inNodeCount = inNodes;
        this.outNodeCount = outNodes;

        weights = new double[inNodes][outNodes];
        biases = new double[outNodes];
        costWithRespectToWeightGradients = new double[inNodes][outNodes];
        costWithRespectToBiasGradients = new double[outNodes];
        weightedInputs = new double[outNodes];
        activatedInputs = new double[outNodes];
        inputs = new double[inNodes];

        initializeRandomizedWeights();
    }

    //gradient descent
    public void applyGradients(double learnRate)
    {
        for(int outNode = 0; outNode < outNodeCount; outNode++)
        {
            biases[outNode] -= costWithRespectToBiasGradients[outNode] * learnRate;
            for(int inNode = 0; inNode < inNodeCount; inNode++)
            {
                weights[inNode][outNode] -= costWithRespectToWeightGradients[inNode][outNode] * learnRate;
            }
        }
    }

    public void clearGradients()
    {
        costWithRespectToBiasGradients = new double[outNodeCount];
        costWithRespectToWeightGradients = new double[inNodeCount][outNodeCount];
    }

    public void updateGradients(double[] storedDerivatives)
    {
        for(int outNode = 0; outNode < outNodeCount; outNode++)
        {
            for(int inNode = 0; inNode < inNodeCount; inNode++)
            {
                double costWithRespectToWeight = inputs[inNode] * storedDerivatives[outNode];
                costWithRespectToWeightGradients[inNode][outNode] += costWithRespectToWeight;
            }
            //1 is shown here for clarity. The derivative of the weighted input with respect to the bias is 1
            costWithRespectToBiasGradients[outNode] += 1 * storedDerivatives[outNode];
        }
    }

    public double[] computeOutputLayerStoredDerivatives(double[] expectedOutputs)
    {
        double[] storedDerivatives = new double[expectedOutputs.length];

        for(int inNode = 0; inNode < storedDerivatives.length; inNode++)
        {
            //the chain rule
            double costDerivative = costFunction.calculateCostDerivative(activatedInputs[inNode], expectedOutputs[inNode]);
            double activationDerivative = activationFunction.activateDerivative(weightedInputs, inNode);
            storedDerivatives[inNode] = costDerivative * activationDerivative;
        }

        return storedDerivatives;
    }

    public double[] computeHiddenLayerStoredDerivatives(Layer nextLayer, double[] nextLayerStoredDerivatives)
    {
        double[] storedDerivatives = new double[outNodeCount];

        for(int thisNodeIndex = 0; thisNodeIndex < storedDerivatives.length; thisNodeIndex++)
        {
            //chain rule for a hidden layer
            double newNodeValue = 0;
            for(int nextNodeIndex = 0; nextNodeIndex < nextLayerStoredDerivatives.length; nextNodeIndex++)
            {
                double weightedInputDerivative = nextLayer.getWeights()[thisNodeIndex][nextNodeIndex];
                newNodeValue += weightedInputDerivative * nextLayerStoredDerivatives[nextNodeIndex];
            }
            newNodeValue *= activationFunction.activateDerivative(weightedInputs, thisNodeIndex);
            storedDerivatives[thisNodeIndex] = newNodeValue;
        }
        return storedDerivatives;
    }

    public double[] computeOutputs(double[] inputs)
    {
        this.inputs = inputs;
        for(int outNode = 0; outNode < outNodeCount; outNode++)
        {
            double inputWeighted = biases[outNode];
            for(int inNode = 0; inNode < inNodeCount; inNode++)
            {
                inputWeighted += inputs[inNode] * weights[inNode][outNode];
            }
            weightedInputs[outNode] = inputWeighted;
            activatedInputs[outNode] = activationFunction.activate(weightedInputs, outNode);
        }
        return activatedInputs;
    }

    private void initializeRandomizedWeights()
    {
        Random rng = new Random();

        for(int inNodes = 0; inNodes < inNodeCount; inNodes++)
        {
            for(int outNodes = 0; outNodes < outNodeCount; outNodes++)
            {
                weights[inNodes][outNodes] = rng.nextGaussian() / Math.sqrt(inNodeCount);
            }
        }
    }

    public void setCostFunction(ICostFunction costFunction)
    {
        this.costFunction = costFunction;
    }

    public void setActivationFunction(IActivationFunction activationFunction)
    {
        this.activationFunction = activationFunction;
    }

    public int getOutNodeCount()
    {
        return outNodeCount;
    }

    public double[][] getWeights()
    {
        return weights;
    }
}
