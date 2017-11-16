package uk.ac.aber.dcs.blockmotion.transformation;

import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * @author moc17
 * @version 2017-05-08.
 */
public class Slide {

    int slideValue;

    /**
     * creates a slide. Used by both SlideX and SlideY
     * @param slideValue the number of pixels it will slide by
     * @throws IllegalArgumentException
     */
    public Slide(int slideValue) throws IllegalArgumentException {
        this.slideValue = slideValue;
    }

    /**
     * will slide the frame in either the y or x axis based on the parameters
     * @param frame the frame to transform
     * @param slideValue the number of pixels it will slide by
     * @param yAxis whether it is a slide in the y axis or not
     * @throws IllegalArgumentException
     */
    void slide(IFrame frame, int slideValue, boolean yAxis) throws IllegalArgumentException {
        IFrame temp = frame.copy();
        int numRows = frame.getNumRows();
        if(yAxis){
            for(int y = 0; y < numRows; y++){
                for(int x = 0; x < numRows; x++){
                    if(y + slideValue > numRows - 1){ //checking whether the number will exceed the arrays limit
                        int newY = (y + slideValue) - (numRows); //changing the number so it will be able to wrap around
                        frame.setChar(newY, x, temp.getChar(y, x));
                    } else if (y + slideValue < 0){ //checking whether the number will be negative
                        int newY = numRows - Math.abs(y + slideValue); //changing the number so it will be able to wrap around
                        frame.setChar(newY, x, temp.getChar(y, x));
                    }
                    else {
                        frame.setChar(y + slideValue, x, temp.getChar(y, x));
                    }

                }
            }
        } else {
            for(int y = 0; y < numRows; y++){
                for(int x = 0; x < numRows; x++){
                    if(x + slideValue > numRows - 1){ //checking whether the number will exceed the arrays limit
                        int newX = (x + slideValue) - (numRows); //changing the number so it will be able to wrap around
                        frame.setChar(y, newX, temp.getChar(y, x));
                    } else if (x + slideValue < 0){ //checking whether the number will be negative
                        int newX = numRows - Math.abs(x + slideValue); //changing the number so it will be able to wrap around
                        frame.setChar(y, newX, temp.getChar(y, x));
                    }
                    else {
                        frame.setChar(y, x + slideValue, temp.getChar(y, x));
                    }
                }
            }

        }
    }
}
