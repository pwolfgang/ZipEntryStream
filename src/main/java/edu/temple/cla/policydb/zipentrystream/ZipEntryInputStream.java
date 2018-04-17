/* 
 * Copyright (c) 2018, Temple University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * All advertising materials features or use of this software must display 
 *   the following  acknowledgement
 *   This product includes software developed by Temple University
 * * Neither the name of the copyright holder nor the names of its 
 *   contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.temple.cla.policydb.zipentrystream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * This is a wrapper for a ZipInputStream that allows an entry to be treated
 * as an InputStream.
 * @author Paul Wolfgang
 */
public class ZipEntryInputStream extends InputStream {

    /**
     * Method to determine if a file is Zip File.  It examines the first two
     * bytes to see if they are the characters "PK".
     * @param file The file to be tested.
     * @return True if the file is a zip file.
     * @throws IOException 
     */
    public static boolean isZipFile(File file) throws IOException {
        boolean isIt;
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            isIt = isZipEntry(inputStream);
        }
        return isIt;
    }

    /**
     * Method to determine if an input stream is a Zip file. It examines the
     * first two bytes to see if they are the characters "PK"
     * @param inputStream A buffered input stream to be examined.
     * @return True if the stream is a zip file.
     * @throws IOException 
     */
    public static boolean isZipEntry(final BufferedInputStream inputStream) throws IOException {
        byte[] firstTwoBytes = new byte[2];
        inputStream.mark(2);
        inputStream.read(firstTwoBytes);
        inputStream.reset();
        boolean isIt = firstTwoBytes[0] == 'P' && firstTwoBytes[1] == 'K';
        return isIt;
    }
    
    /** The wrapped ZipInputStream */
    private final ZipInputStream zipInputStream;
    
    /** 
     * Constructor.
     * @param zipInputStream The stream to be wrapped by this class.
     */
    public ZipEntryInputStream(ZipInputStream zipInputStream) {
        this.zipInputStream = zipInputStream;
    }
    
    /**
     * Read the next byte. Calls read on the wrapped input stream.
     * @return The next byte as an int, or -1 if EOF. 
     * @throws IOException 
     */
    @Override
    public int read() throws IOException {
        return zipInputStream.read();
    }
    
    /**
     * Close the entry. Calls closeEntry on the wrapped ZipInputStream.
     * @throws IOException 
     */
    @Override
    public void close() throws IOException {
        zipInputStream.closeEntry();
    }

}
