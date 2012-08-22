// Copyright FreeHEP, 2005.

#include <string>
#include <fstream>

#include "cheprep/ZipOutputStream.h"

/**
 * @author Mark Donszelmann
 * @version $Id: BHepRepTest.cpp 8617 2006-08-16 07:39:12Z duns $
 */

using namespace cheprep ;
using namespace std;

void writeFileToBHepRepOutputStream(ZipOutputStream& bheprep, const string& filename) ;

int main() {
    try {
        ofstream outfile("BHepRepTest.zip", ios::out | ios::binary);

        ZipOutputStream bheprep(outfile) ;

        writeFileToBHepRepOutputStream(bheprep, "test.xml") ;

        bheprep.close();
        outfile.close();

        return 0;
    } catch(exception &e) {
        cerr << "Exception caught in main() :" << endl ;
        cerr << e.what() << endl ;
    }
    return -1;
}

void writeFileToBHepRepOutputStream( ZipOutputStream& bheprep, const string& filename ) {
    bheprep.putNextEntry(filename, true) ;

    ifstream infile(filename.c_str(), ios::in | ios::binary) ;

    bheprep << infile.rdbuf() ;

    cerr << "ostream Stream state: "  ;
    cerr << "good() = " << bheprep.good() << ",\t" ;
    cerr << "fail() = " << bheprep.fail() << ",\t" ;
    cerr << "bad()  = " << bheprep.bad()  << ",\t" ;
    cerr << "eof()  = " << bheprep.eof()  << endl  ;

    cerr << "istream Stream state: "  ;
    cerr << "good() = " << infile.good() << ",\t" ;
    cerr << "fail() = " << infile.fail() << ",\t" ;
    cerr << "bad()  = " << infile.bad()  << ",\t" ;
    cerr << "eof()  = " << infile.eof()  << endl  ;

}
