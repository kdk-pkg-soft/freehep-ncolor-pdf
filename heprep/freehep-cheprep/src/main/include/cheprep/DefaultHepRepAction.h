// Copyright FreeHEP, 2005.
#ifndef CHEPREP_DEFAULTHEPREPACTION_H
#define CHEPREP_DEFAULTHEPREPACTION_H 1

#include "cheprep/config.h"

#include <string>

#include "HEPREP/HepRepAction.h"

/**
 * @author Mark Donszelmann
 * @version $Id: DefaultHepRepAction.h 8617 2006-08-16 07:39:12Z duns $
 */
namespace cheprep {

class DefaultHepRepAction : public virtual HEPREP::HepRepAction {

    private:
        std::string name;
        std::string expression;

    public:
        DefaultHepRepAction(std::string name, std::string expression);
        ~DefaultHepRepAction();

        std::string getName();
        std::string getExpression();
        HEPREP::HepRepAction* copy();
};

} // cheprep


#endif
