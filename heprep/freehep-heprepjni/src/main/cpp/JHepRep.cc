#include <cassert>

#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepTypeTree.h"
#include "HEPREP/HepRepInstanceTree.h"

#include "JHepRep.hh"
#include "JHepRepFactory.hh"
#include "JHepRepTypeTree.hh"
#include "JHepRepInstanceTree.hh"
#include "JNIUtil.hh"
#include "JNIMacros.hh"

using namespace HEPREP;
using namespace std;

JNIEXPORT JHepRep::JHepRep(HepRepFactory *f, jobject obj)
        : JHepRepRef(f, obj) {

    GETMETHOD("JHepRep", addLayerMethod,
              "addLayer", "(Ljava/lang/String;)V" )
    GETMETHOD("JHepRep", addTypeTreeMethod,
              "addTypeTree", "(Lhep/graphics/heprep/HepRepTypeTree;)V" )
    GETMETHOD("JHepRep", addInstanceTreeMethod,
              "addInstanceTree", "(Lhep/graphics/heprep/HepRepInstanceTree;)V" )
}

JNIEXPORT JHepRep::~JHepRep() {
	env->DeleteLocalRef(ref);
}

JNIEXPORT void JHepRep::addLayer(string layer) {
	jstring jlayer;
	NEWSTRING(jlayer, layer)
    env->CallVoidMethod(ref, addLayerMethod, jlayer);
    DELSTRING(jlayer)
	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRep::addTypeTree(HepRepTypeTree *typeTree) {
    JHepRepTypeTree* jtypeTree = dynamic_cast<JHepRepTypeTree*>(typeTree);
    assert(jtypeTree != NULL);

	env->CallVoidMethod(ref, addTypeTreeMethod, jtypeTree->getRef());

	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRep::addInstanceTree(HepRepInstanceTree *instanceTree) {
    JHepRepInstanceTree* jinstanceTree = dynamic_cast<JHepRepInstanceTree*>(instanceTree);
    assert(jinstanceTree != NULL);

	env->CallVoidMethod(ref, addInstanceTreeMethod, jinstanceTree->getRef());

	JNIUtil::checkExceptions(env);
}

JNIEXPORT std::vector<std::string> JHepRep::getLayerOrder() {
    std::vector<std::string> order;
    return order;
}

JNIEXPORT void JHepRep::removeTypeTree(HepRepTypeTree * typeTree) {
}

JNIEXPORT std::vector<HepRepTypeTree *> JHepRep::getTypeTreeList() {
    std::vector<HepRepTypeTree *> v;
    return v;
}

JNIEXPORT HepRepTypeTree * JHepRep::getTypeTree(std::string name, std::string version) {
    return NULL;
}

JNIEXPORT void JHepRep::overlayInstanceTree(HepRepInstanceTree * instanceTree) {
}

JNIEXPORT void JHepRep::removeInstanceTree(HepRepInstanceTree * instanceTree) {
}

JNIEXPORT std::vector<HepRepInstanceTree *> JHepRep::getInstanceTreeList() {
    std::vector<HepRepInstanceTree *> v;
    return v;
}

JNIEXPORT HepRepInstanceTree * JHepRep::getInstanceTreeTop(std::string name, std::string version) {
    return NULL;
}

JNIEXPORT HepRepInstanceTree * JHepRep::getInstances(std::string name, std::string version, std::vector<std::string>  typeNames) {
    return NULL;
}

JNIEXPORT HepRepInstanceTree * JHepRep::getInstancesAfterAction(std::string name, std::string version, std::vector<std::string>  typeNames, std::vector<HepRepAction *>  actions, bool getPoints, bool getDrawAtts, bool getNonDrawAtts, std::vector<std::string>  invertAtts) {
    return NULL;
}

JNIEXPORT std::string JHepRep::checkForException() {
    return NULL;
}

JNIEXPORT HepRep * JHepRep::copy() {
    return NULL;
}

JNIEXPORT HepRep * JHepRep::copy(HepRepSelectFilter * filter) {
    return NULL;
}
