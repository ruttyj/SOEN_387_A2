
package com.soen387.repository.com.soen387.repository.core;


import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.io.*;


/**
 *
 * @author Jordan Rutty
 */
public class ImageManip {
    
    protected static String getExtFromName(String fileName){
        String[] splitName = fileName.split("\\.");
        return splitName[splitName.length-1];
    }
    
    // Keep the aspect ratio on resize
    protected static InputStream resizeImage(InputStream uploadedInputStream, String fileName, int inputWidth) {
        // Reference: https://stackoverflow.com/questions/5895829/resizing-image-in-java
        try {
            // Get extension for later encoding
            String extension = getExtFromName(fileName);
            
            // Create buffered image
            BufferedImage image = ImageIO.read(uploadedInputStream);
            
            // Scale with aspect ratio
            int width = inputWidth;
            float heightRatio = ((float)image.getHeight())/((float)image.getHeight());
            int height = (int)heightRatio*inputWidth;
  
            // Resize image
            Image originalImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            int type = ((image.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : image.getType());
            BufferedImage resizedImage = new BufferedImage(width, height, type);
            Graphics2D render = resizedImage.createGraphics();
            render.drawImage(originalImage, 0, 0, width, height, null);
            render.dispose();
            render.setComposite(AlphaComposite.Src);
            
            // Encode data into InputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, extension, byteArrayOutputStream);
            
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        } catch (IOException ex) {
            return uploadedInputStream;
        }
    }
}
