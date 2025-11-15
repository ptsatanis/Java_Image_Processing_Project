/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;
//import java.lang.Exception;
//import java.util.InputMismatchException;

/**
 *
 * @author Tsatanis Panagiotis
 */
public class UnsupportedFileFormatException extends java.lang.Exception {
    String Message;
    
    public UnsupportedFileFormatException() {
        //try {}
        //catch( InputMismatchException Ex ) {}
    }
    
    public UnsupportedFileFormatException(String msg) {
        this.Message = msg;
    }
}
