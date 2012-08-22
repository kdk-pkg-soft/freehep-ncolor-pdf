
#include <cstdlib>
#include <iostream>
#include <jni.h>
#include <string>

#include "HEPREP/HepRepAttValue.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepAttValue.hh"

using namespace HEPREP;


JNIEXPORT JHepRepAttValue::JHepRepAttValue(HepRepFactory* f, jobject obj) {
}

JNIEXPORT JHepRepAttValue::~JHepRepAttValue() {
}

JNIEXPORT HepRepAttValue * JHepRepAttValue::copy() {
    return NULL;
}

JNIEXPORT std::string JHepRepAttValue::getName() {
    return NULL;
}

JNIEXPORT std::string JHepRepAttValue::getLowerCaseName() {
    return NULL;
}

JNIEXPORT int JHepRepAttValue::getType() {
    return -1;
}

JNIEXPORT std::string JHepRepAttValue::getTypeName() {
    return NULL;
}

JNIEXPORT int JHepRepAttValue::showLabel() {
    return -1;
}

JNIEXPORT std::string JHepRepAttValue::getString() {
    return NULL;
}

JNIEXPORT std::string JHepRepAttValue::getLowerCaseString() {
    return NULL;
}

JNIEXPORT std::string JHepRepAttValue::getAsString() {
    return NULL;
}

JNIEXPORT HEPREP::int64 JHepRepAttValue::getLong() {
    return 0;
}

JNIEXPORT int JHepRepAttValue::getInteger() {
    return 0;
}

JNIEXPORT double JHepRepAttValue::getDouble() {
    return 0.0;
}

JNIEXPORT bool JHepRepAttValue::getBoolean() {
    return false;
}
