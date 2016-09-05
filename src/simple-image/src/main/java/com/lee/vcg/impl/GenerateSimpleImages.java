/**
 * Project Name : vc-simple-image <br>
 * File Name : GenerateSimpleImages.java <br>
 * Package Name : com.lee.vcg.impl <br>
 * Create Time : Sep 5, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 * Copyright © 2006, 2016, Jimmybly Lee. All rights reserved.
 */
package com.lee.vcg.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName : GenerateSimpleImages <br>
 * Description : generate images to the given path <br>
 * Create Time : Sep 5, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 *
 */
public class GenerateSimpleImages {

    /**
     * Description : generate images to the given path by parameter <br>
     * Create Time: Sep 5, 2016 <br>
     * Create by : jimmyblylee@126.com <br>
     *
     * @param args [folder path]
     */
    public static void main(String[] args) {
        if (args == null || args.length < 1) {
            System.err.println("please input the path you want to output the images");
            return;
        }
        String path = args[0];
        // test folder
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String now = new SimpleDateFormat("MM.dd.HH.mm").format(new Date());
        VcgSimpleImageImpl generator = new VcgSimpleImageImpl();
        for (int i = 0; i < 20; i++) {
            String filePath = path + (path.endsWith("/") || path.endsWith("\\") ? "" : "/") + "test_image_" + now + "_" + (i < 9 ? "0" : "") + (i + 1) + ".png";
            try {
                generator.generateVerificationCode(200, 80, new FileOutputStream(filePath), 6);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
