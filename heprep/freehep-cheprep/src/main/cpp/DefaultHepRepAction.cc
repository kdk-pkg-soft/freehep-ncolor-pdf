// Copyright FreeHEP, 2005.

#include <iostream>

#include "cheprep/DefaultHepRepAction.h"

using namespace std;
using namespace HEPREP;

/**
 * @author Mark Donszelmann
 * @version $Id: DefaultHepRepAction.cc 8617 2006-08-16 07:39:12Z duns $
 */
namespace cheprep {

DefaultHepRepAction::DefaultHepRepAction(string name, string expression)
    : name(name), expression(expression) {
}

DefaultHepRepAction::~DefaultHepRepAction() {
}

string DefaultHepRepAction::getName() {
    return name;
}

string DefaultHepRepAction::getExpression() {
    return expression;
}

HepRepAction* DefaultHepRepAction::copy() {
    return new DefaultHepRepAction(name, expression);
}

} // cheprep

