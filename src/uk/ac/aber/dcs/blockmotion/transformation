package uk.ac.aber.dcs.blockmotion.transformation;

import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * @author moc17
 * @version 2017-05-08.
 */
public class Flip  {


    public void flip(IFrame frame, int i, int j) {
        IFrame temp = frame.copy();
        int numRows = frame.getNumRows();
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numRows; x++) {
                frame.setChar(y, x, temp.getChar(Math.abs(i - y), Math.abs(j - x))); //one of i or j will be 0, therefore an x or y will be counting down, so the absolute value stops that
            }
        }
    }
}
