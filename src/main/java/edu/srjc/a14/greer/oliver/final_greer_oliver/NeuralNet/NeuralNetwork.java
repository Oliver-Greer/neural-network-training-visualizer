package edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Simple pass forward neural network. Complete implementation of back propagation and gradient descent.
*/

import edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling.DataPoint;
import edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Activations.IActivationFunction;
import edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Costs.ICostFunction;
import java.util.List;

public class NeuralNetwork
{
    private final Layer[] layers;

    public NeuralNetwork(int[] sizeOfLayers)
    {
        layers = new Layer[sizeOfLayers.length - 1];
        for (int layer = 0; layer < layers.length; layer++)
        {
            layers[layer] = new Layer(sizeOfLayers[layer], sizeOfLayers[layer + 1]);
        }
    }

    public void learn(List<DataPoint> trainingData, double learnRate)
    {
        for(DataPoint point : trainingData)
        {
            updateGradients(point);
        }

        for(Layer layer : layers)
        {
            layer.applyGradients(learnRate / trainingData.size());
            layer.clearGradients();
        }
    }

    //Back propagation algorithm
    private void updateGradients(DataPoint point)
    {
        //run a data point through the network to store inputs, weighted inputs, and activations
        Layer outputLayer = layers[layers.length - 1];
        double[] inputs = new double[]{point.getX(), point.getY(), point.getZ()};
        double[] expectedOutputs = point.getRegionNumberArray(outputLayer.getOutNodeCount());
        computeOutputs(inputs);

        //compute the stored derivatives for the output layer
        double[] storedDerivatives = outputLayer.computeOutputLayerStoredDerivatives(expectedOutputs);
        outputLayer.updateGradients(storedDerivatives);

        //compute stored derivatives for the hidden layers going backwards
        for(int hiddenLayerIndex = layers.length - 2; hiddenLayerIndex >= 0; hiddenLayerIndex--)
        {
            Layer hiddenLayer = layers[hiddenLayerIndex];
            storedDerivatives = hiddenLayer.computeHiddenLayerStoredDerivatives(layers[hiddenLayerIndex + 1], storedDerivatives);
            hiddenLayer.updateGradients(storedDerivatives);
        }
    }

    public int classify(double[] inputs)
    {
        double[] outputs = computeOutputs(inputs);
        return maxOutputIndex(outputs);
    }

    private double[] computeOutputs(double[] inputs)
    {
        for(Layer layer : layers)
        {
            inputs = layer.computeOutputs(inputs);
        }
        return inputs;
    }

    private int maxOutputIndex(double[] outputs)
    {
        double maxOutput = outputs[0];
        int maxIndex = 0;
        for(int outputIndex = 1; outputIndex < outputs.length; outputIndex++)
        {
            if(outputs[outputIndex] > maxOutput)
            {
                maxOutput = outputs[outputIndex];
                maxIndex = outputIndex;
            }
        }
        return maxIndex;
    }

    public void setCostFunction(ICostFunction costFunction)
    {
        //only the last layer needs a cost function
        layers[layers.length - 1].setCostFunction(costFunction);
    }

    public void setOutputActivationFunction(IActivationFunction outputActivationFunction)
    {
        layers[layers.length - 1].setActivationFunction(outputActivationFunction);
    }

    public void setHiddenActivationFunction(IActivationFunction hiddenActivationFunction)
    {
        for(int layer = 0; layer < layers.length - 1; layer++)
        {
            layers[layer].setActivationFunction(hiddenActivationFunction);
        }
    }
}
