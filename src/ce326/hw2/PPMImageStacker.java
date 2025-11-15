/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ListIterator;
import java.util.Scanner;

/**
 *
 * @author Tsatanis Panagiotis
 */
public class PPMImageStacker {
    LinkedList<File> Files = null;
    PPMImage StackedImage;
    
    public PPMImageStacker(java.io.File dir) throws FileNotFoundException, UnsupportedFileFormatException {
        //try {
            
            if( dir.exists() == false ) {
                throw new FileNotFoundException("[ERROR]Directory " + dir.getName() + " does not exists!");
            }
            
            else if( dir.isDirectory() == false ) {
                throw new FileNotFoundException("[ERROR] " + dir.getName() + " is not a directory!");
            }
            
            Files = new LinkedList<File>();
            
            File[] tmp = dir.listFiles();
            
            for( File curr : tmp ) {
                Files.add(curr);
            }
            
    }
    
    public void stack() throws FileNotFoundException {
        if( Files == null ) return;
        
        short[][] RedArray = null;
        short[][] GreenArray = null;
        short[][] BlueArray = null;
        
        RGBImage Img = null;
        
        ListIterator<File> curr = Files.listIterator();
        
        while( curr.hasNext() ) {
            
            try ( Scanner Sc = new Scanner(curr.next()) ) {
                String MagicNumber = Sc.next();
                int Width = Sc.nextInt();
                int Height = Sc.nextInt();
                int ColorDepth = Sc.nextInt();
                
                if( Img == null ) {
                    Img = new RGBImage(Width, Height, ColorDepth);
                    RedArray = new short[Height][Width];
                    GreenArray = new short[Height][Width];
                    BlueArray = new short[Height][Width];
                }
                
                for(int row = 0; row < Img.getHeight(); row++) {
                    for(int col = 0; col < Img.getWidth(); col++) {
                        RGBPixel pixel = Img.getPixel(row, col);
                        short red = Sc.nextShort();
                        short green = Sc.nextShort();
                        short blue = Sc.nextShort();
                        
                        RedArray[row][col] += red;
                        GreenArray[row][col] += green;
                        BlueArray[row][col] += blue;
                        
                    }
                }
             
            }
        }
        
        if( Img == null ) return;
        
        for(int row = 0; row < Img.getHeight(); row++) {
            for(int col = 0; col < Img.getWidth(); col++) {
                RGBPixel pixel = Img.getPixel(row, col);
                pixel.setRGB((short) (RedArray[row][col]/Files.size()), (short) (GreenArray[row][col]/Files.size()),
                        (short) (BlueArray[row][col]/Files.size()));
            }
        }
        
        this.StackedImage = new PPMImage(Img);
        
    }
    
    public PPMImage getStackedImage() {
        return(this.StackedImage);
    }
    
}
