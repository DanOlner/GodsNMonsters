/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package godsnmonsters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Dan
 */
public class StaticArrayReadWrite {

    /*
     * Write 2D doubles to file
     */
    public static void write2DDoubleArray(double[][] vals, String fileName) {

        BufferedWriter writer;

        System.out.println("In static writer, writing " + fileName);

        try {

            //p.p(filenames[str]);
            writer = new BufferedWriter(new FileWriter("data/" + fileName));

            //Add one for column names
            for (int d = 0; d < vals.length; d++) {

//                    //write columns if we're at the start...
//                    if (d == 0) {
//                        //if it's set
//                        if (c.colnames != null) {
//
//                            for (String a : c.colnames) {
//
//                                writer.write(a + ",");
//
//                            }
//
//                        }
//                    } else {
                for (int f = 0; f < vals[1].length; f++) {

                    //System.out.println("f=" + f);
                    //if we've reached the end of the line - when f == entity number - don't use a comma after the value
                    //add column name if there is one, for first column
                    //System.out.println("Size of array: " + c.vals.length + "," + c.vals[0].length + "f=" + f + ", d="+d);
                    if (f == vals[1].length) {
                        writer.write(Double.toString(vals[d][f]));
                    } else {
                        writer.write(Double.toString(vals[d][f]) + ",");
                    }

                }

                //}
                //next line
                writer.write("\n");

            }

            writer.close();

        } catch (IOException ex) {
            System.out.println("couldn't write file: " + ex);
        }

    }//end method write2DDoubleArray

    /*
     * Write 2D doubles to file
     */
    public static void write2DArrayOfDoubles(ArrayList<Double[]> array, String fileName) {

        Double d;

        BufferedWriter writer;

        System.out.println("In static writer, writing " + fileName);

        try {

            //p.p(filenames[str]);
            writer = new BufferedWriter(new FileWriter(fileName));

            //Add one for column names
            for (int e = 0; e < array.size(); e++) {

//                    //write columns if we're at the start...
//                    if (d == 0) {
//                        //if it's set
//                        if (c.colnames != null) {
//
//                            for (String a : c.colnames) {
//
//                                writer.write(a + ",");
//
//                            }
//
//                        }
//                    } else {
                for (int f = 0; f < array.get(0).length; f++) {

                    //System.out.println("f=" + f);
                    d = (Double) array.get(e)[f];

                    //if we've reached the end of the line - when f == entity number - don't use a comma after the value
                    //add column name if there is one, for first column
                    //System.out.println("Size of array: " + c.vals.length + "," + c.vals[0].length + "f=" + f + ", d="+d);
                    if (f == array.get(0).length) {
                        writer.write(Double.toString(d));
                    } else {
                        writer.write(Double.toString(d) + ",");
                    }

                }

                //}
                //next line
                writer.write("\n");

            }

            writer.close();

        } catch (IOException ex) {
            System.out.println("couldn't write file: " + ex);
        }

    }//end method write2DDoubleArray

    /*
     * Write 2D floats to file
     */
    public static void writeArrayListOfFloatArrays(ArrayList<Float[]> array, String fileName) {

        Float d;

        BufferedWriter writer;

        System.out.println("In static writer, writing " + fileName);

        try {

            //p.p(filenames[str]);
            writer = new BufferedWriter(new FileWriter(fileName));

            for (int e = 0; e < array.size(); e++) {

                for (int f = 0; f < array.get(0).length; f++) {

                    //System.out.println("f=" + f);
                    d = (Float) array.get(e)[f];

                    //if we've reached the end of the line - when f == entity number - don't use a comma after the value
                    //add column name if there is one, for first column
                    //System.out.println("Size of array: " + c.vals.length + "," + c.vals[0].length + "f=" + f + ", d="+d);
                    if (f == array.get(0).length) {
                        writer.write(Double.toString(d));
                    } else {
                        writer.write(Double.toString(d) + ",");
                    }

                }

                //}
                //next line
                writer.write("\n");

            }

            writer.close();

        } catch (IOException ex) {
            System.out.println("couldn't write file: " + ex);
        }

    }//

    public static ArrayList<Float[]> read2DarrayOfFloats(String fileName) {

        ArrayList<Float[]> data = new ArrayList<Float[]>();
        
        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
//        List<String> lines = new ArrayList<>();
            String line;
            String[] cells;

            //ob number
//        int ob = 0;
            while ((line = reader.readLine()) != null) {

                cells = line.trim().split(",");
                
//                System.out.println("line length: " + cells.length);
                
                Float[] row = new Float[125];
                
                for (int i = 0; i < 125; i++) {
                    row[i] = Float.parseFloat(cells[i]);
                }

                data.add(row);

            }

        } catch (Exception e) {
            System.out.println("geo ah: " + e.getLocalizedMessage());
        }

        return data;
        
    }
}
