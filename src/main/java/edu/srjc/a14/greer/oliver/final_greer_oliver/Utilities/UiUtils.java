package edu.srjc.a14.greer.oliver.final_greer_oliver.Utilities;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    Provides quick popup windows for use within JavaFX.
*/

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class UiUtils
{
    public static ButtonType btnOK = new ButtonType("OK");
    public static ButtonType btnYes = new ButtonType("Yes");
    public static ButtonType btnNo = new ButtonType("No");

    /**
     * shows a messagebox with the specified AlertType
     * @param message
     * @param type
     */
    public static void messageBox(String message, Alert.AlertType type)
    {
        Alert alert = new Alert(type, message, btnOK);
        alert.setResizable(true);
        alert.showAndWait();
    }

    /**
     * shows a messagebox with AlertType.None
     * @param message
     */
    public static void messageBox(String message)
    {
        Alert alert = new Alert(Alert.AlertType.NONE, message, btnOK);
        alert.setResizable(true);
        alert.showAndWait();
    }

    /**
     * displays an alert with Yes/No buttons
     * @param message
     * @return UiUtils.btnYes or UiUtils.btnNo
     */
    public static ButtonType yesNoAnswerBox(String message)
    {
        Alert dlg = new Alert(Alert.AlertType.INFORMATION, message, btnYes, btnNo);
        dlg.setContentText(message);
        dlg.setHeaderText(null);
        dlg.setResizable(true);

        Optional<ButtonType> result = dlg.showAndWait();
        return result.get();

    }
}
