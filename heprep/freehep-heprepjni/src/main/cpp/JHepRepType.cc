#include <cassert>

#include "HEPREP/HepRepType.h"

#include "JHepRepType.hh"
#include "JHepRepFactory.hh"
#include "JHepRepType.hh"

#include "JNIUtil.hh"
#include "JNIMacros.hh"

using namespace HEPREP;

JNIEXPORT JHepRepType::JHepRepType(HepRepFactory *f, jobject obj)
        : JHepRepDefinition(f, obj) {

	GETMETHOD("JHepRepType", addTypeMethod,
		      "addType", "(Lhep/graphics/heprep/HepRepType;)V" )
}

JNIEXPORT JHepRepType::~JHepRepType() {
	env->DeleteLocalRef(ref);
}

JNIEXPORT void JHepRepType::addType(HepRepType *type) {
    JHepRepType* jtype = dynamic_cast<JHepRepType*>(type);
    assert(jtype != NULL);

	env->CallVoidMethod(ref, addTypeMethod, jtype->getRef());

	JNIUtil::checkExceptions(env);
}

JNIEXPORT std::string JHepRepType::getName() {
    return NULL;
}

JNIEXPORT std::string JHepRepType::getFullName() {
    return (getSuperType() == NULL) ? getName() : getSuperType()->getFullName() + "/"+ getName();
}

JNIEXPORT std::string JHepRepType::getDescription() {
    return NULL;
}

JNIEXPORT void JHepRepType::setDescription(std::string description) {
}

JNIEXPORT std::string JHepRepType::getInfoURL() {
    return NULL;
}

JNIEXPORT void JHepRepType::setInfoURL(std::string description) {
}

JNIEXPORT HepRepType* JHepRepType::getSuperType() {
    return NULL;
}

JNIEXPORT std::vector<HepRepType *> JHepRepType::getTypeList() {
    std::vector<HepRepType*> v;
    return v;
}

JNIEXPORT HepRepType * JHepRepType::copy(HepRepType * parent) {
    return NULL;
}

