package uk.ac.aber.dcs.blockmotion.transformation;

import uk.ac.aber.dcs.blockmotion.model.IFrame;
import uk.ac.aber.dcs.blockmotion.model.Transformer;

/**
 * @author moc17
 * @version 2017-05-08.
 */
public class FlipH extends Flip implements Transformer {


    /**
     * flips the frame horizontally
     * @param frame the frame to transformation
     * @throws IllegalArgumentException
     */
    public void transform(IFrame frame) throws IllegalArgumentException {
        flip(frame, frame.getNumRows() -1 , 0);
    }
}
