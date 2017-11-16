package uk.ac.aber.dcs.blockmotion.transformation;

import uk.ac.aber.dcs.blockmotion.model.IFrame;
import uk.ac.aber.dcs.blockmotion.model.Transformer;

/**
 * @author moc17
 * @version 2017-05-08.
 */
public class SlideX extends Slide implements Transformer {

    /**
     * gets the slideValue from when it was called. Creates a SlideX
     * @param slideValue the number of pixels it will slide by
     * @throws IllegalArgumentException
     */
    public SlideX(int slideValue) throws IllegalArgumentException {
        super(slideValue);
    }

    /**
     * slides the frame in the x direction
     * @param frame the frame to transformation
     * @throws IllegalArgumentException
     */
    public void transform(IFrame frame) throws IllegalArgumentException{
        slide(frame, slideValue, false);
    }
}
