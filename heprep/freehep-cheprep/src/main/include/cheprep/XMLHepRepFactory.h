// Copyright FreeHEP, 2005.
#ifndef XMLHEPREPFACTORY_H
#define XMLHEPREPFACTORY_H 1

#include <string>
#include <iostream>

#include "HEPREP/HepRepReader.h"
#include "HEPREP/HepRepWriter.h"

#include "DefaultHepRepFactory.h"

/**
 * @author Mark Donszelmann
 * @version $Id: XMLHepRepFactory.h 8617 2006-08-16 07:39:12Z duns $
 */
namespace cheprep {

class XMLHepRepFactory : public DefaultHepRepFactory {

    public:
        XMLHepRepFactory();
        ~XMLHepRepFactory();

        HEPREP::HepRepReader* createHepRepReader (std::istream* in);
        HEPREP::HepRepReader* createHepRepReader (std::string filename);
        HEPREP::HepRepWriter* createHepRepWriter (std::ostream* out, bool randomAccess, bool compress);
};

} // cheprep


#endif
