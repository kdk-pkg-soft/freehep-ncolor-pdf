#include <cassert>

#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepPoint.h"
#include "HEPREP/HepRepInstance.h"

#include "JHepRepPoint.hh"
#include "JHepRepInstance.hh"
#include "JHepRepFactory.hh"

#include "JNIUtil.hh"
#include "JNIMacros.hh"

using namespace HEPREP;

JNIEXPORT JHepRepInstance::JHepRepInstance(HepRepFactory *f, jobject obj)
        : JHepRepAttribute(f, obj) {

    GETMETHOD("JHepRepInstance", addPointMethod,
              "addPoint", "(Lhep/graphics/heprep/HepRepPoint;)V" )
    GETMETHOD("JHepRepInstance", addInstanceMethod,
              "addInstance", "(Lhep/graphics/heprep/HepRepInstance;)V" )
}
JNIEXPORT JHepRepInstance::~JHepRepInstance() {
	env->DeleteLocalRef(ref);
}

JNIEXPORT void JHepRepInstance::overlay(HepRepInstance * instance) {
}

JNIEXPORT void JHepRepInstance::addPoint(HepRepPoint *point) {
    JHepRepPoint* jpoint = dynamic_cast<JHepRepPoint*>(point);
    assert(jpoint != NULL);

	env->CallVoidMethod(ref, addPointMethod, jpoint->getRef());

	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepInstance::addInstance(HepRepInstance *instance) {
    JHepRepInstance* jinstance = dynamic_cast<JHepRepInstance*>(instance);
    assert(jinstance != NULL);

	env->CallVoidMethod(ref, addInstanceMethod, jinstance->getRef());

	JNIUtil::checkExceptions(env);
}

JNIEXPORT HepRepType * JHepRepInstance::getType() {
    return NULL;
}

JNIEXPORT void JHepRepInstance::removeInstance(HepRepInstance * instance) {
}


JNIEXPORT std::vector<HepRepInstance*> JHepRepInstance::getInstances() {
    std::vector<HepRepInstance*> list;
    return list;
}


JNIEXPORT std::vector<HepRepPoint*> JHepRepInstance::getPoints() {
    std::vector<HepRepPoint*> list;    
    return list;
}

JNIEXPORT HepRepInstance * JHepRepInstance::getSuperInstance() {
    return NULL;
}

JNIEXPORT HepRepInstance * JHepRepInstance::copy(HepRepTypeTree * typeTree, HepRepInstance * parent, HepRepSelectFilter * filter) {
    return NULL;
}

JNIEXPORT HepRepInstance * JHepRepInstance::copy(HepRepTypeTree * typeTree, HepRepInstanceTree * parent, HepRepSelectFilter * filter) {
    return NULL;
}


