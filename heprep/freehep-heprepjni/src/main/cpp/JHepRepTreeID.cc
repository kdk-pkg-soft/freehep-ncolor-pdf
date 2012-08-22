
#include <cstdlib>
#include <iostream>

#include "JHepRepTreeID.hh"
#include "JHepRepRef.hh"

using namespace HEPREP;

JNIEXPORT JHepRepTreeID::JHepRepTreeID(HepRepFactory* f, jobject obj)
        : JHepRepRef(f, obj) {
}

JNIEXPORT JHepRepTreeID::~JHepRepTreeID() {
}

JNIEXPORT std::string JHepRepTreeID::getQualifier() {
    return NULL;
}

JNIEXPORT void JHepRepTreeID::setQualifier(std::string qualifier) {
}

JNIEXPORT std::string JHepRepTreeID::getName() {
    return NULL;
}

JNIEXPORT std::string JHepRepTreeID::getVersion() {
    return NULL;
}
