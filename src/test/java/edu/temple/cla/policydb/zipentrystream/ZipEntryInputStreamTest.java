/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.policydb.zipentrystream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Paul
 */
public class ZipEntryInputStreamTest {
    
    public ZipEntryInputStreamTest() {
    }
    
    @Test
    public void zipEntryInputStreamTest() throws Exception {
        File testFile = new File("src/main/resources/Dir1.zip");
        assertTrue(ZipEntryInputStream.isZipFile(testFile));
        processZipEntryStream(new FileInputStream(testFile));
    }
    
    public void processZipEntryStream(InputStream in) throws Exception {
        ZipInputStream zipInputStream = new ZipInputStream(in);
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            System.out.println("Processing entry " + entry);
            BufferedInputStream zipEntry = new BufferedInputStream(new ZipEntryInputStream(zipInputStream));
            if (ZipEntryInputStream.isZipEntry(zipEntry)){
                System.out.println("Found nested zip file");
                processZipEntryStream(zipEntry);
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(zipEntry));
                reader.lines().forEach(System.out::println);
            }
        }
    }
    
    @Test
    public void zipEntryDirectoryTest() throws Exception {
        File testFile = new File("src/main/resources");
        if (testFile.isDirectory()) {
            System.out.println("Found directory");
            File[] files = testFile.listFiles();
            for (File file:files) {
                if (ZipEntryInputStream.isZipFile(file)) {
                    processZipEntryStream(new FileInputStream(file));
                }
            }
        }
    }
    
}
