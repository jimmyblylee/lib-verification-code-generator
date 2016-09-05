/**
 * Project Name : vc-simple-image <br>
 * File Name : VerificationCodeGenerator.java <br>
 * Package Name : com.lee.vcg <br>
 * Create Time : Sep 4, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 * Copyright © 2006, 2016, Jimmybly Lee. All rights reserved.
 */
package com.lee.vcg;

import java.io.IOException;
import java.io.OutputStream;

/**
 * ClassName : VerificationCodeGenerator <br>
 * Description : API of Verification Code Generator <br>
 * Create Time : Sep 4, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 * TODO make a api project
 *
 */
public interface VerificationCodeGenerator {

    /**
     * Description : generate verification code and output the verification image into the given output stream <br>
     * Create Time: Sep 4, 2016 <br>
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
    public String generateVerificationCode(Integer imageWidth, Integer imageHeight, OutputStream os, Integer codeLength) throws IOException;
}
