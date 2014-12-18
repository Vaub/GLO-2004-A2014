/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * Utilitaires pour la manipulation de couleur
 * 
 * @author Vincent
 */
public class ColorUtilities
{

    /**
     * Utilitaire pour faire un mélange entre une couleur transparente sur un fond
     * 
     * @param _avant La couleur a appliquer la transparence
     * @param _arriere La couleur de fond sur laquelle on applique la transparence
     * @param _transparence Valeur alpha entre 0 et 1 (transparence)
     * @return La couleur mélangée
     */
    public static Color AppliquerAlpha(Color _avant, Color _arriere, float _transparence)
    {
        int rouge = _avant.getRed();
        int vert = _avant.getGreen();
        int bleu = _avant.getBlue();
        
        if (_transparence >= 0 && _transparence <= 1)
        {
            rouge = (int)(_avant.getRed() * (_transparence) + _arriere.getRed() * (1 - _transparence));
            vert = (int)(_avant.getGreen() * (_transparence) + _arriere.getGreen() * (1 - _transparence));
            bleu = (int)(_avant.getBlue() * (_transparence) + _arriere.getBlue() * (1 - _transparence));
        }
        
        // Who knows what can happen
        rouge = (rouge > 255) ? 255 : rouge;
        vert = (vert > 255) ? 255 : vert;
        bleu = (bleu > 255) ? 255 : bleu;
        
        return new Color(rouge, vert, bleu);
    }
    
    /**
     * Rempli une image (en respectant le channel alpha) d'une coulour demandée
     * 
     * @param _image Image a colorier
     * @param _couleur Couleur à appliquer
     */
    public static Image ColorImage(Image _image, Color _couleur)
    {
        BufferedImage bimage = new BufferedImage(_image.getWidth(null), _image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bimage.getGraphics();
        g.drawImage(_image, 0, 0, null);
        
        int width = bimage.getWidth();
        int height = bimage.getHeight();
        WritableRaster raster = bimage.getRaster();
        
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                
                pixels[0] = _couleur.getRed();
                pixels[1] = _couleur.getGreen();
                pixels[2] = _couleur.getBlue();
                
                raster.setPixel(x, y, pixels);
            }
        }
        
        return bimage;
    }
    
    public static Color ContrastWhiteOrBlack(Color _color)
    {
//        Color contraste = Color.WHITE;
//        
//        float[] hsb = new float[3];
//        Color.RGBtoHSB(_color.getRed(), _color.getGreen(), _color.getBlue(), hsb);
//        
//        //System.out.println(hsb[0] +" "+ hsb[1] +" "+ hsb[2]);
//        
//        if (hsb[1] > 0.5 &&
//            hsb[0] > 0.1f && hsb[0] <= 0.55 &&
//            hsb[2] > 0.8)
//        {
//            contraste = Color.GRAY;
//        }
//        else if (hsb[1] <= 0.5 &&
//            hsb[2] > 0.6)
//        {
//            contraste = Color.BLACK;
//        }
        double red = _color.getRed(),
               green = _color.getGreen(),
               blue = _color.getBlue();
        
        double yiq = ((red*299d)+(green*587d)+(blue*114d))/1000d;
        
        return (yiq >= 128) ? Color.BLACK : Color.WHITE;
    }
    
    public static byte[] SerialiserImage(BufferedImage _image)
    {
        byte[] array = new byte[0];
        
        try
        {
            DataBufferByte data = (DataBufferByte) _image.getData().getDataBuffer();
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(_image, "png", baos);
            
            array = baos.toByteArray();
            
        } catch (Exception e) { }
        
        return array;
    }
    
    public static Image OuvrirImage(byte[] _buffer)
    {
        BufferedImage img = null;
        
        try
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(_buffer);
            img = ImageIO.read(bais);
            
            System.out.println(img);
        } 
        catch (Exception e) 
        { 
            System.err.println(e.getMessage()); 
        }
        
        return img;
    }
}
