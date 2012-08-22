// Copyright FreeHEP, 2005.
#ifndef CHEPREP_DEFAULTHEPREPATTDEF_H
#define CHEPREP_DEFAULTHEPREPATTDEF_H 1

#include "cheprep/config.h"

#include <string>

#include "HEPREP/HepRepAttDef.h"

/**
 * @author Mark Donszelmann
 * @version $Id: DefaultHepRepAttDef.h 8617 2006-08-16 07:39:12Z duns $
 */
namespace cheprep {

class DefaultHepRepAttDef : public virtual HEPREP::HepRepAttDef {

    private:
        std::string name, desc, category, extra;

    public:
        DefaultHepRepAttDef(std::string name, std::string desc, std::string category, std::string extra);
        ~DefaultHepRepAttDef();

        HEPREP::HepRepAttDef* copy();
        std::string getName();
        std::string getLowerCaseName();
        std::string getDescription();
        std::string getCategory();
        std::string getExtra();
};

} // cheprep


#endif
