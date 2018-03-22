package edu.temple.cla.policydb.zipentrystream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class ZipEntryInputStream extends InputStream {

    public static boolean isZipFile(File file) throws IOException {
        boolean isIt;
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] firstTwoBytes = new byte[2];
            inputStream.read(firstTwoBytes);
            isIt = firstTwoBytes[0] == 'P' && firstTwoBytes[1] == 'K';
        }
        return isIt;
    }
    
    private final ZipInputStream zipInputStream;
    
    public ZipEntryInputStream(ZipInputStream zipInputStream) {
        this.zipInputStream = zipInputStream;
    }
    
    @Override
    public int read() throws IOException {
        return zipInputStream.read();
    }
    
    @Override
    public void close() throws IOException {
        zipInputStream.closeEntry();
    }

}
