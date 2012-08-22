// Copyright FreeHEP, 2005.

#include "cheprep/GZIPOutputStreamBuffer.h"
#include "cheprep/GZIPOutputStream.h"

/**
 * @author Mark Donszelmann
 * @version $Id: GZIPOutputStream.cc 8617 2006-08-16 07:39:12Z duns $
 */
namespace cheprep {

    using namespace std;

    GZIPOutputStream::GZIPOutputStream(ostream &os)
                : std::ostream(NULL) {
      
        buffer = new GZIPOutputStreamBuffer(os.rdbuf()); 
        init(buffer);   
    }


    void GZIPOutputStream::setFilename(const string &filename) {
        buffer->setFilename(filename);
    }

    void GZIPOutputStream::setComment(const string &comment) {
        buffer->setComment(comment);
    }

    void GZIPOutputStream::close() {
        buffer->close();
    }


    GZIPOutputStream::~GZIPOutputStream() {
        delete buffer;
    }

} // cheprep
