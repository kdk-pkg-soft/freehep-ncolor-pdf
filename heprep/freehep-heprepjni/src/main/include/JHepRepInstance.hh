#ifndef JHEPREP_INSTANCE
#define JHEPREP_INSTANCE

#include <string>
#include <cstdlib>

#include <jni.h>

#include "HEPREP/HepRepInstance.h"
#include "HEPREP/HepRepPoint.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepAttribute.hh"

class JHepRepInstance : public JHepRepAttribute, public virtual HEPREP::HepRepInstance {

    private:
        jmethodID addPointMethod;
        jmethodID addInstanceMethod;

    protected:
        inline JHepRepInstance() { };
        inline JHepRepInstance(const JHepRepInstance& r) { };
        inline JHepRepInstance& operator=(const JHepRepInstance&) { return *this; };

    public:
        JNIEXPORT JHepRepInstance(HEPREP::HepRepFactory *factory, jobject obj);
        JNIEXPORT virtual ~JHepRepInstance();

        JNIEXPORT virtual void overlay(HEPREP::HepRepInstance * instance);
        JNIEXPORT virtual void addPoint(HEPREP::HepRepPoint *point);
        JNIEXPORT virtual void addInstance(HEPREP::HepRepInstance *instance);

        JNIEXPORT virtual HEPREP::HepRepType * getType();
        JNIEXPORT virtual void removeInstance(HEPREP::HepRepInstance * instance);
        JNIEXPORT virtual std::vector<HEPREP::HepRepInstance*> getInstances();
        JNIEXPORT virtual std::vector<HEPREP::HepRepPoint*> getPoints();
        JNIEXPORT virtual HEPREP::HepRepInstance * getSuperInstance();
        JNIEXPORT virtual HEPREP::HepRepInstance * copy(HEPREP::HepRepTypeTree * typeTree, HEPREP::HepRepInstance * parent, HEPREP::HepRepSelectFilter * filter);
        JNIEXPORT virtual HEPREP::HepRepInstance * copy(HEPREP::HepRepTypeTree * typeTree, HEPREP::HepRepInstanceTree * parent, HEPREP::HepRepSelectFilter * filter);
};

#endif
