package edu.srjc.a14.greer.oliver.final_greer_oliver.Objects3D;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    A 3D box that automatically sizes based on an input of data points.
    Provides extra functionality for fancy visualization that I didn't end up using.
*/

import edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling.Point;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.ArrayList;
import java.util.List;

public class Automatic3DBox
{
    private final double[] greatestX;
    private final double[] greatestY;
    private final double[] greatestZ;
    private final double[] smallestX;
    private final double[] smallestY;
    private final double[] smallestZ;

    //mesh resolution is left over from testing, but I decided not to use it.
    //It creates an interesting effect by drawing more boxes and providing better visualization
    //Ended up being to computationally intensive to justify
    private final int meshResolution = 1;

    public Automatic3DBox(ArrayList<Point> points)
    {
        greatestX = new double[meshResolution];
        greatestY = new double[meshResolution];
        greatestZ = new double[meshResolution];
        smallestX = new double[meshResolution];
        smallestY = new double[meshResolution];
        smallestZ = new double[meshResolution];

        for(int i = 0; i < meshResolution; i++)
        {
            smallestX[i] = Double.MAX_VALUE;
            smallestY[i] = Double.MAX_VALUE;
            smallestZ[i] = Double.MAX_VALUE;
        }

        //get the smallest and largest coordinates for each box
        ArrayList<List<Point>> splitPoints = new ArrayList<>(meshResolution);
        int startPoint = 0;
        for(int i = 0; i < meshResolution; ++i)
        {
            splitPoints.add(points.subList(startPoint, startPoint + points.size() / meshResolution));
            startPoint += points.size() / meshResolution;

            for(int j = 0; j < splitPoints.get(i).size(); ++j)
            {
                Point pnt = splitPoints.get(i).get(j);
                if(pnt.getX() > greatestX[i])
                {
                    greatestX[i] = pnt.getX();
                }
                if(pnt.getY() > greatestY[i])
                {
                    greatestY[i] = pnt.getY();
                }
                if(pnt.getZ() > greatestZ[i])
                {
                    greatestZ[i] = pnt.getZ();
                }
                if(pnt.getX() < smallestX[i])
                {
                    smallestX[i] = pnt.getX();
                }
                if(pnt.getY() < smallestY[i])
                {
                    smallestY[i] = pnt.getY();
                }
                if(pnt.getZ() < smallestZ[i])
                {
                    smallestZ[i] = pnt.getZ();
                }
            }
        }
    }

    public Box[] makeBox(PhongMaterial material)
    {
        Box[] boxes = new Box[meshResolution];

        for(int i = 0; i < meshResolution; ++i)
        {
            double width = (greatestX[i] - smallestX[i]);
            double height = (greatestY[i] - smallestY[i]);
            double depth = (greatestZ[i] - smallestZ[i]);

            Point center = new Point(smallestX[i] + width / 2, smallestY[i] + height / 2, smallestZ[i] + depth / 2);
            Box meshBox = new Box(width, height, depth);
            meshBox.setTranslateX(center.getX());
            meshBox.setTranslateY(-center.getY());
            meshBox.setTranslateZ(-center.getZ());
            meshBox.setMaterial(material);

            boxes[i] = meshBox;
        }

        return boxes;
    }
}
