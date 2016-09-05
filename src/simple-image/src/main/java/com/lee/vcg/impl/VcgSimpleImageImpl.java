/**
 * Project Name : vc-simple-image <br>
 * File Name : VcgSimpleImageImpl.java <br>
 * Package Name : com.lee.vcg.impl <br>
 * Create Time : Sep 4, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 * Copyright © 2006, 2016, Jimmybly Lee. All rights reserved.
 */
package com.lee.vcg.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import com.lee.vcg.VerificationCodeGenerator;

/**
 * ClassName : VcgSimpleImageImpl <br>
 * Description : simple image implementation of {@link VerificationCodeGenerator} <br>
 * Create Time : Sep 4, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 *
 */
public class VcgSimpleImageImpl implements VerificationCodeGenerator {

    /** sources which the verification code will be generated random from */
    private final String VERIFICATION_CODE_SOURCES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    private Random random = new Random();
    /**
     * Description : generate random verification code with given size by <b>default</b> sources {@code "23456789ABCDEFGHJKLMNPQRSTUVWXYZ"} <br>
     * Create Time: Sep 2, 2016 <br>
     * Create by : jimmyblylee@126.com <br>
     *
     * @param codeLength
     *            the random verification code length
     * @return the random code string
     */
    private String generateVerificationCode(int codeLength) {
        return generateVerificationCode(codeLength, VERIFICATION_CODE_SOURCES);
    }

    /**
     * Description : generate random verification code with given size and given sources <br>
     * Create Time: Sep 2, 2016 <br>
     * Create by : jimmyblylee@126.com <br>
     *
     * @param codeLength
     *            the random verification code length
     * @param sources
     *            which the verification code will be be generated random from
     * @return the random code string
     */
    private String generateVerificationCode(int codeLength, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFICATION_CODE_SOURCES;
        }
        int sourcesLength = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder randomCode = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            randomCode.append(sources.charAt(rand.nextInt(sourcesLength - 1)));
        }
        return randomCode.toString();
    }

    /* (non-Javadoc)
     * @see com.lee.vcg.VerificationCodeGenerator#generateVerificationCode(java.lang.Integer, java.lang.Integer, java.io.OutputStream, java.lang.Integer)
     */
    @Override
    public String generateVerificationCode(Integer imageWidth, Integer imageHeight, OutputStream os, Integer codeLength) throws IOException {
        String randomCode = generateVerificationCode(codeLength);
        outputImage(imageWidth, imageHeight, os, randomCode);
        return randomCode;
    }

    private void outputImage(int imageWidth, int imageHeight, OutputStream os, String code) throws IOException {
        int verificationCodeLength = code.length();
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY); // set border color
        g2.fillRect(0, 0, imageWidth, imageHeight);

        Color c = getRandColor(200, 250);
        g2.setColor(c); // set background color
        g2.fillRect(0, 2, imageWidth, imageHeight - 4);

        // draw the spurious lines
        Random random = new Random();
        g2.setColor(getRandColor(160, 200)); // set the color of the line
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(imageWidth - 1);
            int y = random.nextInt(imageHeight - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // draw hot pixel
        float yawpRate = 0.05f;// Noise rate
        int area = (int) (yawpRate * imageWidth * imageHeight);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(imageWidth);
            int y = random.nextInt(imageHeight);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        // distorted the picture
        shear(g2, imageWidth, imageHeight, c);
        g2.setColor(getRandColor(100, 160));
        int fontSize = imageHeight - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verificationCodeLength; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (imageWidth / verificationCodeLength) * i + fontSize / 2,
                    imageHeight / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((imageWidth - 10) / verificationCodeLength) * i + 5, imageHeight / 2 + fontSize / 2 - 10);
        }

        g2.dispose();
        ImageIO.write(image, "jpg", os);
    }

    private Color getRandColor(int fc, int bc) {
        fc = fc > 255 ? 255 : fc;
        bc = bc > 255 ? 255 : bc;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);
        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    private void shearY(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(40) + 10; // 50;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }
}
