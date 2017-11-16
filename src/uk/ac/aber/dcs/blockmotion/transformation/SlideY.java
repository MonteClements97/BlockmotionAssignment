package uk.ac.aber.dcs.blockmotion.transformation;

import uk.ac.aber.dcs.blockmotion.model.IFrame;
import uk.ac.aber.dcs.blockmotion.model.Transformer;

/**
 * @author moc17
 * @version 2017-05-08.
 */
public class SlideY extends Slide implements Transformer {

    /**
     * gets the slide value from where it was called. creates a SlideY
     * @param slideValue the number of pixels it will slide by
     * @throws IllegalArgumentException
     */
    public SlideY(int slideValue) throws IllegalArgumentException {
        super(slideValue);
    }

    /**
     * slides in the Y axis
     * @param frame the frame to transformation
     * @throws IllegalArgumentException
     */
    public void transform(IFrame frame) throws IllegalArgumentException {
        slide(frame, slideValue, true);
    }
}
