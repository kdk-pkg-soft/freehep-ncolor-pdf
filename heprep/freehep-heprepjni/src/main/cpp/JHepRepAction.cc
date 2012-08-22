
#include <cstdlib>
#include <iostream>

#include "HEPREP/HepRepFactory.h"

#include "JHepRepAction.hh"

using namespace HEPREP;


JNIEXPORT JHepRepAction::JHepRepAction(HepRepFactory *factory, jobject obj) {
}

JNIEXPORT JHepRepAction::~JHepRepAction() {
}

JNIEXPORT std::string JHepRepAction::getName() {
    return NULL;
}

JNIEXPORT std::string JHepRepAction::getExpression() {
    return NULL;
}

JNIEXPORT HepRepAction * JHepRepAction::copy() {
    return NULL;
}
