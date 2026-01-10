package edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Data point in 3D with X, Y, and Z coordinates as well as a region number. The region number is used to check
    neural network accuracy.
*/

public class DataPoint extends Point
{
    private int regionNumber = 0;

    public DataPoint(double x, double y, double z, int regionNumber)
    {
        super(x, y, z);
        this.regionNumber = regionNumber;
    }

    public double[] getRegionNumberArray(int outNodes)
    {
        double[] regionNumberArray = new double[outNodes];
        regionNumberArray[regionNumber] = 1.0;

        return regionNumberArray;
    }

    public int getRegionNumber()
    {
        return regionNumber;
    }
}
