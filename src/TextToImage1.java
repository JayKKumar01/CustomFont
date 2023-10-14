import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextToImage1 {

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
        // Set line spacing
        int lineSpacing = -4; // Gap between two lines

        // Split the text into lines
        String[] lines = text.split("\n");
        int yOffset = topMargin; // Start from the top margin

        // Render each line of text
        // ...
 // Margin from the right

// ...

// Render each line of text
        for (String line : lines) {
            int x = leftMargin; // Start from the left margin
            int y = yOffset;
            int lineWidth = 0; // Track the width of the line

            // Render text with fallback font for unsupported characters
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                if (customFont.canDisplay(ch)) {
                    int charWidth = fm.charWidth(ch);
                    // Check if the character fits within the available width
                    if (lineWidth + charWidth <= rightMargin - leftMargin) {
                        // Render the character
                        g2d.drawString(String.valueOf(ch), x, y);
                        x += charWidth;
                        lineWidth += charWidth;
                    } else {
                        // Move to the next line and reset x-coordinate
                        y += fm.getHeight() + lineSpacing;
                        x = leftMargin;
                        lineWidth = 0;
                        // Render the character on the next line
                        g2d.drawString(String.valueOf(ch), x, y);
                        x += charWidth;
                        lineWidth += charWidth;
                    }
                } else {
                    // Handle unsupported characters (fallback font logic)
                    g2d.setFont(fallbackFont);
                    FontMetrics fallbackFm = g2d.getFontMetrics();
                    int charWidth = fallbackFm.charWidth(ch);
                    if (lineWidth + charWidth <= rightMargin - leftMargin) {
                        // Render the character with fallback font
                        g2d.drawString(String.valueOf(ch), x, y);
                        x += charWidth;
                        lineWidth += charWidth;
                    } else {
                        // Move to the next line and reset x-coordinate
                        y += fm.getHeight() + lineSpacing;
                        x = leftMargin;
                        lineWidth = 0;
                        // Render the character with fallback font on the next line
                        g2d.drawString(String.valueOf(ch), x, y);
                        x += charWidth;
                        lineWidth += charWidth;
                    }
                    g2d.setFont(customFont);
                }
            }

            // Move to the next line with spacing
            yOffset += fm.getHeight() + lineSpacing;
        }

// ...


        // Dispose of the Graphics2D object
        g2d.dispose();

        // Save the image to a file
        try {
            File outputImage = new File(System.currentTimeMillis() + "_output.png");
            ImageIO.write(image, "png", outputImage);
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
