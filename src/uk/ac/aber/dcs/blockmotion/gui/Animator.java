package uk.ac.aber.dcs.blockmotion.gui;

/**
 * @author moc17
 * @version 2017-05-08.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.aber.dcs.blockmotion.model.CFootage;
import uk.ac.aber.dcs.blockmotion.model.Transform;
import uk.ac.aber.dcs.blockmotion.model.IFootage;
import uk.ac.aber.dcs.blockmotion.model.IFrame;
import java.io.IOException;
import java.util.Scanner;


public class Animator extends Application {

    private Button[][] gridArray;
    private boolean doRun;
    private IFootage footage;
    private Stage stage;
    private Scanner in;

    // You will need more instance variables

    public static void main(String[] args) {
        // This is how a javafx class is run.
        // This will cause the start method to run.
        // You don't need to change main.
        launch(args);
    }

    // This is the javafx main starting method. The JVM will run this
    // inside an object of this class (Animator). You do not need to
    // create that object yourself.
    @Override
    public void start(Stage primaryStage) {

        // The Stage is where we place GUI stuff, such as a GridPane (see later).
        // I'll look more at this after Easter, but please read the
        // following comments
        stage = primaryStage;

        // In this version of the app we will drive
        // the app using a command line menu.
        // YOU ARE REQUIRED TO IMPLEMENT THIS METHOD
        footage = new CFootage();
        runMenu();

        // This is how we can handle a window close event. I will talk
        // about the strange syntax later (lambdas), but essentially it's a Java 8
        // was of providing a callback function: when someone clicks the
        // close window icon then this bit of code is run, passing in the event
        // object that represents the click event.
        primaryStage.setOnCloseRequest((event) -> {
            // Prevent window from closing. We only want quitting via
            // the command line menu.
            event.consume();

            System.out.println("\nPlease quit the application via the menu");
        });
    }

    private void runMenu() {
        // The GUI runs in its own thread of control. Here
        // we start by defining the function we want the thread
        // to call using that Java 8 lambda syntax. The Thread
        // constructor takes a Runnable
        Runnable commandLineTask = () -> {

            // YOUR MENU CODE GOES HERE
            String choice;
            String currentFile = null;
            in = new Scanner(System.in);
            do {
                System.out.println("Please enter a letter that corresponds with the action you desire. \nl - Load footage file\ns - Save footage file\nsa - Save as footage file\na - Run footage animation\nt - Stop footage animation\ne - Edit current footage\nq - Quit");
                System.out.println("Enter option: ");
                choice = in.nextLine().toLowerCase();
                switch (choice) {
                    case "l":
                        System.out.println("Name of the file: ");
                        choice = in.nextLine();

                        try {
                            footage.load(choice);
                            currentFile = choice; //file name is stored to be able to save easily
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(footage.getNumRows() != 0){
                            createGrid(); //if the file is not able to load, it will not create a grid
                        }
                        break;
                    case "s":
                        System.out.println("SAVE FUNCTION");
                        if (currentFile == null) {
                            System.err.println("NO FOOTAGE TO SAVE");
                            break;
                        } else {
                            try {
                                footage.save(currentFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "sa":
                        System.out.println("SAVE AS FUNCTION");
                        System.out.println("Please enter the name of the file you wish to save as: ");
                        choice = in.nextLine() + ".txt";
                        try {
                            footage.save(choice);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "a":
                        if (footage.getNumFrames() == 0){
                            System.err.println("NO FRAMES TO RUN");
                            break;
                        }
                        if (doRun){
                            System.err.println("THE ANIMATION IS ALREADY RUNNING");
                            break;
                        }
                        System.out.println("RUN FUNCTION");
                        runAnimation();
                        break;
                    case "t":
                        if (!doRun){
                            System.err.println("ANIMATION IS NOT RUNNING");
                            break;
                        }
                        System.out.println("STOP FUNCTION");
                        terminateAnimation();
                        break;
                    case "e":
                        if(footage.getNumRows() == 0){
                            System.err.println("NO FOOTAGE TO EDIT");
                            break;
                        }
                        else{
                            System.out.println("EDIT FUNCTION");
                            Transform transform = new Transform(footage);
                            break;
                        }

                    case "q":
                        System.out.println("Au revoir");
                        Platform.exit();
                        System.exit(0);

                    default:
                        System.err.println("INVALID CHOICE");

                }
            } while (!choice.equals("q")); //until the user wishes to quit
            // At some point you will call createGrid.
            // Here's an example

        };
        Thread commandLineThread = new Thread(commandLineTask);
        // This is how we start the thread.
        // This causes the run method to execute.
        commandLineThread.start();

        // You can stop javafx with the command
        // Platform.exit();
    }

    // An example method that you might like to call from your
    // menu. Whenever you need to do something in the GUI thread
    // from another thread you have to call Platform.runLater
    // This is a javafx method that queues your code ready for the GUI
    // thread to run when it is ready. We use that strange lambda Java 8 syntax
    // again although this time there are no parameters hence ()
    private void terminateAnimation() {
        doRun = false;
    }

    // Here is another example. This one is useful because it creates
    // the GUI grid. You will need to call this from the menu, e.g. when starting
    // an animation.
    private void createGrid() {
        Platform.runLater(() -> {

            // Update UI here
            //createGrid(numRows);   // E.g. let's create a 20 x 20 grid
            createGrid(footage.getNumRows());
        });
    }

    // I'll give you this method since I haven't done
    // much on javafx. This also creates a scene and adds it to the stage;
    // all very theatrical.
    private void createGrid(int numRows) {
        // Creates a grid so that we can display the animation. We will see
        // other layout panes in the lectures.
        GridPane grid = new GridPane();

        // We need to create a 2D array of javafx Buttons that we will
        // add to the grid. The 2D array makes it much easier to change
        // the colour of the buttons in the grid; easy lookup using
        // 2D indicies. Note that we make this read-only for this display
        // onlt grid. If you go for flair marks, then I'd imagine that you
        // could create something similar that allows you to edits frames
        // in the footage.
        gridArray = new Button[numRows][numRows];
        Button displayButton = null;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numRows; col++) {  // The display is square
                displayButton = new Button();
                gridArray[row][col] = displayButton;
                displayButton.setDisable(true);
                grid.add(displayButton, col, row);
            }
        }

        // Create a scene to hold the grid of buttons
        // The stage will "shrink wrap" around the grid of buttons,
        // so we don't need to set its height and width.
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        scene.getStylesheets().add(Animator.class.getResource("styling.css").toExternalForm());

        // Make it resizable so that the window shrinks to fit the scene grid
        stage.setResizable(true);
        stage.sizeToScene();
        // Raise the curtain on the stage!
        stage.show();
        // Stop the user from resizing the window
        stage.setResizable(false);
    }

    // I'll also give you this one too. This does the animation and sets colours for
    // the grid buttons. You will have to call this from the menu. You should not
    // need to change this code, unless you want to add more colours
    private void runAnimation() {
        if (footage == null) {
            System.out.println("Load the footage first");
        } else {
            Runnable animationToRun = () -> {

                Background yellowBg = new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY));
                Background blackBg = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
                Background blueBg = new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY));
                Background whiteBg = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

                doRun = true;
                int numFrames = footage.getNumFrames();
                int currentFrameIndex = 0;
                Background bck = null;
                while (doRun) {
                    if (currentFrameIndex >= numFrames) currentFrameIndex = 0;
                    IFrame currentFrame = footage.getFrame(currentFrameIndex);
                    // Iterate through the current frame displaying appropriate colour
                    for (int row = 0; row < footage.getNumRows(); row++) {
                        for (int col = 0; col < footage.getNumRows(); col++) {
                            switch (currentFrame.getChar(row, col)) {
                                case 'l':
                                    bck = yellowBg;
                                    break;
                                case 'r':
                                    bck = blackBg;
                                    break;
                                case 'b':
                                    bck = blueBg;
                                    break;
                                default:
                                    bck = whiteBg;
                            }
                            final int theRow = row;
                            final int theCol = col;
                            final Background backgroundColour = bck;
                            // This is another lambda callback. When the Platform
                            // GUI thread is ready it will run this code.
                            Platform.runLater(() -> {

                                // Update UI here
                                // We have to make this request on a queue that the GUI thread
                                // will run when ready.
                                gridArray[theRow][theCol].setBackground(backgroundColour);
                            });

                        }
                    }
                    try {
                        // This is how we delay for 200th of a second
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    currentFrameIndex++;
                }
            };
            Thread animation = new Thread(animationToRun);
            animation.start();
        }
    }
}

