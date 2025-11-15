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
public class YUVImage {
    YUVPixel[][] Image;
    int Width, Height, ColorDepth;
    File YUVFile;
    
    public static final int MAX_COLORDEPTH = 255;
    
    public static final int LENGTH = 256;
    
    public YUVImage(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
        this.setColorDepth(MAX_COLORDEPTH);
        
        this.Image = new YUVPixel[this.getHeight()][this.getWidth()];
        
        for(int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                this.Image[row][col] = new YUVPixel( (short)16, (short)128, (short)128);
                YUVPixel p = this.getPixel(row, col);
                p.setYUV( (short)16, (short)128, (short)128 );
            }
        }
        
    }
    
    public YUVImage(YUVImage CopyImg) {
        this.setHeight(CopyImg.getHeight());
        this.setWidth(CopyImg.getWidth());
        this.setColorDepth(CopyImg.getColorDepth());
        this.Image = new YUVPixel[this.getHeight()][this.getWidth()];
        
        for(int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                this.Image[row][col] = new YUVPixel( (short)16, (short)128, (short)128);
                YUVPixel p = CopyImg.getPixel(row, col);
                this.setPixel(row, col, p.getY(), p.getU(), p.getV());
            }
        }
    }
    
    public YUVImage(RGBImage RGBImg) {
        this.setHeight(RGBImg.getHeight());
        this.setWidth(RGBImg.getWidth());
        this.setColorDepth(RGBImg.getColorDepth());
        
        this.Image = new YUVPixel[this.getHeight()][this.getWidth()];
        
        for(int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                this.Image[row][col] = new YUVPixel( (short)16, (short)128, (short)128);
                RGBPixel pixel = RGBImg.getPixel(row, col);
                short NewY = (short) (( ( 66*pixel.getRed() + 129*pixel.getGreen() + 25*pixel.getBlue() + 128 ) >> 8 ) + 16 );
                short NewU = (short) (( ( -38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128 ) >> 8 ) + 128 );
                short NewV = (short) (( ( 112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128 ) >> 8 ) + 128 );
                
                this.setPixel(row, col, NewY, NewU, NewV);
            }
        }
    }
    
    public YUVImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {
        Scanner Sc = null;
        try {
            
            if( file.exists() == false || file.canRead() == false ) {
                throw new java.io.FileNotFoundException();
            }
            
            Sc = new Scanner(file);
            
            String MagicNumber = Sc.next();
            
            if( MagicNumber.compareTo("YUV3") !=0 ) {
                Sc.close();
                throw new ce326.hw2.UnsupportedFileFormatException();
            }
            int NewWidth = Sc.nextInt();
            int NewHeight = Sc.nextInt();
            //int MaxColorDepth = Sc.nextInt();
            
            this.setWidth(NewWidth);
            this.setHeight(NewHeight);
            //this.setColorDepth(MaxColorDepth);
            
            this.Image = new YUVPixel[this.getHeight()][this.getWidth()];
            
            int row = 0;
            int col = 0;
            while( Sc.hasNextShort() && row < this.getHeight() ) {
                this.Image[row][col] = new YUVPixel( (short)16, (short)128, (short)128);
                YUVPixel pixel = this.getPixel(row, col);
                short y = Sc.nextShort();
                short u = Sc.nextShort();
                short v = Sc.nextShort();
                pixel.setYUV(y, u, v);
                this.setPixel( row, col++, pixel.getY(), pixel.getU(), pixel.getV() );
                if( col == this.getWidth() ) {
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
    
    public int getWidth() {
        return(this.Width);
    }
    
    public int getHeight() {
        return(this.Height);
    }
    
    public int getSize() {
        return(this.getHeight() * this.getWidth());
    }
    
    public void setWidth(int Width) {
        this.Width = Width;
    }
    
    public void setHeight(int Height) {
        this.Height = Height;
    }
    
    public YUVPixel getPixel(int row, int col) {
        return(this.Image[row][col]);
    }
    
    public void setPixel(int row, int col, short y, short u, short v) {
        YUVPixel pixel = this.getPixel(row, col);
        pixel.setYUV(y, u, v);
    }
    
    
    int getColorDepth() {
        return(this.ColorDepth);
    }
    
    void setColorDepth(int colordepth) {
        this.ColorDepth = colordepth;
    }
    
    public void toFile(java.io.File file) {
        
        try {
            
            if( file.exists() == true ) {
                file.delete();
            }
            
            if( file.createNewFile() == true ) {
                PrintWriter Writer = new PrintWriter(file);
                Writer.println("YUV3");
                Writer.print(this.getWidth());
                Writer.print(' ');
                Writer.println(this.getHeight());
                
                for(int row = 0; row < this.getHeight(); row++) {
                    for(int col = 0; col < this.getWidth(); col++) {
                        YUVPixel pixel = this.getPixel(row, col);
                        
                        Writer.print(pixel.getY());
                        Writer.print(' ');
                        Writer.print(pixel.getU());
                        Writer.print(' ');
                        Writer.print(pixel.getV());
                        Writer.print("\n");
                    }
                }
                
                this.YUVFile = file;
                Writer.close();
            }
        }
        catch (IOException ex) {}
        
    }
    
    @Override
    public String toString() {
        Scanner Sc = null;
        String Contents = new String();
        try {
            Sc = new Scanner(this.YUVFile);
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
    
    public void equalize() {
        double[][] PixelsLuminocity = new double[3][256];
        double[] CP = new double[256];
        double[] PMF = new double[256];
        /*
            row 0 = luminocity
            row 1 = # of pixels with above luminocity
            double PMF = #/size of image
            double CP = cumulative probability
            row 4 = row 3 * desired max luminocity
        */
        for(int i = 0; i < LENGTH; i++) {
            PixelsLuminocity[0][i] = (short) i;
            PixelsLuminocity[1][i] = (short) this.getInstanciesOfPixel((short) i);
            //PixelsLuminocity[2][i] = (short) (PixelsLuminocity[1][i]/this.getSize());
            //System.out.println(PixelsLuminocity[1][i]);
            PMF[i] = (double) (PixelsLuminocity[1][i]/this.getSize());
            //System.out.println(this.getSize());
        }
        
        CP[0] = PMF[0];
        PixelsLuminocity[2][0] = (short) (235*CP[0]);
        
        
        for(int i = 1; i < LENGTH; i++) { // htan i = 0
            CP[i] = CP[i-1] + PMF[i];
            
            //System.out.println("CP = " + CP[i] + "i = " + i);
            PixelsLuminocity[2][i] = (short) (235*CP[i]);
            System.out.println(PixelsLuminocity[2][i] + " i = " + i);
        }
        
        int i;
        for(int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                YUVPixel pixel = this.getPixel(row, col);
                for(i = 0; i < LENGTH && pixel.getY() != i; i++) {}
                
                pixel.setY((short)PixelsLuminocity[2][i]);
            }
        }
        
    }
    
    public int getInstanciesOfPixel(short Luminocity) {
        int instancies = 0;

        for( int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                YUVPixel curr = this.getPixel(row, col);
                if( Luminocity == curr.getY() ) {
                    instancies++;
                }
            }
        }
        //System.out.println(instancies);
        return(instancies);
    }
    
}
