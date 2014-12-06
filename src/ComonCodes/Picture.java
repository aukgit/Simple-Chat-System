package ComonCodes;

import Database.Components.ISoftwareInformation;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.coobird.thumbnailator.Thumbnails;

//collected from http://introcs.cs.princeton.edu/java/stdlib/Picture.java.html
//Modifed by Alim Ul Karim
public final class Picture implements ActionListener, ISoftwareInformation {

    private BufferedImage image;               // the rasterized image
    private BufferedImage imageResized;         // resized image
    private JFrame frame;                      // on-screen view
    private String filename;                   // name of file
    private boolean isOriginUpperLeft = true;  // location of origin
    private int width, height;                 // width and height
    private ImageIcon imgico;
    private JFileChooser filesaveDialog = new JFileChooser();
    private boolean FilterAttached = false;

    public Picture() {
    }

    /**
     * Create a blank w-by-h picture, where each pixel is black.
     */
    public Picture(int w, int h) {
        width = w;
        height = h;
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // set to TYPE_INT_ARGB to support transparency
        filename = w + "-by-" + h;
    }

    //Modified By Alim UL Karim
    public Picture(String filename) {
        generateImageFromFile(filename);
    }

    //Written By Alim UL Karim, using http://code.google.com/p/thumbnailator/
    public BufferedImage ResizeImage(String imageLocation, int w, int h) {
        try {
            image = ImageIO.read(new File(imageLocation));
        } catch (Exception e) {
            System.out.println("Can't find the image path file: " + imageLocation);
            return null;
        }
        if (image.getHeight() == h && image.getWidth() == w) {
            imageResized = image;
            return imageResized;
        }

        try {

            imageResized = Thumbnails.of(image).size(w, h).asBufferedImage();
        } catch (Exception e) {
            System.out.println("Sorry can't resize this image:" + e);
        }

        return imageResized;
    }

    //Written By Alim UL Karim, using http://code.google.com/p/thumbnailator/
    public BufferedImage ResizeImage(BufferedImage img, int w, int h) {
        try {
            image = img;
        } catch (Exception e) {
            System.out.println("Can't find the image.");
            return null;
        }
        if (image.getHeight() == h && image.getWidth() == w) {
            imageResized = image;
            return imageResized;
        }
        try {
            imageResized = Thumbnails.of(image).size(w, h).asBufferedImage();
        } catch (Exception e) {
            System.out.println("Sorry can't resize this image:" + e);
        }

        return imageResized;
    }

    //Written By Alim UL Karim
    public BufferedImage generateImageFromFile(String fileName) {
        this.filename = fileName;
        try {
            // try to read from file in working directory
            File file = new File(fileName);
            if (file.isFile()) {
                image = ImageIO.read(file);
            } // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
                if (url == null) {
                    url = new URL(filename);
                }
                image = ImageIO.read(url);
            }
            width = image.getWidth(null);
            height = image.getHeight(null);
        } catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException("Could not open file: " + filename);
        }
        return image;
    }

    /**
     * Create a picture by reading in a .png, .gif, or .jpg from a File.
     */
    public Picture(File file) {
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException("Could not open file: " + file);
        }
        if (image == null) {
            throw new RuntimeException("Invalid image file: " + file);
        }
    }
