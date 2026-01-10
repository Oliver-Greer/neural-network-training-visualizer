package edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    An ArrayList with extra support for handling Data Point operations. Supports weighted randomized generation.
*/

import java.util.ArrayList;
import java.util.Random;

public class DataPointArrayList extends ArrayList<DataPoint>
{
    private int numberOfPoints = 0;
    private final double[] randomBoundWeights = new double[]{0.05, 0.123, 0.231, 0.274, 0.322};
    private final double[] possibleBounds = new double[5];
    private int maxX = 0;
    private int maxY = 0;
    private int maxZ = 0;
    private Point centerOfDataPoints = null;
    private int regionNumber;

    public DataPointArrayList()
    {
        super();
    }

    public DataPointArrayList
            (int numberOfPoints, Point center, int maxRandomXPos, int maxRandomYPos, int maxRandomZPos, int regionNumber)
    {
        super();
        this.numberOfPoints = numberOfPoints;
        this.centerOfDataPoints = center;
        this.regionNumber = regionNumber;
        maxX = maxRandomXPos;
        maxY = maxRandomYPos;
        maxZ = maxRandomZPos;
        for(int i = 0; i < possibleBounds.length; i++)
        {
            //weights make it more likely that a point will be generated close to the probable location
            possibleBounds[i] = (10 * randomBoundWeights[randomBoundWeights.length - i - 1]);
        }
    }

    public void generatePoints()
    {
        Random rng = new Random();
        for (int p = 0; p < numberOfPoints; p++)
        {
            DataPoint point = getRandomWeightedLocation(rng);
            add(point);
        }
    }

    private DataPoint getRandomWeightedLocation(Random rng)
    {
        double upperBound = getUpperBound();
        double xCoordinate = Math.clamp(centerOfDataPoints.getX() + rng.nextDouble(upperBound + upperBound) - upperBound, 0, maxX);
        upperBound = getUpperBound();
        double yCoordinate = Math.clamp(centerOfDataPoints.getY() + rng.nextDouble(upperBound + upperBound) - upperBound, 0, maxY);
        upperBound = getUpperBound();
        double zCoordinate = Math.clamp(centerOfDataPoints.getZ() + rng.nextDouble(upperBound + upperBound) - upperBound, 0, maxZ);

        return new DataPoint(xCoordinate, yCoordinate, zCoordinate, regionNumber);
    }

    private double getUpperBound()
    {
        //simple weighted randomizing
        int index = 0;
        for (double r = Math.random(); index < possibleBounds.length; index++)
        {
            r -= randomBoundWeights[index];
            if(r <= 0.0)
            {
                break;
            }
        }
        return possibleBounds[index];
    }
}
