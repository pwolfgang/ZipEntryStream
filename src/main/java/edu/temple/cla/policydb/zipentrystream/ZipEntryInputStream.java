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

public class ZipEntryInputStream extends InputStream {

    public static boolean isZipFile(File file) throws IOException {
        boolean isIt;
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            isIt = isZipEntry(inputStream);
        }
        return isIt;
    }

    public static boolean isZipEntry(final BufferedInputStream inputStream) throws IOException {
        byte[] firstTwoBytes = new byte[2];
        inputStream.mark(2);
        inputStream.read(firstTwoBytes);
        inputStream.reset();
        boolean isIt = firstTwoBytes[0] == 'P' && firstTwoBytes[1] == 'K';
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