//Written By Alim UL Karim

    public ImageIcon setImageIcon(JLabel ji, String imageLocation, String NotFoundText) {
        if (new File(imageLocation).exists() == false) {
            imgico = new ImageIcon();
            ji.setText(NotFoundText);
            ji.setIcon(imgico);
            return imgico;
        } else {
            imgico = new ImageIcon(imageLocation);
            ji.setText("");
            ji.setIcon(imgico);
            return imgico;
        }

    }

    //Written By Alim UL Karim
    public ImageIcon setImageIconSave(Component o, JLabel ji, String imageSaveLocation) {
        if (FilterAttached == false) {
            FileFilter ft = new FileNameExtensionFilter("JPEG", "jpg");
            FileFilter ft2 = new FileNameExtensionFilter("PNG", "png");
            FileFilter ft3 = new FileNameExtensionFilter("GIF", "gif");
            filesaveDialog.addChoosableFileFilter(ft);
            filesaveDialog.addChoosableFileFilter(ft2);
            filesaveDialog.addChoosableFileFilter(ft3);
            FilterAttached = true;
        }
        int returnVal = filesaveDialog.showSaveDialog(o);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                String SelectedFileName = filesaveDialog.getSelectedFile().getAbsolutePath().toString();
                generateImageFromFile(SelectedFileName);
                imgico = new ImageIcon(image);
            } catch (Exception e) {
                err.showError(o, e, "Image can't be proccessed");
                return null;
            }
            ji.setIcon(imgico);
            save(imageSaveLocation);
        }
        return imgico;
    }

    //Written By Alim UL Karim
    public ImageIcon setImageIconSave(Component o, JLabel ji, String imageSaveLocation, int w, int h) {
        if (FilterAttached == false) {
            FileFilter ft = new FileNameExtensionFilter("JPEG", "jpg");
            FileFilter ft2 = new FileNameExtensionFilter("PNG", "png");
            FileFilter ft3 = new FileNameExtensionFilter("GIF", "gif");
            filesaveDialog.addChoosableFileFilter(ft);
            filesaveDialog.addChoosableFileFilter(ft2);
            filesaveDialog.addChoosableFileFilter(ft3);
            FilterAttached = true;
        }
        int returnVal = filesaveDialog.showSaveDialog(o);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                String SelectedFileName = filesaveDialog.getSelectedFile().getAbsolutePath().toString();
                generateImageFromFile(SelectedFileName);
                ResizeImage(image, w, h);
                imgico = new ImageIcon(imageResized);
            } catch (Exception e) {
                err.showError(o, e, "Image can't be proccessed");
                return null;
            }
            ji.setIcon(imgico);
            save(new File(imageSaveLocation), imageResized);
        }
        return imgico;
    }

    /**
     * Return a JLabel containing this Picture, for embedding in a JPanel,
     * JFrame or other GUI widget.
     */
    public JLabel getJLabel() {
        if (image == null) {
            return null;
        }         // no image available
        ImageIcon icon = new ImageIcon(image);
        return new JLabel(icon);
    }

    /**
     * Set the origin to be the upper left pixel.
     */
    public void setOriginUpperLeft() {
        isOriginUpperLeft = true;
    }

    /**
     * Set the origin to be the lower left pixel.
     */
    public void setOriginLowerLeft() {
        isOriginUpperLeft = false;
    }

    /**
     * Display the picture in a window on the screen.
     */
    public void show() {

        // create the GUI for viewing the image if needed
        if (frame == null) {
            frame = new JFrame();

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("File");
            menuBar.add(menu);
            JMenuItem menuItem1 = new JMenuItem(" Save...   ");
            menuItem1.addActionListener(this);
            menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            menu.add(menuItem1);
            frame.setJMenuBar(menuBar);



            frame.setContentPane(getJLabel());
            // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setTitle(filename);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }

        // draw
        frame.repaint();
    }

    /**
     * Return the height of the picture in pixels.
     */
    public int height() {
        return height;
    }

    /**
     * Return the width of the picture in pixels.
     */
    public int width() {
        return width;
    }

    /**
     * Return the color of pixel (i, j).
     */
    public Color get(int i, int j) {
        if (isOriginUpperLeft) {
            return new Color(image.getRGB(i, j));
        } else {
            return new Color(image.getRGB(i, height - j - 1));
        }
    }

    /**
     * Set the color of pixel (i, j) to c.
     */
    public void set(int i, int j, Color c) {
        if (c == null) {
            throw new RuntimeException("can't set Color to null");
        }
        if (isOriginUpperLeft) {
            image.setRGB(i, j, c.getRGB());
        } else {
            image.setRGB(i, height - j - 1, c.getRGB());
        }
    }

    /**
     * Is this Picture equal to obj?
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Picture that = (Picture) obj;
        if (this.width() != that.width()) {
            return false;
        }
        if (this.height() != that.height()) {
            return false;
        }
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (!this.get(x, y).equals(that.get(x, y))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Save the picture to a file in a standard image format.
     * The filetype must be .png or .jpg.
     */
    public void save(String name) {
        save(new File(name));
    }

    /**
     * Save the picture to a file in a standard image format.
     */
    public void save(File file) {
        save(file, this.image);
    }

    public void save(File file, BufferedImage image2) {
        this.filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        if (suffix.equals("jpg") || suffix.equals("png") || suffix.equals("gif")) {
            File f2 = new File(file.getPath());
            if (f2.exists() == false) {
                f2.mkdirs();
            }
            try {
                ImageIO.write(image2, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }

    /**
     * Opens a save dialog box when the user selects "Save As" from the menu.
     */
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame,
                "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        if (chooser.getFile() != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    /**
     * Test client. Reads a picture specified by the command-line argument,
     * and shows it in a window on the screen.
     */
    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        System.out.printf("%d-by-%d\n", pic.width(), pic.height());
        pic.show();
    }
}
