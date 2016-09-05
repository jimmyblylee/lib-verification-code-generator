/**
 * Project Name : vc-simple-image <br>
 * File Name : VerificationCodeUtils.java <br>
 * Package Name : com.lee.util <br>
 * Create Time : Sep 2, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 * Copyright © 2006, 2016, Jimmybly Lee. All rights reserved.
 */
package com.lee.util;

import java.io.IOException;
import java.io.OutputStream;

import com.lee.vcg.impl.VcgSimpleImageImpl;

/**
 * ClassName : VerificationCodeUtils <br>
 * Description : generate simple image and provide the verification code <br>
 * using the font of "Algerian", And there should be the font in the OS <br>
 * Create Time : Sep 2, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 *
 */
public class VerificationCodeUtils {

    /**
     * Description : generate verification code and output the verification image into the given output stream <br>
     * Create Time: Sep 2, 2016 <br>
     * Create by : jimmyblylee@126.com <br>
     *
     * @param imageWidth
     *            the width of the generated image
     * @param imageHeight
     *            the height of the generated image
     * @param os
     *            the output stream that you want to put the image into
     * @param codeLength
     *            the random verification code length
     * @return the verification code
     * @throws IOException
     *             if there is some problem while writing the image
     */
    public static String generateVerificationCode(int imageWidth, int imageHeight, OutputStream os, int codeLength) throws IOException {
        // TODO get it from class name and make a generator selection logic
        return new VcgSimpleImageImpl().generateVerificationCode(imageWidth, imageHeight, os, codeLength);
    }
}
