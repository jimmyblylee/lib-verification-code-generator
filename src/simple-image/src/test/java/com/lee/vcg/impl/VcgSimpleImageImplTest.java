/**
 * Project Name : vc-simple-image <br>
 * File Name : VcgSimpleImageImplTest.java <br>
 * Package Name : com.lee.vcg.impl <br>
 * Create Time : Sep 5, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 * Copyright © 2006, 2016, Jimmybly Lee. All rights reserved.
 */
package com.lee.vcg.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsNot.*;

import org.junit.Test;

/**
 * ClassName : VcgSimpleImageImplTest <br>
 * Description : Unit Test for {@link VcgSimpleImageImpl} <br>
 * Create Time : Sep 5, 2016 <br>
 * Create by : jimmyblylee@126.com <br>
 *
 */
public class VcgSimpleImageImplTest {

    private static String VCG_TEMP_DIR = new File(System.getProperty("java.io.tmpdir"), "vcg").getPath();
    private static String now = new SimpleDateFormat("MM.dd.HH.mm").format(new Date());

    @Test
    public void testGenerateVerificationCode() {
        createTempDirIfNeeds(VCG_TEMP_DIR);
        for (int i = 0; i < 20; i++) {
            String fileName = getTempFileName(i);
            String filePath = VCG_TEMP_DIR + File.separator + fileName;
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(new File(filePath));
            } catch (FileNotFoundException e) {
                fail("can not create the file of " + filePath);
            }
            try {
                new VcgSimpleImageImpl().generateVerificationCode(200, 80, fos, 6);
            } catch (IOException e) {
                fail("can not write image into " + filePath);
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    fail();
                }
            }
        }
        
        for (int i = 0; i < 20; i++) {
            String fileName = getTempFileName(i);
            String filePath = VCG_TEMP_DIR + File.separator + fileName;
            File file = new File(filePath);
            assertTrue("new output verification code image file should exists.", file.exists());
            assertThat("new output verification code image file size should greater than 0.", file.length(), not(0L));
            assertTrue("new output verification code image file should be delete after test.", file.delete());
        }
        
        assertTrue("test temp folder should be deleted.", new File(VCG_TEMP_DIR).delete());
    }

    private String getTempFileName(int number) {
        return "test_image_" + now + "_" + (number < 9 ? "0" : "") + (number + 1) + ".png";
    }
    
    private void createTempDirIfNeeds(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            if (folder.isFile()) {
                folder.delete();
                new File(path).mkdirs();
            }
        }
    }
}
