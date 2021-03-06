package uk.ac.aber.dcs.blockmotion.model;

/**
 * All transformer classes must transformation this interace
 * @author Chris Loftus
 * @version 1.0, 9th March 2017
 */
public interface Transformer {
    /**
     * Transforms the provided frame
     * @param frame the frame to transformation
     * @throws IllegalArgumentException if the frame is null.
     */
    public abstract void transform(IFrame frame);
}
