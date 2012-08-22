#ifndef JHEPREP_FACTORY
#define JHEPREP_FACTORY

#include <string>

#include "jni.h"

#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepPoint.h"
#include "HEPREP/HepRepInstance.h"
#include "HEPREP/HepRepInstanceTree.h"
#include "HEPREP/HepRepType.h"
#include "HEPREP/HepRepTypeTree.h"
#include "HEPREP/HepRepTreeID.h"
#include "HEPREP/HepRep.h"

class JHepRep;

class JHepRepFactory : public virtual HEPREP::HepRepFactory {

    private:
    	jmethodID createPointMethod;
    	jmethodID createInstanceMethod1;
    	jmethodID createInstanceMethod2;
    	jmethodID createTreeIDMethod;
    	jmethodID createInstanceTreeMethod;
    	jmethodID createTypeMethod1;
    	jmethodID createTypeMethod2;
    	jmethodID createTypeTreeMethod;
    	jmethodID createHepRepMethod;

        jclass ioClass;
        jmethodID saveMethod;

    protected:
        // needed instead of JHepRepRef
        JHepRepFactory* factory;
        jobject ref;
        jclass cls;

        JNIEnv *env;
        JavaVM *jvm;

        inline JHepRepFactory(const JHepRepFactory& r) { };
        inline JHepRepFactory& operator=(const JHepRepFactory&) { return *this; };

    public:
    	JNIEXPORT JHepRepFactory();
    	JNIEXPORT virtual ~JHepRepFactory();

//        int runWired(JHepRep *heprep);
//    	void registerImmediateFunction(void* fnptr);

        JNIEXPORT virtual HEPREP::HepRepPoint* createHepRepPoint(HEPREP::HepRepInstance *instance, double x, double y, double z);
        JNIEXPORT virtual HEPREP::HepRepInstance* createHepRepInstance(HEPREP::HepRepInstance *parent, HEPREP::HepRepType *type);
        JNIEXPORT virtual HEPREP::HepRepInstance* createHepRepInstance(HEPREP::HepRepInstanceTree *parent, HEPREP::HepRepType *type);
        JNIEXPORT virtual HEPREP::HepRepTreeID* createHepRepTreeID(std::string name, std::string version, std::string qualifier);
        JNIEXPORT virtual HEPREP::HepRepInstanceTree* createHepRepInstanceTree(std::string name, std::string version, HEPREP::HepRepTreeID *typeTreeID);
        JNIEXPORT virtual HEPREP::HepRepType* createHepRepType(HEPREP::HepRepType *parent, std::string name);
        JNIEXPORT virtual HEPREP::HepRepType* createHepRepType(HEPREP::HepRepTypeTree *parent, std::string name);
        JNIEXPORT virtual HEPREP::HepRepTypeTree* createHepRepTypeTree(HEPREP::HepRepTreeID *treeID);
        JNIEXPORT virtual HEPREP::HepRep* createHepRep();
//        virtual int save(HEPREP::HepRep *hepRep, std::string filename);

    	JNIEnv* getEnv();
//    	void error(std::string message, ...);
//    	std::string getCommand();
//    	void freeCommand(std::string command);
//    	void log(std::string message);
};
#endif
