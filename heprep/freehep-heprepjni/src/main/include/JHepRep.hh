#ifndef JHEPREP
#define JHEPREP

#include <string>
#include <vector>
#include <set>
#include <cstdlib>

#include <jni.h>

#include "HEPREP/HepRep.h"
#include "HEPREP/HepRepTypeTree.h"
#include "HEPREP/HepRepInstanceTree.h"

#include "JHepRepRef.hh"

class JHepRep : public JHepRepRef, public virtual HEPREP::HepRep {

    private:
        jmethodID addLayerMethod;
        jmethodID addTypeTreeMethod;
        jmethodID addInstanceTreeMethod;

    protected:
        inline JHepRep() { };
        inline JHepRep(const JHepRep& r) { };
        inline JHepRep& operator=(const JHepRep&) { return *this; };

    public:
        JNIEXPORT JHepRep(HEPREP::HepRepFactory *factory, jobject obj);
        JNIEXPORT virtual ~JHepRep();

        JNIEXPORT virtual void addLayer(std::string layer);
        JNIEXPORT virtual void addTypeTree(HEPREP::HepRepTypeTree *typeTree);
        JNIEXPORT virtual void addInstanceTree(HEPREP::HepRepInstanceTree *instanceTree);
        JNIEXPORT virtual void overlayInstanceTree(HEPREP::HepRepInstanceTree * instanceTree);

        JNIEXPORT virtual std::vector<std::string> getLayerOrder();
        JNIEXPORT virtual void removeTypeTree(HEPREP::HepRepTypeTree * typeTree);
        JNIEXPORT virtual std::vector<HEPREP::HepRepTypeTree *> getTypeTreeList();
        JNIEXPORT virtual HEPREP::HepRepTypeTree * getTypeTree(std::string name, std::string version);
        JNIEXPORT virtual void removeInstanceTree(HEPREP::HepRepInstanceTree * instanceTree);
        JNIEXPORT virtual std::vector<HEPREP::HepRepInstanceTree *> getInstanceTreeList();
        JNIEXPORT virtual HEPREP::HepRepInstanceTree * getInstanceTreeTop(std::string name, std::string version);
        JNIEXPORT virtual HEPREP::HepRepInstanceTree * getInstances(std::string name, std::string version, std::vector<std::string>  typeNames);
        JNIEXPORT virtual HEPREP::HepRepInstanceTree * getInstancesAfterAction(std::string name, std::string version, std::vector<std::string>  typeNames, std::vector<HEPREP::HepRepAction *>  actions, bool getPoints, bool getDrawAtts, bool getNonDrawAtts, std::vector<std::string>  invertAtts);
        JNIEXPORT virtual std::string checkForException();
        JNIEXPORT virtual HEPREP::HepRep * copy();
        JNIEXPORT virtual HEPREP::HepRep * copy(HEPREP::HepRepSelectFilter * filter);

};

#endif
