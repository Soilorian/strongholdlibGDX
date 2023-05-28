package org.example.model;

import java.util.Random;

public class Captcha {
    String[] picture = {"", "", "", "", "", "", ""};
    String[][] NumbersPicture = {
            {"  ###   ",
                    " #   #  ",
                    "#     # ",
                    "#     # ",
                    "#     # ",
                    " #   #  ",
                    "  ###   "},
            {"  #     ",
                    " ##     ",
                    "# #     ",
                    "  #     ",
                    "  #     ",
                    "  #     ",
                    "#####   "},
            {" #####  ",
                    "      # ",
                    "      # ",
                    " #####  ",
                    "#       ",
                    "#       ",
                    "####### "},
            {" #####  ",
                    "      # ",
                    "      # ",
                    " #####  ",
                    "      # ",
                    "      # ",
                    " #####  "},
            {"#       ",
                    "#    #  ",
                    "#    #  ",
                    "#    #  ",
                    "####### ",
                    "     #  ",
                    "     #  "},
            {"####### ",
                    "#       ",
                    "#       ",
                    "######  ",
                    "      # ",
                    "      # ",
                    " #####  "},
            {" #####  ",
                    "#       ",
                    "#       ",
                    "######  ",
                    "#     # ",
                    "#     # ",
                    " #####  "},
            {"####### ",
                    "#    #  ",
                    "    #   ",
                    "   #    ",
                    "  #     ",
                    "  #     ",
                    "  #     "},
            {" #####  ",
                    "#     # ",
                    "#     # ",
                    " #####  ",
                    "#     # ",
                    "#     # ",
                    " #####  "},
            {" #####  ",
                    "#     # ",
                    "#     # ",
                    " ###### ",
                    "      # ",
                    "      # ",
                    " #####  "}
    };
    private int howManyNumber;
    private Random random;
    private int result = 0;


    public Captcha() {
        generateNewResult();
    }


    public void generateNewResult() {
        random = new Random();
        howManyNumber = random.nextInt(5) + 4;

        for (int i = 0; i < howManyNumber; i++)
            addNumberToPicture(random.nextInt(10));
        randomNoise();
    }

    public void addNumberToPicture(int number) {
        result *= 10;
        result += number;
        for (int i = 0; i < 7; i++)
            picture[i] += NumbersPicture[number][i];

    }

    public void printPicture() {
        for (int i = 0; i < 7; i++)
            System.out.println(picture[i]);
    }

    public void randomNoise() {
        int noiseCounts = random.nextInt(50) + 20;
        int whichLine, whichChar;
        for (int i = 0; i < noiseCounts; i++) {
            whichLine = random.nextInt(7);
            whichChar = random.nextInt(howManyNumber * 8);
            picture[whichLine] = picture[whichLine].substring(0, whichChar) + "*" + picture[whichLine].substring(whichChar + 1);
        }
    }

    public boolean checkCaptcha(int input) {
        return result == input;
    }
}