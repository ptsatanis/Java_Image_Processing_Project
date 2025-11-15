/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

/**
 *
 * @author Tsatanis Panagiotis
 */
public class YUVPixel {
    private short Y, U, V;
    
    public YUVPixel(short Y, short U, short V) {
        this.setYUV(Y, U, V);
    }
    
    public YUVPixel(YUVPixel pixel) {
        this( pixel.getY(), pixel.getU(), pixel.getV() );
    }
    
    public YUVPixel(RGBPixel pixel) {
        //short NewY = (short) (( ( 66*pixel.getRed() + 129*pixel.getGreen() + 25*pixel.getBlue() + 128 ) >> 8 ) + 16 );
        //short NewU = (short) (( ( -38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128 ) >> 8 ) + 128 );
        //short NewV = (short) (( ( 112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128 ) >> 8 ) + 128 );
        
        //this.setYUV(NewY, NewU, NewV);
        this(
              (short) (( ( 66*pixel.getRed() + 129*pixel.getGreen() + 25*pixel.getBlue() + 128 ) >> 8 ) + 16 ),
              (short) (( ( -38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128 ) >> 8 ) + 128 ),
              (short) (( ( 112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128 ) >> 8 ) + 128 )
        );
    }
    
    public short getY() {
        return(this.Y);
    }
    
    public short getU() {
        return(this.U);
    }
    
    public short getV() {
        return(this.V);
    }
    
    public void setY(short Y) {
        this.Y = Y;
    }
    
    public void setU(short U) {
        this.U = U;
    }
    
    public void setV(short V) {
        this.V = V;
    }
    
    public void setYUV(short Y, short U, short V) {
        this.setY(Y);
        this.setU(U);
        this.setV(V);
    }
    
    
}
