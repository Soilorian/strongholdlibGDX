package org.example.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Captcha {


//    String[] picture = {"", "", "", "", "", "", ""};
//
//    String[][] NumbersPicture = {
//            {"  ###   ",
//                    " #   #  ",
//                    "#     # ",
//                    "#     # ",
//                    "#     # ",
//                    " #   #  ",
//                    "  ###   "},
//            {"  #     ",
//                    " ##     ",
//                    "# #     ",
//                    "  #     ",
//                    "  #     ",
//                    "  #     ",
//                    "#####   "},
//            {" #####  ",
//                    "      # ",
//                    "      # ",
//                    " #####  ",
//                    "#       ",
//                    "#       ",
//                    "####### "},
//            {" #####  ",
//                    "      # ",
//                    "      # ",
//                    " #####  ",
//                    "      # ",
//                    "      # ",
//                    " #####  "},
//            {"#       ",
//                    "#    #  ",
//                    "#    #  ",
//                    "#    #  ",
//                    "####### ",
//                    "     #  ",
//                    "     #  "},
//            {"####### ",
//                    "#       ",
//                    "#       ",
//                    "######  ",
//                    "      # ",
//                    "      # ",
//                    " #####  "},
//            {" #####  ",
//                    "#       ",
//                    "#       ",
//                    "######  ",
//                    "#     # ",
//                    "#     # ",
//                    " #####  "},
//            {"####### ",
//                    "#    #  ",
//                    "    #   ",
//                    "   #    ",
//                    "  #     ",
//                    "  #     ",
//                    "  #     "},
//            {" #####  ",
//                    "#     # ",
//                    "#     # ",
//                    " #####  ",
//                    "#     # ",
//                    "#     # ",
//                    " #####  "},
//            {" #####  ",
//                    "#     # ",
//                    "#     # ",
//                    " ###### ",
//                    "      # ",
//                    "      # ",
//                    " #####  "}
//    };
//    private int howManyNumber;
//    private Random random;
//    private int result = 0;
//
//
//    public Captcha() {
//        generateNewResult();
//    }
//
//
//    public void generateNewResult() {
//        random = new Random();
//        howManyNumber = random.nextInt(5) + 4;
//
//        for (int i = 0; i < howManyNumber; i++)
//            addNumberToPicture(random.nextInt(10));
//        randomNoise();
//    }
//
//    public void addNumberToPicture(int number) {
//        result *= 10;
//        result += number;
//        for (int i = 0; i < 7; i++)
//            picture[i] += NumbersPicture[number][i];
//
//    }
//
//    public void printPicture() {
//        for (int i = 0; i < 7; i++)
//            System.out.println(picture[i]);
//        Graphics
//    }
//
//    public void randomNoise() {
//        int noiseCounts = random.nextInt(50) + 20;
//        int whichLine, whichChar;
//        for (int i = 0; i < noiseCounts; i++) {
//            whichLine = random.nextInt(7);
//            whichChar = random.nextInt(howManyNumber * 8);
//            picture[whichLine] = picture[whichLine].substring(0, whichChar) + "*" + picture[whichLine].substring(whichChar + 1);
//        }
//    }
//
//    public boolean checkCaptcha(int input) {
//        return result == input;
//    }

    private String captcha;

    public Captcha() {
        generateCaptcha();
    }

    public void generateCaptcha() {
        captcha = generateRandomCaptchaText();
        try {
            BufferedImage bi = textToImage(captcha);
            File outputfile = new File("saved.png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            // handle exception
        }

    }

    public boolean isFilledCaptchaValid(String input) {
        return input.matches(captcha);
    }

    private String generateRandomCaptchaText() {
        int size = (int) (Math.random() * 4 + 4);
        StringBuilder captcha = new StringBuilder();
        String lettersAndNumbers = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        while (captcha.length() < size) {
            int character = (int) (Math.random() * 62);
            captcha.append(lettersAndNumbers.charAt(character));
        }
        return captcha.toString();
    }

    private BufferedImage textToImage(String displayCode) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 30);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(displayCode);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
                RenderingHints.VALUE_DITHER_DISABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_DEFAULT);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        g2d.drawString(displayCode, 0, fm.getAscent());
        makeNoise(g2d, width, height);
        g2d.dispose();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return img;
    }

    private Point generateRandomPoint(int width, int height) {
        int side = (int) (Math.random() * 3); //0=top, 1=bot, 2=left, 3=right

        int x = 0;
        int y = 0;

        switch (side) {
            case 0:
                x = (int) (Math.random() * width);
                break;
            case 1:
                y = height;
                x = (int) (Math.random() * width);
            case 2:
                y = (int) (Math.random() * height);
                x = 0;
                break;
            case 3:
                y = (int) (Math.random() * height);
                x = width;
                break;
        }

        return new Point(x, y);
    }

    private void drawCircles(Graphics2D g2d, int width, int height) {
        for (int i = 0; i < 5; i++) {
            int x = (int) (Math.random() * 10);
            g2d.fillOval((int) (Math.random() * width), (int) (Math.random() * height), x, x);
        }
    }

    private void makeNoise(Graphics2D g2d, int width, int height) {
        drawCircles(g2d, width, height);
        for (int i = 0; i < 5; i++) {
            Point rand1 = generateRandomPoint(width, height);
            Point rand2 = generateRandomPoint(width, height);
            g2d.drawLine(rand1.x, rand1.y, rand2.x, rand2.y);
        }
    }

}