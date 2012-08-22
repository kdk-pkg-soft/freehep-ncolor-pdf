#include <cassert>

#include "HEPREP/HepRepFactory.h"

#include "JHepRepRef.hh"
#include "JHepRepFactory.hh"
#include "JNIUtil.hh"

using namespace HEPREP;

JNIEXPORT JHepRepRef::JHepRepRef(HepRepFactory* f, jobject obj) {
    JHepRepFactory* jfactory = dynamic_cast<JHepRepFactory*>(f);
    assert(jfactory != NULL);

    factory = jfactory;
    ref = obj;
    env = factory->getEnv();
    cls = env->GetObjectClass(obj);
}

JNIEXPORT JHepRepRef::~JHepRepRef() {
}

jobject JHepRepRef::getRef() {
    return ref;
}
