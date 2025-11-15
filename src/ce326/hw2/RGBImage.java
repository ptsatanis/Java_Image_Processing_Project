/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

/**
 *
 * @author Tsatanis Panagiotis
 */
public class RGBImage implements Image {
    RGBPixel[][] Image;
    int Height, Width, ColorDepth;
    
    public static final int MAX_COLORDEPTH = 255;
    
    
    public RGBImage() {}
    
    public RGBImage(int width, int height, int colordepth) {
        this.Image = new RGBPixel[height][width];
        this.setHeight(height);
        this.setWidth(width);
        this.setColorDepth(colordepth);
        
        for(int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                this.Image[row][col] = new RGBPixel( (short)0, (short)0, (short)0);
            }
        }
        
    }
    
    public RGBImage(RGBImage copyImg) {
        this( copyImg.getWidth(), copyImg.getHeight(), copyImg.getColorDepth() );
        
        for(int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                this.setPixel(row, col, copyImg.getPixel(row, col));
            }
        }
        
    }
    
    public RGBImage(YUVImage YUVImg) {
        this( YUVImg.getWidth(), YUVImg.getHeight(), YUVImg.getColorDepth() );
        
        for(int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                YUVPixel pixel = YUVImg.getPixel(row, col);
                this.setPixel(row, col, pixel);
            }
        }
        
    }
    
    int getWidth() {
        return(this.Width);
    }
    
    int getSize() {
        return( this.getHeight() * this.getWidth() );
    }
    
    int getHeight() {
        return(this.Height);
    }
    
    int getColorDepth() {
        return(this.ColorDepth);
    }
    
    RGBPixel getPixel(int row, int col) {
        return(this.Image[row][col]);
    }
    
    void setPixel(int row, int col, RGBPixel pixel) {
        RGBPixel p = this.getPixel(row, col);
        //p.setRGB(pixel.getRGB());
        p.setRGB(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }
    
    void setPixel(int row, int col, YUVPixel pixel) {
        RGBPixel p = this.getPixel(row, col);
        
        short NewRed = p.clip((short) ( ( 298*( pixel.getY() - 16 ) + 409*( pixel.getV() - 128 ) + 128 ) >> 8 ) );
        
        short NewGreen = p.clip((short) ( ( 298*( pixel.getY() - 16 ) - 100*( pixel.getU() - 128 ) 
                - 208*( pixel.getV() - 128 ) + 128 ) >> 8 ) );
        
        short NewBlue = p.clip((short) ( ( 298*( pixel.getY() - 16 ) + 516*( pixel.getU() - 128 ) + 128 ) >> 8 ) );
        
        p.setRGB(NewRed, NewGreen, NewBlue);
    }
    
    void setHeight(int height) {
        this.Height = height;
    }
    
    void setWidth(int width) {
        this.Width = width;
    }
    
    void setColorDepth(int colordepth) {
        this.ColorDepth = colordepth;
    }
    
    public void grayscale() {
        for(int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                RGBPixel p = this.getPixel(row, col);
                short gray = (short) (p.getRed()*0.3 + p.getGreen()*0.59 + p.getBlue()*0.11);
                p.setRGB(gray, gray, gray);
                this.setPixel(row, col, p);
            }
        }
    }
    
    public void doublesize() {
        RGBPixel[][] NewImage = new RGBPixel[this.getHeight()*2][this.getWidth()*2];
        
        for(int row = 0; row < this.getHeight()*2; row++) {
            for(int col = 0; col < this.getWidth()*2; col++) {
                NewImage[row][col] = new RGBPixel( (short)0, (short)0, (short)0);
            }
        }
        
        for(int row = 0; row < this.getHeight(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                
                RGBPixel p0 = this.getPixel(row, col);
                
                RGBPixel p1 = NewImage[2*row][2*col];
                RGBPixel p2 = NewImage[2*row + 1][2*col];
                RGBPixel p3 = NewImage[2*row][2*col + 1];
                RGBPixel p4 = NewImage[2*row + 1][2*col + 1];
                
                p1.setRGB(p0.getRed(), p0.getGreen(), p0.getBlue());
                p2.setRGB(p0.getRed(), p0.getGreen(), p0.getBlue());
                p3.setRGB(p0.getRed(), p0.getGreen(), p0.getBlue());
                p4.setRGB(p0.getRed(), p0.getGreen(), p0.getBlue());
            }
        }
        
        this.setHeight(this.getHeight()*2);
        this.setWidth(this.getWidth()*2);
        this.Image = NewImage;
        
    }
    
    public void halfsize() {
        RGBPixel[][] NewImage = new RGBPixel[this.getHeight()/2][this.getWidth()/2];
        
        for(int row = 0; row < this.getHeight()/2; row++) {
            for(int col = 0; col < this.getWidth()/2; col++) {
                NewImage[row][col] = new RGBPixel( (short)0, (short)0, (short)0);
            }
        }
        
        for(int row = 0; row < this.getHeight()/2; row++) {
            for(int col = 0; col < this.getWidth()/2; col++) {
                
                RGBPixel p0 = NewImage[row][col];
                
                RGBPixel p1 = this.getPixel(2*row , 2*col);
                RGBPixel p2 = this.getPixel(2*row + 1, 2*col);
                RGBPixel p3 = this.getPixel(2*row, 2*col + 1);
                RGBPixel p4 = this.getPixel(2*row + 1, 2*col + 1);
                
                //p0.setRGB( ( p1.getRGB() + p2.getRGB() + p3.getRGB() + p4.getRGB() )/4 );
                
                p0.setRed((short) (( p1.getRed() + p2.getRed() + p3.getRed() + p4.getRed() )/4));
                p0.setGreen((short) (( p1.getGreen() + p2.getGreen() + p3.getGreen() + p4.getGreen() )/4));
                p0.setBlue((short) (( p1.getBlue() + p2.getBlue() + p3.getBlue() + p4.getBlue() )/4));
            }
        }
        
        this.setHeight(this.getHeight()/2);
        this.setWidth(this.getWidth()/2);
        this.Image = NewImage;
        
    }
    
    public void rotateClockwise() {
        ///RGBImage RotatedImage = new RGBImage(this.getHeight(), this.getHeight(), this.getColorDepth());
        
        int NewHeight = this.getWidth();
        int NewWidth = this.getHeight();
        
        RGBPixel[][] RotatedImage = new RGBPixel[NewHeight][NewWidth];
        
        for(int row = 0; row < NewHeight; row++) {
            for(int col = 0; col < NewWidth; col++) {
                RotatedImage[row][col] = new RGBPixel( (short)0, (short)0, (short)0);
            }
        }
        
        for(int col = 0; col < this.getWidth(); col++) {
            for(int row = this.getHeight() - 1, i = 0; row >= 0; row--) {
                //RotatedImage.setPixel(col, i++, this.getPixel(row, col));
                RGBPixel p = this.getPixel(row, col);
                RotatedImage[col][i++].setRGB(p.getRed(), p.getGreen(), p.getBlue());
            }
        }

        this.setHeight(NewHeight);
        this.setWidth(NewWidth);
        this.Image = RotatedImage;
    }

    
    
}
