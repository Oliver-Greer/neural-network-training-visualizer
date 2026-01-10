package edu.srjc.a14.greer.oliver.final_greer_oliver;

/*
Name: Oliver Greer
Email: ogreer@bearcubs.santarosa.edu
Date: 2024.12.20
Project Name: Final_Greer_Oliver
Course: CS17.11
Description:
    The FXML controller for a neural network training sim. Handles settings, data generation, and network learning logic.
    Please forgive the absolute mess this project is. I was planning on a huge refactor, but I got too busy just
    getting it to work.
*/

import edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling.DataPoint;
import edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling.DataPointArrayList;
import edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling.Point;
import edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Activations.ActivationRetriever;
import edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.Costs.CostRetriever;
import edu.srjc.a14.greer.oliver.final_greer_oliver.NeuralNet.NeuralNetwork;
import edu.srjc.a14.greer.oliver.final_greer_oliver.Objects3D.DataPointSphere;
import edu.srjc.a14.greer.oliver.final_greer_oliver.Objects3D.Automatic3DBox;
import edu.srjc.a14.greer.oliver.final_greer_oliver.Objects3D.Root3D;
import edu.srjc.a14.greer.oliver.final_greer_oliver.Utilities.MathUtils;
import edu.srjc.a14.greer.oliver.final_greer_oliver.Utilities.UiUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class A14_Greer_Oliver_Controller implements Initializable
{
    //region variables
    @FXML
    Text txtClassificationAccuracy;
    @FXML
    TextField txtNodeCounts;
    @FXML
    ChoiceBox<String> chBoxHiddenActivationFunctions;
    @FXML
    ChoiceBox<String> chBoxOutputActivationFunctions;
    @FXML
    ChoiceBox<String> chBoxCostFunctions;
    @FXML
    Slider sliLearnRate;
    @FXML
    Slider sliBatchSize;
    @FXML
    Slider sliTrainingSplit;
    @FXML
    Slider sliNumberOfCategories;
    @FXML
    Slider sliDataPerCategory;
    @FXML
    SubScene subScene3D;

    private final int fractionOfDataToShow = 10;
    private final int dataDisplayScaleFactor = 6;
    private final double axisLength = 1000;
    private final double scrollSensitivity = 0.1;
    private final double maximumCameraZoom = 400;
    private final double minimumCameraZoom = 30;
    private final double cameraDragSensitivity = 0.3;

    private Root3D subSceneRoot3D;
    private PerspectiveCamera camera3D;

    private final Random random = new Random();

    private int numberOfCategories = 0;
    private int numberOfDataPoints = 0;
    private int miniBatchSize = 0;
    private int maxRandomXPos = 0;
    private int maxRandomYPos = 0;
    private int maxRandomZPos = 0;
    private double trainingSplit = 0;
    private double learnRate = 0;
    int[] nodeCounts = null;

    private Color[] dataRegionRandomColors;

    private final ArrayList<DataPoint> data = new ArrayList<DataPoint>();
    private List<DataPoint> trainingData = new ArrayList<DataPoint>();
    private List<DataPoint> validationData = new ArrayList<DataPoint>();

    private NeuralNetwork neuralNetwork;

    private final Timeline learningAnimationTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> learn()));

    private double currentMouseXPosition = 0;
    private double currentMouseYPosition = 0;
    private int currentMiniBatchIndex = 0;
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        subSceneRoot3D = new Root3D();

        update_Changes(null);
        addCostFunctions();
        addOutputActivations();
        addHiddenActivations();

        setUpAxes();
        setUpCamera();

        subScene3D.setRoot(subSceneRoot3D);
    }

    //region data_generation
    @FXML
    private void generate_Data()
    {
        //clear existing data
        data.clear();
        subSceneRoot3D.getChildren().removeIf(node -> node instanceof DataPointSphere);

        int dataSize = (numberOfDataPoints / numberOfCategories);

        //chop up space into regions
        double[] regionXBounds = new double[(int) (numberOfCategories * 2)];
        int xInterval = 0;
        for(int region = 0; region < numberOfCategories; region++)
        {
            regionXBounds[region * 2] = xInterval;
            xInterval += maxRandomXPos / numberOfCategories;
            regionXBounds[region * 2 + 1] = xInterval;
        }

        //pick data centers within those regions and generate the data randomly
        Random rng = new Random();
        for (int region = 0; region < numberOfCategories; region++)
        {
            Point locationCenter = new Point
                    (rng.nextDouble(regionXBounds[region * 2 + 1] - regionXBounds[region * 2]) + regionXBounds[region * 2],
                            rng.nextDouble(maxRandomYPos),
                            rng.nextDouble(maxRandomZPos));
            DataPointArrayList regionData = new DataPointArrayList
                    (dataSize, locationCenter, maxRandomXPos, maxRandomYPos, maxRandomZPos, region);
            regionData.generatePoints();

            //show every tenth piece of data
            Color newColor = Color.color(Math.random(), Math.random(), Math.random());
            dataRegionRandomColors[region] = newColor;
            PhongMaterial material = new PhongMaterial(newColor);
            for(int dataPoint = 0; dataPoint < regionData.size(); dataPoint++)
            {
                data.add(regionData.get(dataPoint));

                if(dataPoint % fractionOfDataToShow == 0)
                {
                    DataPointSphere sphere = new DataPointSphere(.1);
                    sphere.setMaterial(material);
                    sphere.setTranslateX(regionData.get(dataPoint).getX());
                    sphere.setTranslateY(-regionData.get(dataPoint).getY());
                    sphere.setTranslateZ(-regionData.get(dataPoint).getZ());
                    subSceneRoot3D.getChildren().add(sphere);
                }
            }
        }

        Collections.shuffle(data);
        int trainingSize = (int) (data.size() * trainingSplit);
        trainingData = data.subList(0, trainingSize);
        validationData = data.subList(trainingSize, data.size());
    }
    //endregion

    //region setup_3D_environment
    private void setUpAxes()
    {
        Cylinder yAxis = new Cylinder(0.1, axisLength);
        Cylinder xAxis = new Cylinder(0.1, axisLength);
        xAxis.setRotationAxis(Rotate.Z_AXIS);
        xAxis.setRotate(90);
        Cylinder zAxis = new Cylinder(0.1, axisLength);
        zAxis.setRotationAxis(Rotate.X_AXIS);
        zAxis.setRotate(90);
        subSceneRoot3D.getChildren().add(yAxis);
        subSceneRoot3D.getChildren().add(xAxis);
        subSceneRoot3D.getChildren().add(zAxis);
    }

    private void setUpCamera()
    {
        camera3D = new PerspectiveCamera(true);
        camera3D.setFarClip(1000);
        camera3D.setNearClip(0.1);
        camera3D.setTranslateZ(-(double) (maxRandomZPos + 50));
        camera3D.setTranslateY((double) -1 * maxRandomYPos / 3);
        subScene3D.setCamera(camera3D);
    }
    //endregion

    //region fxml_events
    @FXML
    private void scroll_Camera(ScrollEvent scrollEvent)
    {
        float newScrolledZPosition = (float) Math.clamp
                (camera3D.getTranslateZ() + scrollEvent.getDeltaY() * scrollSensitivity, -maximumCameraZoom, -minimumCameraZoom);
        camera3D.setTranslateZ(newScrolledZPosition);
    }

    @FXML
    private void rotate_Camera(MouseEvent mouseEvent)
    {
        double mouseOldX = currentMouseXPosition;
        double mouseOldY = currentMouseYPosition;
        currentMouseXPosition = mouseEvent.getScreenX();
        currentMouseYPosition = mouseEvent.getScreenY();
        double mouseDeltaX = Math.clamp(currentMouseXPosition - mouseOldX, -cameraDragSensitivity, cameraDragSensitivity);
        double mouseDeltaY = Math.clamp(currentMouseYPosition - mouseOldY, -cameraDragSensitivity, cameraDragSensitivity);

        subSceneRoot3D.getYRotationTransform().setAngle(subSceneRoot3D.getYRotationTransform().getAngle() - mouseDeltaX);
        subSceneRoot3D.getXRotationTransform().setAngle(subSceneRoot3D.getXRotationTransform().getAngle() + mouseDeltaY);
    }

    @FXML
    private void stop_Learning()
    {
        learningAnimationTimeline.pause();
    }

    @FXML
    private void make_Network_Learn()
    {
        learningAnimationTimeline.setCycleCount(Timeline.INDEFINITE);
        learningAnimationTimeline.play();
    }

    @FXML
    private void close_Application(ActionEvent actionEvent)
    {
        System.exit(1);
    }

    @FXML
    private void update_Changes(ActionEvent actionEvent)
    {
        int[] newNodeCounts;
        try
        {
            newNodeCounts = parseNodeCounts();
        }
        catch(Exception e)
        {
            UiUtils.messageBox("Node counts format issue! Changes not applied!", Alert.AlertType.ERROR);
            return;
        }

        trainingSplit = sliTrainingSplit.getValue() / 100;
        miniBatchSize = (int) sliBatchSize.getValue();

        //if this field has changed we need to regenerate data and update the network
        if(numberOfCategories != (int) sliNumberOfCategories.getValue())
        {
            numberOfCategories = (int) sliNumberOfCategories.getValue();
            numberOfDataPoints = (int) sliDataPerCategory.getValue() * numberOfCategories;
            maxRandomXPos = dataDisplayScaleFactor * numberOfCategories;
            maxRandomYPos = dataDisplayScaleFactor * numberOfCategories;
            maxRandomZPos = dataDisplayScaleFactor * numberOfCategories;
            dataRegionRandomColors = new Color[numberOfCategories];
            generate_Data();
            nodeCounts = parseNodeCounts();
            neuralNetwork = new NeuralNetwork(nodeCounts);
        }
        if(numberOfDataPoints != (int) sliDataPerCategory.getValue() * numberOfCategories)
        {
            numberOfDataPoints = (int) sliDataPerCategory.getValue() * numberOfCategories;
            generate_Data();
        }

        //if this field has changed with need to update the network
        if(!Arrays.equals(nodeCounts, newNodeCounts))
        {
            nodeCounts = parseNodeCounts();
            neuralNetwork = new NeuralNetwork(nodeCounts);
        }

        neuralNetwork.setHiddenActivationFunction(ActivationRetriever.getActivationFunction((String) chBoxHiddenActivationFunctions.getValue()));
        neuralNetwork.setOutputActivationFunction(ActivationRetriever.getActivationFunction((String) chBoxOutputActivationFunctions.getValue()));
        neuralNetwork.setCostFunction(CostRetriever.getCostFunction((String) chBoxCostFunctions.getValue()));

        learnRate = sliLearnRate.getValue();
    }
    //endregion

    //region activation_&_cost_adding
    private void addHiddenActivations()
    {
        chBoxHiddenActivationFunctions.getItems().addAll("LReLU", "ReLU", "Sigmoid", "Tanh", "Softmax");
        chBoxHiddenActivationFunctions.setValue(ActivationRetriever.defaultActivation);
    }

    private void addOutputActivations()
    {
        chBoxOutputActivationFunctions.getItems().addAll("Sigmoid", "Tanh", "Softmax");
        chBoxOutputActivationFunctions.setValue(ActivationRetriever.defaultActivation);
    }

    private void addCostFunctions()
    {
        chBoxCostFunctions.getItems().addAll("MeanSquareRoot", "CrossEntropy");
        chBoxCostFunctions.setValue(CostRetriever.defaultCost);
    }
    //endregion

    private void learn()
    {
        Automatic3DBox[] regionMeshes = new Automatic3DBox[numberOfCategories];

        ArrayList<ArrayList<Point>> regionPoints = new ArrayList<>();
        for(int i = 0; i < numberOfCategories; i++)
        {
            regionPoints.add(new ArrayList<>());
        }

        float successes = 0;

        subSceneRoot3D.getChildren().removeIf(node -> node instanceof Box);

        //if a training epoch has ended reset the batch index to 0
        if(currentMiniBatchIndex >= trainingData.size())
        {
            Collections.shuffle(trainingData);
            currentMiniBatchIndex = 0;
        }
        currentMiniBatchIndex = Math.clamp(currentMiniBatchIndex, 0, trainingData.size() - miniBatchSize - 1);
        List<DataPoint> trainingDataBatch = trainingData.subList(currentMiniBatchIndex, currentMiniBatchIndex + miniBatchSize);
        currentMiniBatchIndex += miniBatchSize;

        neuralNetwork.learn(trainingDataBatch, learnRate);


        //Test Data
        for (DataPoint dataPoint : validationData)
        {
            int region = neuralNetwork.classify(new double[]{dataPoint.getX(), dataPoint.getY(), dataPoint.getZ()});
            if (region == dataPoint.getRegionNumber())
            {
                successes++;
            }
            regionPoints.get(region).add(new Point(dataPoint.getX(), dataPoint.getY(), dataPoint.getZ()));
        }

        //Draw Boxes Around Data
        for (int r = 0; r < numberOfCategories; r++)
        {
            regionMeshes[r] = new Automatic3DBox(regionPoints.get(r));
            Color regionColor = new Color
                    (dataRegionRandomColors[r].getRed(),
                            dataRegionRandomColors[r].getGreen(),
                            dataRegionRandomColors[r].getBlue(),
                            .000001);
            PhongMaterial meshMaterial = new PhongMaterial(regionColor);
            Box[] meshBoxes = regionMeshes[r].makeBox(meshMaterial);
            for (Box meshBox : meshBoxes)
            {
                subSceneRoot3D.getChildren().add(meshBox);
            }

            regionPoints.get(r).clear();
        }

        float successPercentage = successes / (float)validationData.size() * 100F;

        txtClassificationAccuracy.setText(String.format("Accuracy: %.1f%%", successPercentage));
    }

    private int[] parseNodeCounts()
    {
        //input layer node count = 3 because there are 3 axes, output node count = the number of categories
        String nodeCountInput = txtNodeCounts.getText();
        String[] nodeCountRawInput = nodeCountInput.split(",");

        int[] nodeCounts = new int[nodeCountRawInput.length + 2];
        nodeCounts[0] = 3;

        for(int hiddenNodeCount = 0; hiddenNodeCount < nodeCountRawInput.length; hiddenNodeCount++)
        {
            int nodeCount = MathUtils.tryParse(nodeCountRawInput[hiddenNodeCount].trim());
            if(nodeCount == 0)
            {
                throw new InputMismatchException();
            }

            nodeCounts[hiddenNodeCount+1] = nodeCount;
        }
        nodeCounts[nodeCounts.length-1] = numberOfCategories;

        return nodeCounts;
    }
}