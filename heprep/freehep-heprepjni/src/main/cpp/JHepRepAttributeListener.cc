
#include <cstdlib>
#include <iostream>
#include <jni.h>
#include <string>
#include <vector>

#include "HEPREP/HepRepFactory.h"

#include "JHepRepAttributeListener.hh"

using namespace HEPREP;


JNIEXPORT JHepRepAttributeListener::JHepRepAttributeListener(HepRepFactory * factory, jobject obj) {
}

JNIEXPORT JHepRepAttributeListener::~JHepRepAttributeListener() {
}

JNIEXPORT void JHepRepAttributeListener::setAttribute(HEPREP::HepRepInstance* instance, std::string key, std::string value, std::string lowerCaseValue, int showLabel) {
}

JNIEXPORT void JHepRepAttributeListener::setAttribute(HEPREP::HepRepInstance* instance, std::string key, std::vector<double> value, int showLabel) {
}

JNIEXPORT void JHepRepAttributeListener::setAttribute(HEPREP::HepRepInstance* instance, std::string key, long value, int showLabel) {
}

JNIEXPORT void JHepRepAttributeListener::setAttribute(HEPREP::HepRepInstance* instance, std::string key, int value, int showLabel) {
}

JNIEXPORT void JHepRepAttributeListener::setAttribute(HEPREP::HepRepInstance* instance, std::string key, double value, int showLabel) {
}

JNIEXPORT void JHepRepAttributeListener::setAttribute(HEPREP::HepRepInstance* instance, std::string key, bool value, int showLabel) {
}

JNIEXPORT void JHepRepAttributeListener::removeAttribute(HEPREP::HepRepInstance* instance, std::string key) {
}
