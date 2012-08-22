#ifndef JHEPREP_INSTANCE_TREE
#define JHEPREP_INSTANCE_TREE

#include <string>
#include <vector>
#include <set>
#include <cstdlib>

#include <jni.h>

#include "HEPREP/HepRepInstanceTree.h"
#include "HEPREP/HepRepSelectFilter.h"
#include "HEPREP/HepRepTreeID.h"
#include "HEPREP/HepRep.h"

#include "JHepRepTreeID.hh"

class JHepRepInstanceTree : public JHepRepTreeID, public virtual HEPREP::HepRepInstanceTree {

    private:
        jmethodID addInstanceMethod;
        jmethodID addInstanceTreeMethod;

    protected:
        inline JHepRepInstanceTree() { };
        inline JHepRepInstanceTree(const JHepRepInstanceTree& r) { };
        inline JHepRepInstanceTree& operator=(const JHepRepInstanceTree&) { return *this; };

    public:
        JNIEXPORT JHepRepInstanceTree(HEPREP::HepRepFactory *factory, jobject obj);
        JNIEXPORT virtual ~JHepRepInstanceTree();

        JNIEXPORT virtual void overlay(HEPREP::HepRepInstanceTree * instanceTree);
        JNIEXPORT virtual void addInstance(HEPREP::HepRepInstance *instance);
        JNIEXPORT virtual void addInstanceTree(HEPREP::HepRepTreeID *treeID);

        JNIEXPORT virtual void removeInstance(HEPREP::HepRepInstance * instance);
        JNIEXPORT virtual std::vector<HEPREP::HepRepInstance *> getInstances();
        JNIEXPORT virtual HEPREP::HepRepTreeID * getTypeTree();
        JNIEXPORT virtual std::vector<HEPREP::HepRepTreeID *> getInstanceTreeList();
        JNIEXPORT virtual HEPREP::HepRepInstanceTree * copy(HEPREP::HepRepTypeTree * typeTree, HEPREP::HepRepSelectFilter * filter);
};

#endif
