#ifndef JHEPREP_REF
#define JHEPREP_REF

#include <string>
#include <cstdlib>

#include <jni.h>

#include "HEPREP/HepRepFactory.h"

class JHepRepFactory;

class JHepRepRef {

    protected:
        JHepRepFactory* factory;
        JNIEnv* env;
        jclass cls;
        jobject ref;

        inline JHepRepRef() { };
        inline JHepRepRef(const JHepRepRef& r) { };
        inline JHepRepRef& operator=(const JHepRepRef&) { return *this; };

    public:
        JNIEXPORT JHepRepRef(HEPREP::HepRepFactory *factory, jobject obj);
        JNIEXPORT virtual ~JHepRepRef();

        jobject getRef();
};

#endif
