
#include <cstdlib>
#include <iostream>
#include <jni.h>
#include <string>

#include "HEPREP/HepRepAttValue.h"
#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepInstance.h"
#include "HEPREP/HepRepAttributeListener.h"

#include "JHepRepIterator.hh"

using namespace HEPREP;
using namespace std;


JNIEXPORT JHepRepIterator::JHepRepIterator(HepRepFactory * factory, jobject obj) {
}

JNIEXPORT JHepRepIterator::~JHepRepIterator() {
}

JNIEXPORT HepRepInstance * JHepRepIterator::nextInstance() {
    return NULL;
}

JNIEXPORT bool JHepRepIterator::hasNext() {
    return false;
}

JNIEXPORT bool JHepRepIterator::addHepRepIteratorListener(string name, HepRepAttributeListener * listener) {
    return false;
}

JNIEXPORT void JHepRepIterator::removeHepRepIteratorListener(string name, HepRepAttributeListener * listener) {
}

//JNIEXPORT HepRepAttValue * JHepRepIterator::getAttValue(std::string key) {
//    return NULL;
//}
