/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;




/**
 *
 * @author Tsatanis Panagiotis
 */
public class PPMImage extends RGBImage {
    private File PPMFile = null;
    
    public PPMImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {
        Scanner Sc = null;
        try {
            
            if( file.exists() == false || file.canRead() == false ) {
                throw new java.io.FileNotFoundException();
            }
            
            Sc = new Scanner(file);
            
            String MagicNumber = Sc.next();
            
            if( MagicNumber.compareTo("P3") !=0 ) {
                Sc.close();
                throw new ce326.hw2.UnsupportedFileFormatException();
            }
            int NewWidth = Sc.nextInt();
            int NewHeight = Sc.nextInt();
            int MaxColorDepth = Sc.nextInt();
            
            RGBImage PPMimg = new RGBImage(NewWidth, NewHeight, MaxColorDepth);
            
            ///////////////
            super.Image = PPMimg.Image;
            super.setHeight(NewHeight);
            super.setWidth(NewWidth);
            super.setColorDepth(MaxColorDepth);
            //////////////
            
            
            int row = 0;
            int col = 0;
            while( Sc.hasNextShort() && row < PPMimg.getHeight() ) {
                RGBPixel pixel = PPMimg.getPixel(row, col);
                //System.out.println("Row = " + row + " Col = " + col);
                short red = Sc.nextShort();
                short green = Sc.nextShort();
                short blue = Sc.nextShort();
                pixel.setRGB(red, green, blue);
                PPMimg.setPixel(row, col++, pixel);
                if( col == PPMimg.getWidth() ) {
                    col = 0;
                    row++;
                }
            }
            
            //PrintWriter Writer = new PrintWriter(file);
            
        }
        //catch ( ce326.hw2.UnsupportedFileFormatException ex ) {}
        //catch (FileNotFoundException ex) {}
        finally {
            if( Sc != null ) {
                Sc.close();
            }
        }
        
        
    }
    
    public PPMImage(RGBImage img) {
        super(img);
    }
    
    public PPMImage(YUVImage img) {
        super(img);
    }
    
    @Override
    public String toString() {
        Scanner Sc = null;
        String Contents = new String();
        try {
            Sc = new Scanner(this.PPMFile);
            while( Sc.hasNextLine() ) {
                Contents += Sc.nextLine();
                Contents += "\n";
            }
        } 
        catch (FileNotFoundException ex) {}
        finally {
            if( Sc != null ) {
                Sc.close();
            }
        }
         
        return(Contents);
    }
    
    public void toFile(java.io.File file) {
        if( file.exists() == true ) {
            file.delete();
        }
        
        try {
            if( file.createNewFile() == true ) {
                PrintWriter Writer = new PrintWriter(file);
                Writer.println("P3");
                Writer.print(super.getWidth());
                Writer.print(' ');
                Writer.print(super.getHeight());
                Writer.print(' ');
                Writer.println(super.getColorDepth());
                
                for(int row = 0; row < super.getHeight(); row++) {
                    for(int col = 0; col < super.getWidth(); col++) {
                        RGBPixel pixel = super.getPixel(row, col);
                        
                        Writer.print(pixel.getRed());
                        Writer.print(' ');
                        Writer.print(pixel.getGreen());
                        Writer.print(' ');
                        Writer.print(pixel.getBlue());
                        Writer.print("\n");
                        
                        
                    }
                }
                
                Writer.close();
                this.PPMFile = file;
            }
        } 
        catch (IOException ex) {}
        
    }
    
}
