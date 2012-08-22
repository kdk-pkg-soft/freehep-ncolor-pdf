#include <cassert>

#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepInstanceTree.h"
#include "HEPREP/HepRepSelectFilter.h"
#include "HEPREP/HepRepTreeID.h"
#include "HEPREP/HepRep.h"

#include "JHepRepInstanceTree.hh"
#include "JHepRepTreeID.hh"
#include "JHepRepInstance.hh"

#include "JNIUtil.hh"
#include "JNIMacros.hh"

using namespace HEPREP;

JNIEXPORT JHepRepInstanceTree::JHepRepInstanceTree(HepRepFactory *f, jobject obj)
        : JHepRepTreeID(f, obj) {

    GETMETHOD("JHepRepInstanceTree", addInstanceMethod,
      		  "addInstance", "(Lhep/graphics/heprep/HepRepInstance;)V" )
    GETMETHOD("JHepRepInstanceTree", addInstanceTreeMethod,
      		  "addInstanceTree", "(Lhep/graphics/heprep/HepRepName;)V" )
}

JNIEXPORT JHepRepInstanceTree::~JHepRepInstanceTree() {
	env->DeleteLocalRef(ref);
}

JNIEXPORT void JHepRepInstanceTree::overlay(HepRepInstanceTree * instanceTree) {
}

JNIEXPORT void JHepRepInstanceTree::addInstance(HepRepInstance *instance) {
    JHepRepInstance* jinstance = dynamic_cast<JHepRepInstance*>(instance);
    assert(jinstance != NULL);

	env->CallVoidMethod(ref, addInstanceMethod, jinstance->getRef());

	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepInstanceTree::addInstanceTree(HepRepTreeID *treeID) {
    JHepRepTreeID* jtreeID= dynamic_cast<JHepRepTreeID*>(treeID);
    assert(jtreeID != NULL);

	env->CallVoidMethod(ref, addInstanceTreeMethod, jtreeID->getRef());

	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepInstanceTree::removeInstance(HepRepInstance * instance) {
}

JNIEXPORT std::vector<HepRepInstance *> JHepRepInstanceTree::getInstances() {
    std::vector<HepRepInstance *> list;
    return list;
}

JNIEXPORT HepRepTreeID * JHepRepInstanceTree::getTypeTree() {
    return NULL;
}

JNIEXPORT std::vector<HepRepTreeID *> JHepRepInstanceTree::getInstanceTreeList() {
    std::vector<HepRepTreeID *> v;
    return v;
}

JNIEXPORT HepRepInstanceTree * JHepRepInstanceTree::copy(HepRepTypeTree * typeTree, HepRepSelectFilter * filter) {
    return NULL;
}
