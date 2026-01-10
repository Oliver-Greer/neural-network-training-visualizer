/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Lets us parse Integers without throwing an exception.
*/

package edu.srjc.a14.greer.oliver.final_greer_oliver.Utilities;

public class MathUtils
{
    public static int tryParse(String value)
    {
        int returnValue = 0;
        try
        {
            returnValue = Integer.parseInt(value);
            return returnValue;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}
