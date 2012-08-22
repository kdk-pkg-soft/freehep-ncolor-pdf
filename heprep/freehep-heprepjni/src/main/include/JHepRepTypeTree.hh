#ifndef JHEPREP_TYPE_TREE
#define JHEPREP_TYPE_TREE

#include <string>
#include <cstdlib>
#include <vector>

#include <jni.h>

#include "HEPREP/HepRepTypeTree.h"
#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepType.h"

#include "JHepRepTreeID.hh"

class JHepRepTypeTree : public JHepRepTreeID, public virtual HEPREP::HepRepTypeTree {

    private:
        jmethodID addTypeMethod;

    protected:
        inline JHepRepTypeTree() { };
        inline JHepRepTypeTree(const JHepRepTypeTree& r) { };
        inline JHepRepTypeTree& operator=(const JHepRepTypeTree&) { return *this; };

    public:
        JNIEXPORT JHepRepTypeTree(HEPREP::HepRepFactory *factory, jobject obj);
        JNIEXPORT virtual ~JHepRepTypeTree();

        JNIEXPORT virtual void addType(HEPREP::HepRepType *type);

        JNIEXPORT virtual std::vector<HEPREP::HepRepType *> getTypeList();
        JNIEXPORT virtual HEPREP::HepRepTypeTree * copy();
        JNIEXPORT virtual HEPREP::HepRepType * getType(std::string name);
};

#endif
