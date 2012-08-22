
#include "HEPREP/HepRepFactory.h"

#include "JHepRepPoint.hh"
#include "JHepRepFactory.hh"

using namespace HEPREP;
using namespace std;

JNIEXPORT JHepRepPoint::JHepRepPoint(HepRepFactory* f, jobject obj)
        : JHepRepAttribute(f, obj) {

}

JNIEXPORT JHepRepPoint::~JHepRepPoint() {
	env->DeleteLocalRef(ref);
}

JNIEXPORT double JHepRepPoint::getX() {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getY() {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getZ() {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getRho() {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getPhi() {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getTheta() {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getR() {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getEta() {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getX(double xVertex, double yVertex, double zVertex) {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getY(double xVertex, double yVertex, double zVertex) {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getZ(double xVertex, double yVertex, double zVertex) {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getRho(double xVertex, double yVertex, double zVertex) {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getPhi(double xVertex, double yVertex, double zVertex) {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getTheta(double xVertex, double yVertex, double zVertex) {
    return 0.0;
}


JNIEXPORT double JHepRepPoint::getR(double xVertex, double yVertex, double zVertex) {
    return 0.0;
}

JNIEXPORT double JHepRepPoint::getEta(double xVertex, double yVertex, double zVertex) {
    return 0.0;
}

JNIEXPORT vector<double>* JHepRepPoint::getXYZ(vector<double>* xyz) {
    return xyz;
}

JNIEXPORT HepRepInstance* JHepRepPoint::getInstance() {
    return NULL;
}

JNIEXPORT HepRepPoint* JHepRepPoint::copy(HepRepInstance* parent) {
    return NULL;
}
