package edu.srjc.a14.greer.oliver.final_greer_oliver.Objects3D;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    This is the root of all 3D objects in the sub scene. This is important so that every object can be rotated easily.
*/

import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public class Root3D extends Group
{
    private final Rotate xRotationTransform = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
    private final Rotate yRotationTransform = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);

    public Root3D()
    {
        super();
        this.getTransforms().addAll(this.xRotationTransform, this.yRotationTransform);
    }

    public Rotate getXRotationTransform()
    {
        return xRotationTransform;
    }

    public Rotate getYRotationTransform()
    {
        return yRotationTransform;
    }
}
