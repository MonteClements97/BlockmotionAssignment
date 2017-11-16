package uk.ac.aber.dcs.blockmotion.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;


import java.io.IOException;

/**
 * @author moc17
 * @version 2017-05-08.
 */
public class CFootage implements IFootage {

    private Scanner inFile;
    private int rows;
    private int frames;
    private IFrame[] footage;




    public int getNumFrames() {
        if ((inFile == null) || (frames == 0)){
            System.err.println("NO FRAMES");
            return 0;
        }
        return frames;
    }

    public int getNumRows() {
        if ((inFile == null) || (rows == 0)) {
            System.err.println("NO ROWS");
            return 0;
        }
        return rows;
    }

    public IFrame getFrame(int num) throws IllegalArgumentException {
        if ((inFile == null) || (frames == 0)) {
            System.err.println("NO FRAMES");
            return footage[0];

        }
        return footage[num];
    }

    public void add(IFrame f) throws IllegalArgumentException {
        if(f.getNumRows() != getNumRows()){
            System.err.println("\nINCOMPATIBLE SIZE");
            return;
        }
        IFrame[] temp = footage;
        footage = new IFrame[frames + 1];
        for(int x = 0; x < frames; x++){
            footage[x] = temp[x];
        }
        footage[frames] = f;


    }

    public void load(String fn) throws IOException {
        inFile = new Scanner(new FileReader(fn));
        inFile.useDelimiter(":|\r?\n|\r");
        frames = inFile.nextInt();
        rows = inFile.nextInt();
        footage = new IFrame[frames];
        for (int z = 0; z < frames; z++){
            IFrame frame = new CFrame(rows);
            for (int y = 0; y < rows; y++){
                String p = inFile.next();
                char[] j = p.toCharArray(); //frames are stored as an array of chars, so strings must be broken down
                for (int x = 0; x < rows; x++){
                    frame.setChar(rows - 1 - y, x, j[x]);
                }


            } footage[z] = frame;
        }


        inFile.close();
    }

    public void save(String fn) throws IOException {
        PrintWriter outfile = new PrintWriter(new FileWriter(fn));
        outfile.println(frames);
        outfile.println(rows);
        for(int z = 0; z < frames; z++){
            for(int y = 0; y < rows; y++){
                for(int x = 0; x < rows; x++){
                    if (x == rows-1){
                        outfile.print(footage[z].getChar(rows -1 - y, x) + "\n"); //adds a new line when at the end of an array
                    }
                    else{
                        outfile.print(footage[z].getChar(rows -1 - y, x));
                    }
                }
            }

        } outfile.close();

    }

    public void transform(Transformer t) {
        for(int i = 0; i < frames; i++){
            t.transform(footage[i]);
        }
    }
}
