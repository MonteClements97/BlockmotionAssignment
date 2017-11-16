package uk.ac.aber.dcs.blockmotion.model;

import java.io.PrintWriter;

/**
 * @author moc17
 * @version 2017-05-08.
 */
public class CFrame implements IFrame {
    private int rows;
    private char[][] frame;

    /**
     * creates a frame rows equal to what is passed
     * @param rows
     */
    public CFrame(int rows){
            frame = new char[rows][rows];

    }

    public void insertLines(String[] lines) throws IllegalArgumentException {
        if (lines.length != rows){
            System.err.print("\nNOT THE SAME NUMBER OF ROWS");
        }
        else{
            for (int y = 0; y < rows; y++){
                char[] temp = lines[y].toCharArray(); //frame needs chars
                for (int x = 0; x < rows; x++){
                    frame[y][x] = temp[x]; //add each char to frame
                }
            }
        }
    }

    public int getNumRows() {
        return frame.length;
    }

    public void tofile(PrintWriter outfile) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < rows; x++) {
                if (rows - 1 == x) {
                    outfile.print(frame[y][x] + "\n"); //end of a line will need a \n
                } else {
                    outfile.print(frame[y][x]);
                }
            }
        }
    }

    public char getChar(int i, int j) throws IllegalArgumentException {
        return frame[i][j];
    }

    public void setChar(int i, int j, char ch) throws IllegalArgumentException {
        frame[i][j] = ch;
    }

    public IFrame copy() {
        rows = getNumRows();
        CFrame copyOfFrame = new CFrame(rows);
        for (int y = 0; y < rows; y++){
            for (int x = 0; x < rows; x++){
                copyOfFrame.setChar(y, x, frame[y][x]);
            }
        }
        return copyOfFrame;
    }

    public void replace(IFrame f) throws IllegalArgumentException {
        if(f.getNumRows() != rows){
            System.err.println("NOT THE SAME NUMBER OF ROWS");
            return;
        }
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < rows; x++){
                frame[y][x] = f.getChar(y, x);
            }
        }
    }
}
