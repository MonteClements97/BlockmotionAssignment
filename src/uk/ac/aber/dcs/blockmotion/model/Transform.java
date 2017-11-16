package uk.ac.aber.dcs.blockmotion.model;

import uk.ac.aber.dcs.blockmotion.transformation.FlipH;
import uk.ac.aber.dcs.blockmotion.transformation.FlipV;
import uk.ac.aber.dcs.blockmotion.transformation.SlideX;
import uk.ac.aber.dcs.blockmotion.transformation.SlideY;

import java.util.Scanner;

/**
 * @author moc17
 * @version 2017-05-08.
 */
public class Transform {

    private int slideLeftNumber = 1;
    private int slideRightNumber = 1;
    private int slideDownNumber = 1;
    private int slideUpNumber = 1;
    private Scanner in;
    private String lastSelect;


    /**
     * will ask the user which transformation they will like then call a function that will do that for them.
     * @param footage the footage they wish to edit
     * @throws IllegalArgumentException
     */
    public Transform(IFootage footage) throws IllegalArgumentException {
        in = new Scanner(System.in);
        String choice;
        do {
            runMenu();
            choice = in.nextLine().toLowerCase();
            optionChoosen(choice, footage);
        } while (!choice.equals("q")); //until the user wishes to quit
    }

    private void optionChoosen(String choice, IFootage footage) throws IllegalArgumentException {
        switch (choice) {
            case "fh":
                footage.transform(new FlipH());
                lastSelect = choice; //used for repeat
                break;
            case "fv":
                footage.transform(new FlipV());
                lastSelect = choice;
                break;
            case "sl":
                footage.transform(new SlideX(-slideLeftNumber));
                lastSelect = choice;
                break;
            case "sr":
                footage.transform(new SlideX(slideRightNumber));
                lastSelect = choice;
                break;
            case "su":
                footage.transform(new SlideY(-slideUpNumber));
                lastSelect = choice;
                break;
            case "sd":
                footage.transform(new SlideY(slideDownNumber));
                lastSelect = choice;
                break;
            case "nr":
                slideRightByNumber(choice, footage);
                break;
            case "nl":
                slideLeftByNumber(choice, footage);
                break;
            case "nd":
                slideDownByNumber(choice, footage);
                break;
            case "nu":
                slideUpByNumber(choice, footage);
                break;
            case "r":
                repeat(footage);
                break;
            case "q":
                System.out.println("Editing complete");
                break;
            default:
                System.err.println("NOT A VALID OPTION");
                break;
        }
    }




    private void slideUpByNumber(String choice, IFootage footage) {
        //checking whether the number is suitable for the animation and assigning it to the corresponding variable
        slideUpNumber = checkNumber(footage);
    }

    private void slideDownByNumber(String choice, IFootage footage) {

        slideDownNumber = checkNumber(footage);
    }


    private void slideLeftByNumber(String choice, IFootage footage) {

        slideLeftNumber = checkNumber(footage);
    }

    private void slideRightByNumber(String choice, IFootage footage) {

        slideRightNumber = checkNumber(footage);
    }

    private void runMenu() {
        System.out.println("Please select a transformation option\n" +
                "fh - Flip horizontal\n" +
                "fv - Flip vertical\n" +
                "sl - Slide left\n" +
                "sr - Slide right\n" +
                "su - Slide up\n" +
                "sd - Slide down\n" +
                "nr - Slide right number. Currently= " + slideRightNumber + "\n" +
                "nl - Slide left number. Currently= " + slideLeftNumber + "\n" +
                "nd - Slide down number. Currently= " + slideDownNumber + "\n" +
                "nu - Slide up number. Currently= " + slideUpNumber + "\n" +
                "r - Repeat last operation\n" +
                "q - Quit editing");
        System.out.println("Enter option: ");

    }

    private void repeat(IFootage footage) {
        if (lastSelect == null) {
            System.err.println("NOTHING WAS PREVIOUSLY SELECTED"); //checking if lastSelect has something within
            return;
        }
        optionChoosen(lastSelect, footage); //calling the switch statement with what was last entered
    }

    private int checkNumber(IFootage footage) {
        int checkedNumber;
        do {


            System.out.println("Please choose the number you wish to use for slide: ");
            checkedNumber = in.nextInt();
            in.nextLine();
            if(checkedNumber >= footage.getNumRows()){
                System.err.println("NUMBER TOO HIGH"); //if the number is equal to or higher than the number of rows the function will crash
            }
            else if(checkedNumber <= 0){
                System.err.println("NUMBER TOO LOW"); //if the number is equal to or lower than 0 the function will crash
            }


        } while((checkedNumber >= footage.getNumRows()) || (checkedNumber <= 0)); //checking whether the user entered a suitable number
        return checkedNumber;
    }

}
