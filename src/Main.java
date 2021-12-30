import com.formdev.flatlaf.FlatDarculaLaf;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.net.URL;


import javax.swing.*;

public class Main {
    private static void createAndShowGUI() throws IOException {
        FlatDarculaLaf.setup();
        JFrame frame = new JFrame("Get random picture");
        ImageIcon img = new ImageIcon("src/Zeronet_logo.png");
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setPreferredSize(new Dimension(700, 500));
        JLabel pictureLabel = new JLabel(new ImageIcon(getPicture(700, 500)));
        frame.add(pictureLabel);
        JButton generatePictureButton = new JButton("Get new picture");
        generatePictureButton.addActionListener(e -> {
            try {
                pictureLabel.setIcon(new ImageIcon(getPicture(frame.getWidth(), frame.getHeight() - 100)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        generatePictureButton.setFont(new Font("Arial", Font.PLAIN, 20));
        JPanel imageButtons = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));

        imageButtons.add(generatePictureButton);
        frame.add(imageButtons);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                ImageIcon currentIcon = (ImageIcon)pictureLabel.getIcon();
                Image image = currentIcon.getImage();
                Image newimg = image.getScaledInstance(frame.getWidth(), frame.getHeight() - 100,  Image.SCALE_FAST);
                currentIcon = new ImageIcon(newimg);
                pictureLabel.setIcon(currentIcon);
            }
        });
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());
        frame.pack();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static byte[] getPicture(int width, int height) throws IOException {
        URL url = new URL(String.format("https://picsum.photos/%s/%s", width, height));
        InputStream input = url.openStream();
        return input.readAllBytes();
    }
}
