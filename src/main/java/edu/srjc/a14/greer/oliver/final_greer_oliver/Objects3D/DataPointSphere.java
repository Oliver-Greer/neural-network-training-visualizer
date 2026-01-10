package edu.srjc.a14.greer.oliver.final_greer_oliver.Objects3D;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    The same as a JavaFX sphere.
    This is a separate class so that when the controller removes all data spheres it doesn't
    remove other types of spheres. Almost certainly a better way to do this.
*/

import javafx.scene.shape.Sphere;

public class DataPointSphere extends Sphere
{
    public DataPointSphere()
    {
        super();
    }

    public DataPointSphere(double radius)
    {
        super(radius);
    }
}
