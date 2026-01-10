package edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Representation of a point in 3D with X, Y, and Z coordinates.
*/

public class Point
{
    private double[] coordinates = null;

    public Point(double x, double y, double z)
    {
        coordinates = new double[]{x, y, z};
    }

    public double getX()
    {
        return coordinates[0];
    }

    public void setX(double x)
    {
        coordinates[0] = x;
    }

    public double getY()
    {
        return coordinates[1];
    }

    public void setY(double y)
    {
        coordinates[1] = y;
    }

    public double getZ()
    {
        return coordinates[2];
    }

    public void setZ(double z)
    {
        coordinates[1] = z;
    }
}
