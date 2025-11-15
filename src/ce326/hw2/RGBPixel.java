/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

/**
 *
 * @author Tsatanis Panagiotis
 */
public class RGBPixel {
    private byte Red, Green, Blue;
    //private int Color;
    
    public RGBPixel(short red, short green, short blue) {
        this.setRGB(red, green, blue);
    }
    
    public RGBPixel(RGBPixel pixel) {
        this.setRGB(this.getRGB());
    }
    
    public RGBPixel(YUVPixel pixel) {
        short NewRed = clip((short) ( ( 298*( pixel.getY() - 16 ) + 409*( pixel.getV() - 128 ) + 128 ) >> 8 ) );
        short NewGreen = clip((short) ( ( 298*( pixel.getY() - 16 ) - 100*( pixel.getU() - 128 ) 
                - 208*( pixel.getV() - 128 ) + 128 ) >> 8 ) );
        short NewBlue = clip((short) ( ( 298*( pixel.getY() - 16 ) + 516*( pixel.getU() - 128 ) + 128 ) >> 8 ) );
        
        this.setRGB(NewRed, NewGreen, NewBlue);
    }
    
    public short getRed() {
        short red = this.Red;
        red += 128;
        return(red);
        //return( (short)( Color >> 16 )&255 + 128 );
    }
    
    public short getGreen() {
        short green = this.Green;
        green += 128;
        return(green);
        //return( (short)( Color >> 8 )&255 + 128 );
    }
    
    public short getBlue() {
        short blue = this.Blue;
        blue += 128;
        return(blue);
        //return( (short)( Color )&255 + 128 );
    }
    
    public void setRed(short red) {
        this.Red = (byte) (red - 128);
    }
    
    public void setGreen(short green) {
        this.Green = (byte) (green - 128);
    }
    
    public void setBlue(short blue) {
        this.Blue = (byte) (blue - 128);
    }
    
    public final int getRGB() {
        return( this.getRed() + this.getGreen() + this.getBlue() );
    }
    
    public final void setRGB(int value) {
        
        this.setBlue( (short)( ( value ) &255 ) );
        this.setGreen( (short)( ( value >> 8 ) &255 ) );
        this.setRed( (short)( ( value >> 16 ) &255 ) );
    }
    
    public final void setRGB(short red, short green, short blue) {
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
    }
    
    public short clip(short value) {
        if( value < 0 ) {
            return(0);
        }
        else if( value > 255 ) {
            return(255);
        }
        return(value);
    }
    
    @Override
    public String toString() {
        String S = new String();
        S += this.getRed();
        S += " ";
        S += this.getGreen();
        S += " ";
        S += this.getBlue();
        return(S);
    }
    
}
