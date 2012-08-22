// Copyright FreeHEP, 2005.

#include <iostream>
#include <fstream>

#include "cheprep/XMLHepRepWriter.h"
#include "cheprep/XMLHepRepFactory.h"

using namespace std;
using namespace HEPREP;

/**
 * @author Mark Donszelmann
 * @version $Id: XMLHepRepFactory.cc 8617 2006-08-16 07:39:12Z duns $
 */
namespace cheprep {


XMLHepRepFactory::XMLHepRepFactory() {
}

XMLHepRepFactory::~XMLHepRepFactory() {
}

HepRepReader* XMLHepRepFactory::createHepRepReader (istream*) {
    cerr << "XMLHepRepFactory::createHepRepReader not implemented" << endl;
    return NULL;
}

HepRepReader* XMLHepRepFactory::createHepRepReader (std::string) {
    cerr << "XMLHepRepFactory::createHepRepReader not implemented" << endl;
    return NULL;
}

HepRepWriter* XMLHepRepFactory::createHepRepWriter(ostream* out, bool randomAccess, bool compress) {
    return new XMLHepRepWriter(out, randomAccess, compress);
}

} // cheprep

