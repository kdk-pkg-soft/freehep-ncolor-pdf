#include <cassert>

#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepType.h"

#include "JHepRepTypeTree.hh"
#include "JHepRepTreeID.hh"
#include "JHepRepFactory.hh"
#include "JHepRepType.hh"
#include "JNIUtil.hh"
#include "JNIMacros.hh"

using namespace HEPREP;
using namespace std;

JNIEXPORT JHepRepTypeTree::JHepRepTypeTree(HepRepFactory *f, jobject obj)
        : JHepRepTreeID(f, obj) {

    GETMETHOD("JHepRepTypeTree", addTypeMethod,
      		  "addType", "(Lhep/graphics/heprep/HepRepType;)V" )
}

JNIEXPORT JHepRepTypeTree::~JHepRepTypeTree() {
	env->DeleteLocalRef(ref);
}

JNIEXPORT void JHepRepTypeTree::addType(HepRepType *type) {
    JHepRepType* jtype = dynamic_cast<JHepRepType*>(type);
    assert(jtype != NULL);

	env->CallVoidMethod(ref, addTypeMethod, jtype->getRef());

	JNIUtil::checkExceptions(env);
}

JNIEXPORT std::vector<HepRepType *> JHepRepTypeTree::getTypeList() {
    vector<HepRepType*> v;
    return v;
}

JNIEXPORT HepRepTypeTree * JHepRepTypeTree::copy() {
    return NULL;
}

JNIEXPORT HepRepType * JHepRepTypeTree::getType(std::string name) {
    return NULL;
}

