# Class to encapsulate a zip file.

This class provides a method to determine if an input stream contains a zip file.
It also contains a wrapper for the ZipInputStream that allows an entry to be 
processed as if it were a stand-alone uncompressed stream. It does this by 
overriding the close method to not close the stream but to close the current
entry. The read method is passed to the underlying ZipInputStream.
