/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.shareit.model.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;

/**
 *
 * @author Dina Ashraf
 */
@Stateless
public class ImageUtil {

    public static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }

    public static String SaveImage(final Part file, String savingPath) {
        try (InputStream input = file.getInputStream()) {
            String fileName = Paths.get(file.getSubmittedFileName()).getFileName().toString();
            File pathFile = new File(savingPath);
            if (pathFile.exists()) {
                writeResizedImage(input, fileName, savingPath);
            } else if (pathFile.mkdirs()) {
                writeResizedImage(input, fileName, savingPath);
            } else {
                throw new IOException("Cannot Create the directories");
            }
            input.close();
            return (savingPath + fileName);
        } catch (IOException e) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private static void writeResizedImage(InputStream input, String fileName, String savingPath) {
        try {
            BufferedImage originalImage = ImageIO.read(input);
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizeImageJpg = ImageUtil.resizeImage(originalImage, type, 450, 600);
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
            String imageUrl = System.getProperty("user.home") + savingPath + fileName;
            ImageIO.write(resizeImageJpg, extension, new File(imageUrl));
            originalImage.flush();
            resizeImageJpg.flush();
        } catch (IOException ex) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
