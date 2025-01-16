import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.sound.sampled.*;

public class JerryClicker extends JFrame {

    private JTextField counterField;

    public JerryClicker() {
        super("Jerry Clicker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 1000));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        addComponents();

        // Since the music file cannot be added to the repo, I'll keep this line commented out
        // playMusic("stuff/AllJerryNeeds.wav");
    }

    private void addComponents() {
        SpringLayout springLayout = new SpringLayout();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(springLayout);

        // Banner

        JLabel banner = loadPicture("stuff/Banner.png", true, 600, 200);
        jPanel.add(banner);

        // Jerry Button

        JButton jerryButton = createImageButton("stuff/JerryButton.png", true, 500, 525);
        assert jerryButton != null;
        jerryButton.addActionListener(e -> {
            int counter = Integer.parseInt(counterField.getText());
            counterField.setText(Integer.toString(++counter));
            playSound("stuff/test.wav");
        });

        jPanel.add(jerryButton);
        springLayout.putConstraint(SpringLayout.WEST, jerryButton, 40, SpringLayout.WEST, jPanel);
        springLayout.putConstraint(SpringLayout.NORTH, jerryButton, 250, SpringLayout.NORTH, jPanel);

        // "Jerries: " Label

        JLabel counterLabel = new JLabel("Jerries: ");
        counterLabel.setFont(new Font("Dialog", Font.BOLD, 26));

        jPanel.add(counterLabel);
        springLayout.putConstraint(SpringLayout.WEST, counterLabel, 80, SpringLayout.WEST, jPanel);
        springLayout.putConstraint(SpringLayout.NORTH, counterLabel, 800, SpringLayout.NORTH, jPanel);

        // Counter Field

        counterField = new JTextField(10);
        counterField.setFont(new Font("Dialog", Font.BOLD, 26));
        counterField.setHorizontalAlignment(SwingConstants.RIGHT);
        counterField.setText("0");
        counterField.setEditable(false);

        jPanel.add(counterField);
        springLayout.putConstraint(SpringLayout.WEST, counterField, 250, SpringLayout.WEST, jPanel);
        springLayout.putConstraint(SpringLayout.NORTH, counterField, 800, SpringLayout.NORTH, jPanel);

        // Reset Button

        JButton resetButton = new JButton("RESET");
        resetButton.setFont(new Font("Dialog", Font.BOLD, 18));
        resetButton.addActionListener(e -> counterField.setText("0"));

        jPanel.add(resetButton);
        springLayout.putConstraint(SpringLayout.WEST, resetButton, 230, SpringLayout.WEST, jPanel);
        springLayout.putConstraint(SpringLayout.NORTH, resetButton, 900, SpringLayout.NORTH, jPanel);

        this.getContentPane().add(jPanel);
    }

    private JButton createImageButton(String fname, boolean isResized, int newWidth, int newHeight) {
        JButton button;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(fname);
            assert inputStream != null;
            BufferedImage image = ImageIO.read(inputStream);
            if (isResized) {
                image = resizePicture(image, newWidth, newHeight);
            }
            button = new JButton(new ImageIcon(image));
            return button;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    private JLabel loadPicture(String fname, boolean isResized, int newWidth, int newHeight) {
        BufferedImage image;
        JLabel pictureContainer;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(fname);
            assert inputStream != null;
            image = ImageIO.read(inputStream);
            if (isResized) {
                image = resizePicture(image, newWidth, newHeight);
            }
            pictureContainer = new JLabel(new ImageIcon(image));
            return pictureContainer;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    private BufferedImage resizePicture(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);
        graphics2D.dispose();
        return newImage;
    }

    public void playMusic(String fname) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(fname);
            assert inputStream != null;
            AudioInputStream musicInputStream = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(musicInputStream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-15.0f);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void playSound(String fname) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(fname);
            assert inputStream != null;
            AudioInputStream soundInputStream = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(soundInputStream);
            clip.setFramePosition(0);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(5.0f);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
