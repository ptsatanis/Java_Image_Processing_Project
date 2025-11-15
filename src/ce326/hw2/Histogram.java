/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


/**
 *
 * @author Tsatanis Panagiotis
 */
public class Histogram {
    double[][] histogram;
    /*
        row 0 = luminocity
        row 1 = instance of a luminocity
    */
    
    public static final int LENGTH = 256;
    
    public Histogram(YUVImage img) {
        
        this.histogram = new double[2][256];
        
        for(int i = 0; i < LENGTH; i++) {
            this.histogram[0][i] = (short) img.getInstanciesOfPixel((short) i);
            this.histogram[1][i] = this.histogram[0][i];
        }
        
    }
    
    @Override
    public String toString() {
        String Contents = new String();
        
        for(int i = 0; i < 236; i++) {
            
            Integer Num = (int) this.histogram[0][i];
            String S = Num.toString();
            
            Contents += ( "\n");
            
            if( i < 10 ) {
                Contents += "  ";
            }
            else if( i < 100 ){
               Contents += " "; 
            }
            Contents += ( i  + ".(" ) ;
            
            for(int j = 0; j < ( 4 - S.length() ); j++) {
                Contents += " ";
            }
            
            Contents += ( Num.toString() +  ")\t" );
            
            
            if( S.length() == 4 ) {
                for(int j = 0; j <  Character.getNumericValue(S.charAt(0)) ; j++) {
                    Contents += "#";
                }
            }
            
            if( S.length() >= 3 ) {
                
                for(int j = 0; j < Character.getNumericValue(S.charAt( S.length() - 3 )) ; j++) {
                    Contents += "$";
                }
            }
            
            if( S.length() >= 2 ) {
                for(int j = 0; j < Character.getNumericValue(S.charAt( S.length() - 2 )) ; j++) {
                    Contents += "@";
                }
            }
            
            
            if( S.length() >= 1 ) {
                for(int j = 0; j <  Character.getNumericValue(S.charAt( S.length() - 1 )) ; j++) {
                    Contents += "*";
                }
            }
           
        }
        Contents += "\n";
        
        return(Contents);
    }
    
    public void toFile(File file) {
        
        if( file.exists() == false ) {
            try {
                file.createNewFile();
            } catch (IOException ex) {}
        }
        
        try {
            PrintWriter Writer = new PrintWriter(file);
            Writer.print( this.toString() );
        } catch (FileNotFoundException ex) {}
        
    }
    
    public void equalize() {
        double[] CP = new double[256];
        double[] PMF = new double[256];
        /*
            row 0 = luminocity
            row 1 = # of pixels with above luminocity
            double PMF = #/size of image
            double CP = cumulative probability
            row 3 = row 3 * desired max luminocity
        */
        for(int i = 0; i < LENGTH; i++) {
            PMF[i] = (double) ( this.histogram[0][i]/this.getHistogramSum() );
        }
        
        CP[0] = PMF[0];
        this.histogram[0][0] = (short) (235*CP[0]);
        for(int i = 1; i < LENGTH; i++) {//htan int i = 0
            CP[i] = CP[i-1] + PMF[i];
            /*if( i != 0 ) {
                CP[i] = CP[i-1] + PMF[i];
            }
            else {
                CP[i] = PMF[i];
            }*/
            this.histogram[0][i] = (short) (235*CP[i]);
        }
        
    }
    
    public int getHistogramSum() {
        int sum = 0;
        
        for(int i = 0; i < LENGTH; i++) {
            sum += this.histogram[1][i];
        }
        
        return(sum);
    }
    
    public short getEqualizedLuminocity(int luminocity) {
        
        return((short)this.histogram[0][luminocity]);
    }
    
}
