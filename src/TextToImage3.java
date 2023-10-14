import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextToImage3 {

    private static String readTextFromFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private static void saveImage(BufferedImage image, int i) {
        try {
            File outputImage = new File("images/" + 0 + "_output "+i+".png");
            ImageIO.write(image, "png", outputImage);
            System.out.println("Image saved successfully: "+i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Text to be rendered
        String text = readTextFromFile("C:\\Users\\JAY\\Downloads\\imagetotxt\\part 1 - Copy.txt"); // Example text containing English and Chinese characters

        // Path to your custom font file
        String fontPath = "C:\\Users\\JAY\\Downloads\\my fonts (2)\\Myfont5-Regular.otf";

        // Font size
        int fontSize = 28;

        // Fallback font for unsupported characters
        Font fallbackFont = new Font("Arial", Font.PLAIN, fontSize);

        // Load background image
        BufferedImage backgroundImage;
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\JAY\\Downloads\\my fonts (2)\\printable-lined-paper-wide-ruled.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Create a BufferedImage object with the same size as the background image
        BufferedImage image = new BufferedImage(backgroundImage.getWidth(), backgroundImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Draw the background image
        g2d.drawImage(backgroundImage, 0, 0, null);

        // Load custom font
        Font customFont = loadCustomFont(fontPath, fontSize);

        // Set font for rendering
        g2d.setFont(customFont);
        FontMetrics fm = g2d.getFontMetrics();

        // Set color to black
        g2d.setColor(Color.BLACK);

        // Set margins
        int leftMargin = 75; // Margin from the left
        int topMargin = 90;  // Margin from the top

        int rightMargin = backgroundImage.getWidth() - 15;
        int bottomMargin = 15;
        // Set line spacing
        int lineSpacing = -4; // Gap between two lines

        // Split the text into lines
        String[] lines = text.split("\n");
        int yOffset = topMargin; // Start from the top margin
        int y = 0;

        // Render each line of text
        // ...
 // Margin from the right

// ...

// Render each line of text
        // Render each line of text
        // Render each line of text
        int imageIndex = 0;
        for (String line : lines) {
            String[] words = line.split(" "); // Split the line into words
            int x = leftMargin; // Start from the left margin
            int lineWidth = 0; // Track the width of the line

            for (String word : words) {
                int wordWidth = fm.stringWidth(word + " "); // Calculate the width of the word with space
                // Check if the word fits within the available width
                if (lineWidth + wordWidth <= rightMargin - leftMargin) {
                    // Render the word
                    g2d.drawString(word + " ", x, yOffset);
                    x += wordWidth;
                    lineWidth += wordWidth;
                } else {
                    // Move to the next line and reset x-coordinate
                    yOffset += fm.getHeight() + lineSpacing;

                    if (yOffset + fm.getHeight() + lineSpacing > backgroundImage.getHeight() - bottomMargin) {
                        // Save the image
                        saveImage(image, ++imageIndex);
                        // Reset yOffset and start a new image
                        yOffset = topMargin;
                        x = leftMargin;
                        lineWidth = 0;
                        image = new BufferedImage(backgroundImage.getWidth(), backgroundImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
                        g2d = image.createGraphics();
                        g2d.drawImage(backgroundImage, 0, 0, null);

                        // Set font for rendering
                        g2d.setFont(customFont);
                        fm = g2d.getFontMetrics();

                        // Set color to black
                        g2d.setColor(Color.BLACK);
                        g2d.drawString(word + " ", x, yOffset);
                        x += wordWidth;
                        lineWidth += wordWidth;
                    } else {
                        x = leftMargin;
                        lineWidth = 0;
                        // Render the word on the next line
                        g2d.drawString(word + " ", x, yOffset);
                        x += wordWidth;
                        lineWidth += wordWidth;
                    }
                }
            }
            yOffset += fm.getHeight() + lineSpacing;
        }

        saveImage(image,++imageIndex);



// ...


        // Dispose of the Graphics2D object
        g2d.dispose();

        // Save the image to a file
//        try {
//            File outputImage = new File("images/"+System.currentTimeMillis() + "_output.png");
//            ImageIO.write(image, "png", outputImage);
//            System.out.println("Image saved successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static Font loadCustomFont(String fontPath, int fontSize) {
        Font customFont = null;
        try {
            // Load custom font from file
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(Font.PLAIN, fontSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            // Register the custom font
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return customFont;
    }
}
