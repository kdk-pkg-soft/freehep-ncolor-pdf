
#include <cstdlib>
#include <iostream>
#include <jni.h>
#include <string>

#include "HEPREP/HepRepAttDef.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepAttDef.hh"

using namespace HEPREP;

JNIEXPORT JHepRepAttDef::JHepRepAttDef(HepRepFactory* f, jobject obj) {

}

JNIEXPORT JHepRepAttDef::~JHepRepAttDef() {
}

JNIEXPORT std::string JHepRepAttDef::getName() {
    return NULL;
}

JNIEXPORT std::string JHepRepAttDef::getLowerCaseName() {
    return NULL;
}

JNIEXPORT std::string JHepRepAttDef::getDescription() {
    return NULL;
}

JNIEXPORT std::string JHepRepAttDef::getCategory() {
    return NULL;
}

JNIEXPORT std::string JHepRepAttDef::getExtra() {
    return NULL;
}

JNIEXPORT HepRepAttDef * JHepRepAttDef::copy() {
    return NULL;
}
