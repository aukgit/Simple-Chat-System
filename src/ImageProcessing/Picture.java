package ImageProcessing;

import Database.Components.ISoftwareInformation;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Graphics;
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
    private String previousDirectory;

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
            setImage(ImageIO.read(new File(imageLocation)));
        } catch (Exception e) {
            System.out.println("Can't find the image path file: " + imageLocation);
            return null;
        }
        if (getImage().getHeight() == h && getImage().getWidth() == w) {
            setImageResized(getImage());
            return getImageResized();
        }

        try {

            setImageResized(Thumbnails.of(getImage()).size(w, h).asBufferedImage());
        } catch (Exception e) {
            System.out.println("Sorry can't resize this image:" + e);
        }

        return getImageResized();
    }

    //Written By Alim UL Karim, using http://code.google.com/p/thumbnailator/
    public BufferedImage ResizeImage(BufferedImage img, int w, int h) {
        try {
            setImage(img);
        } catch (Exception e) {
            System.out.println("Can't find the image.");
            return null;
        }
        if (getImage().getHeight() == h && getImage().getWidth() == w) {
            setImageResized(getImage());
            return getImageResized();
        }
        try {
            setImageResized(Thumbnails.of(getImage()).size(w, h).asBufferedImage());
        } catch (Exception e) {
            System.out.println("Sorry can't resize this image:" + e);
        }

        return getImageResized();
    }

    //Written By Alim UL Karim
    public BufferedImage generateImageFromFile(String fileName) {
        this.filename = fileName;
        File file = new File(fileName);
        return generateImageFromFile(file);
    }

    public BufferedImage generateImageFromFile(File file) {
        try {
            // try to read from file in working directory
            if (file.isFile()) {
                setImage(ImageIO.read(file));
            } // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
                if (url == null) {
                    url = new URL(filename);
                }
                setImage(ImageIO.read(url));
            }
            width = getImage().getWidth(null);
            height = getImage().getHeight(null);
        } catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException("Could not open file: " + filename);
        }
        return getImage();
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
    /**
     *
     * @param ji
     * @param imageLocation
     * @param NotFoundText
     * @return
     */
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

    /**
     * set current processing image to the label.
     *
     * @param ji
     * @return
     */
    public ImageIcon setImageIcon(JLabel ji) {
        imgico = new ImageIcon(this.getImage());
        ji.setText("");
        ji.setIcon(imgico);
        return imgico;
    }

    public ImageIcon setImageIcon(JLabel ji, BufferedImage img) {
        imgico = new ImageIcon(img);
        ji.setText("");
        ji.setIcon(imgico);
        return imgico;
    }

    public ImageIcon setImageIcon(JLabel ji, ImageIcon img) {

        ji.setText("");
        ji.setIcon(img);
        return imgico;
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
                imgico = new ImageIcon(getImage());
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
                ResizeImage(getImage(), w, h);
                imgico = new ImageIcon(getImageResized());
            } catch (Exception e) {
                err.showError(o, e, "Image can't be proccessed");
                return null;
            }
            ji.setIcon(imgico);
            save(new File(imageSaveLocation), getImageResized());
        }
        return imgico;
    }

    /**
     * Return a JLabel containing this Picture, for embedding in a JPanel,
     * JFrame or other GUI widget.
     */
    public JLabel getJLabel() {
        if (getImage() == null) {
            return null;
        }         // no image available
        ImageIcon icon = new ImageIcon(getImage());
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
            return new Color(getImage().getRGB(i, j));
        } else {
            return new Color(getImage().getRGB(i, height - j - 1));
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
            getImage().setRGB(i, j, c.getRGB());
        } else {
            getImage().setRGB(i, height - j - 1, c.getRGB());
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
     * Save the picture to a file in a standard image format. The filetype must
     * be .png or .jpg.
     */
    public void save(String name) {
        save(new File(name));
    }

    /**
     * Save the picture to a file in a standard image format.
     */
    public void save(File file) {
        save(file, this.getImage());
    }

    /**
     * overwrites if exist.
     *
     * @param file : overwrites if exist.
     * @param image2
     */
    public void save(File file, BufferedImage image2) {
        this.filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        if (suffix.equals("jpg") || suffix.equals("png") || suffix.equals("gif")) {
            File f2 = new File(file.getPath());
            if (f2.exists() == false) {
                f2.mkdirs();
            }
            if (file.exists()) {
                file.delete();
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
     *
     * @param e
     */
    public void browseFileAndSaveFile(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame,
                "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        if (previousDirectory != null) {
            chooser.setDirectory(previousDirectory);
        }
        if (chooser.getFile() != null) {
            previousDirectory = chooser.getFile();

            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    private String GetFullPath(FileDialog f) {
        if (f != null && f.getFile() != null) {
            return f.getDirectory() + File.separator + f.getFile();
        }
        return null;
    }

    public File browseFileAndGetFile() {
        return new File(browseFileAndGetFileName());
    }

    public String browseFileAndGetFileName() {
        FileDialog chooser = new FileDialog(frame,
                "Use a .png or .jpg extension", FileDialog.LOAD);
        chooser.setVisible(true);
        if (previousDirectory != null) {
            chooser.setDirectory(previousDirectory);
        }
        if (chooser.getFile() != null) {
            previousDirectory = chooser.getDirectory();

            return GetFullPath(chooser);
        }
        return null;
    }

    /**
     * Test client. Reads a picture specified by the command-line argument, and
     * shows it in a window on the screen.
     */
    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        System.out.printf("%d-by-%d\n", pic.width(), pic.height());
        pic.show();
    }

    public BufferedImage getBufferedImage(ImageIcon icon) {
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return bi;
    }

    public ImageIcon getImageIcon(BufferedImage img) {
        return new ImageIcon(img);
    }

    /**
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getImage(String fileName) {
        generateImageFromFile(fileName);

        return getImage();
    }

    public BufferedImage getImage(File file) {
        generateImageFromFile(file);

        return getImage();
    }

    public ImageIcon getImageIcon(String fileName) {
        return new ImageIcon(fileName);
    }

    /**
     *
     * @param fileName
     * @param safeLoadIfExist: any safe load image if only exist.
     * @return ImageIcon or null if none exist.
     */
    public ImageIcon getImageIcon(String fileName, boolean safeLoadIfExist) {
        File f = new File(fileName);
        if (f.exists()) {
            return new ImageIcon(fileName);
        }
        return null;
    }

    /**
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public File browseFile(Component whereLoadingFrom) {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Choose Your File");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //below codes for select  the file 
        int returnval = filechooser.showOpenDialog(whereLoadingFrom);
        if (returnval == JFileChooser.APPROVE_OPTION) {
            return filechooser.getSelectedFile();
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the imageResized
     */
    public BufferedImage getImageResized() {
        return imageResized;
    }

    /**
     * @param imageResized the imageResized to set
     */
    public void setImageResized(BufferedImage imageResized) {
        this.imageResized = imageResized;
    }
}
