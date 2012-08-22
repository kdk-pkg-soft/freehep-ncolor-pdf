#include <cassert>
#include <cstdio>
#include <cstdarg>

#include "JHepRepFactory.hh"
#include "JHepRepRef.hh"
#include "JHepRepPoint.hh"
#include "JHepRepInstance.hh"
#include "JHepRepInstanceTree.hh"
#include "JHepRepType.hh"
#include "JHepRepTypeTree.hh"
#include "JHepRep.hh"

#include "JNIUtil.hh"
#include "JNIMacros.hh"

using namespace std;
using namespace HEPREP;

JNIEXPORT JHepRepFactory::JHepRepFactory() {

    factory = this;

    // Start JVM
	int rc = JNIUtil::createJVM(&env, &jvm);
    if (rc < 0) {
        cerr << "JHepRepFactory: Failed to create Java VM" << endl;
    }
    if (rc == 1) {
        cerr << "JHepRepFactory: CLASSPATH or JVM_CLASSPATH not set" << endl;
    }

//	const char* title = "HepRepJob";

    // get the java HepRepIO class
    GETCLASS("JHepRepFactory", ioClass, "hep/graphics/heprep/jni/HepRepIO" )

	saveMethod = env->GetStaticMethodID(ioClass, "save", "(Lhep/graphics/heprep/HepRep;Ljava/lang/String;)I");
	if (saveMethod == NULL) {
	    cerr << "JHepRepFactory: Could not find save method for HepRepFactory" << endl;
	}

    // Get the Suggested HepRep factory
    jmethodID factoryMethod = env->GetStaticMethodID(ioClass, "getFactory", "()Lhep/graphics/heprep/HepRepFactory;");
	if (factoryMethod == NULL) {
	    cerr << "JHepRepFactory: Could not find getFactory() method in hep/graphics/heprep/jni/HepRepIO" << endl;
	}
    ref = env->CallStaticObjectMethod(ioClass, factoryMethod);
	if (ref == NULL) {
	    cerr << "JHepRepFactory: Could not create HepRepFactory" << endl;
	}

    // get all create methods of HepRepFactory
    GETCLASS("JHepRepFactory", cls, "hep/graphics/heprep/HepRepFactory" )

    GETMETHOD("JHepRepFactory", createPointMethod,
        "createHepRepPoint",
        "(Lhep/graphics/heprep/HepRepInstance;DDD)Lhep/graphics/heprep/HepRepPoint;" )
    GETMETHOD("JHepRepFactory", createInstanceMethod1,
        "createHepRepInstance",
	    "(Lhep/graphics/heprep/HepRepInstance;Lhep/graphics/heprep/HepRepType;)Lhep/graphics/heprep/HepRepInstance;" )
    GETMETHOD("JHepRepFactory", createInstanceMethod2,
        "createHepRepInstance",
	    "(Lhep/graphics/heprep/HepRepInstanceTree;Lhep/graphics/heprep/HepRepType;)Lhep/graphics/heprep/HepRepInstance;" )
    GETMETHOD("JHepRepFactory", createTreeIDMethod,
        "createHepRepTreeID",
	    "(Ljava/lang/String;Ljava/lang/String;)Lhep/graphics/heprep/HepRepTreeID;" )
    GETMETHOD("JHepRepFactory", createInstanceTreeMethod,
        "createHepRepInstanceTree",
	    "(Ljava/lang/String;Ljava/lang/String;Lhep/graphics/heprep/HepRepName;)Lhep/graphics/heprep/HepRepInstanceTree;");
    GETMETHOD("JHepRepFactory", createTypeMethod1,
        "createHepRepType",
	    "(Lhep/graphics/heprep/HepRepType;Ljava/lang/String;)Lhep/graphics/heprep/HepRepType;" )
    GETMETHOD("JHepRepFactory", createTypeMethod2,
        "createHepRepType",
	    "(Lhep/graphics/heprep/HepRepTypeTree;Ljava/lang/String;)Lhep/graphics/heprep/HepRepType;" )
    GETMETHOD("JHepRepFactory", createTypeTreeMethod,
        "createHepRepTypeTree",
	    "(Ljava/lang/String;Ljava/lang/String;)Lhep/graphics/heprep/HepRepTypeTree;" )
    GETMETHOD("JHepRepFactory", createHepRepMethod,
        "createHepRep",
	    "()Lhep/graphics/heprep/HepRep;" )


//	cls = env->FindClass("JASG4Driver");
//	if (cls == NULL) error("Could not find class JASG4Driver");
//	jmethodID constructor = env->GetMethodID(cls,"<init>","(Ljava/lang/String;)V");
//	if (constructor == NULL) error("Could not find constructor");
//	jstring jtitle = env->NewStringUTF(title);
//	job = env->NewObject(cls,constructor,jtitle);
//	if (job == NULL) error("Could not create job");

//	getCommand = env->GetMethodID(cls,"getCommand","()Ljava/lang/String;");
//	if (getCommand == NULL) error("Could not find getCommand method");

//	log = env->GetMethodID(cls,"log","(Ljava/lang/String;)V");
//	if (log == NULL) error("Could not find log method");

//	jclass cmdclass = env->FindClass("hep/graphics/heprep/jni/JG4CommandDriver");
//	if (cmdclass == NULL) {
//	    error("JHepRepFactory: Could not find class hep/graphics/heprep/jni/JG4CommandDriver");
//	}

	/*
	// FIXME, the G4 stuff should deal with this registration
    // FIXME: check return code
    JNIUtil::registerFunction(env, cmdclass,
                              "immediateCommand", "(Ljava/lang/String;)V",
                              (void *)Java_hep_graphics_heprep_jni_JG4CommandDriver_immediateCommand);
    */
//    env->DeleteLocalRef(cmdclass);
}

JNIEXPORT JHepRepFactory::~JHepRepFactory() {
	//const char* filename = "aida.javahist";
	//jstring jfilename = env->NewStringUTF(filename);
	//env->CallVoidMethod(job,save,jfilename);
	//env->CallVoidMethod(job,dump);

    env->DeleteLocalRef(ioClass);
	JNIUtil::destroyJVM(jvm);
}

/*
string JHistogramFactory::getCommand()
{
	jboolean isCopy;
	jstring command = (jstring) env->CallObjectMethod(job,getCommand);
	return env->GetStringUTFChars(command,&isCopy);
}
*/

/*
void JHistogramFactory::freeCommand(string c)
{
	env->ReleaseStringUTFChars(command,c.c_str());
}
*/

/**
 * Environment Variables:
 * <pre>
 *      WIRED_HOME
 *      WIRED_VERBOSE
 * </pre>
 */
/*
int JHepRepFactory::runWired(JHepRep *heprep) {
    // find class
    jclass clazz = env->FindClass("ch/cern/wired/application/HepRepWired");
    if (clazz == NULL) {
        JNIUtil::checkExceptions(env);

        return 1;
    }

	jmethodID constructor = env->GetMethodID(clazz, "<init>", "(Lhep/graphics/heprep/HepRep;Ljava/lang/String;)V");
	if (constructor == NULL) {
        env->DeleteLocalRef(clazz);
        return 2;
	}

    // find run method
    jmethodID run = env->GetMethodID(clazz, "run", "(Z)V");
    if (run == NULL) {
        env->DeleteLocalRef(clazz);
        return 3;
    }

	char *wiredHome = getenv("WIRED_HOME");
	if (wiredHome == NULL) {
        env->DeleteLocalRef(clazz);
        return 6;
    }

    jstring jhome = env->NewStringUTF(wiredHome);
    if (jhome == NULL) {
        env->DeleteLocalRef(clazz);
        return 7;
    }

	char *wiredVerbose = getenv("WIRED_VERBOSE");
	jboolean verbose = JNI_FALSE;
	if (wiredVerbose != NULL) {
        verbose = JNI_TRUE;
    }

	jobject wired = env->NewObject(clazz, constructor, heprep->getRef(), jhome);
	if (wired == NULL) {
        env->DeleteLocalRef(clazz);
        env->DeleteLocalRef(jhome);
        return 4;
	}

    env->CallVoidMethod(wired, run, verbose);

    if (JNIUtil::exceptions(env)) {
        env->DeleteLocalRef(clazz);
        env->DeleteLocalRef(wired);
        env->DeleteLocalRef(jhome);
        return 5;
    }

    env->DeleteLocalRef(clazz);
    env->DeleteLocalRef(wired);
    env->DeleteLocalRef(jhome);
    return 0;
}
*/

JNIEXPORT HepRepPoint* JHepRepFactory::createHepRepPoint(HepRepInstance *instance, double x, double y, double z) {
    JHepRepInstance* jinstance = dynamic_cast<JHepRepInstance*>(instance);
    assert(jinstance != NULL);

    jobject obj = env->CallObjectMethod(ref, createPointMethod, jinstance->getRef(), x, y, z);
	return new JHepRepPoint(this , obj);
}

JNIEXPORT HepRepInstance* JHepRepFactory::createHepRepInstance(HepRepInstance *parent, HepRepType *type) {
    JHepRepInstance* jparent = dynamic_cast<JHepRepInstance*>(parent);
    assert(jparent != NULL);

    JHepRepType* jtype = dynamic_cast<JHepRepType*>(type);
    assert(jtype != NULL);

    jobject obj = env->CallObjectMethod(ref, createInstanceMethod1, jparent->getRef(), jtype->getRef());
	return new JHepRepInstance(this, obj);
}

JNIEXPORT HepRepInstance* JHepRepFactory::createHepRepInstance(HepRepInstanceTree *parent, HepRepType *type) {
    JHepRepInstanceTree* jparent = dynamic_cast<JHepRepInstanceTree*>(parent);
    assert(jparent != NULL);

    JHepRepType* jtype = dynamic_cast<JHepRepType*>(type);
    assert(jtype != NULL);

    jobject obj = env->CallObjectMethod(ref, createInstanceMethod2, jparent->getRef(), jtype->getRef());
	return new JHepRepInstance(this, obj);
}

JNIEXPORT HepRepTreeID* JHepRepFactory::createHepRepTreeID(string name, string version, string qualifier) {
	jstring jname, jversion, jqualifier;
	NEWSTRING(jname, name)
	NEWSTRING(jversion, version)
	NEWSTRING(jqualifier, qualifier)
    jobject obj = env->CallObjectMethod(ref, createTreeIDMethod, jname, jversion, jqualifier);
    DELSTRING(jname)
    DELSTRING(jversion)
    DELSTRING(jqualifier)
	return new JHepRepTreeID(this, obj);
}

JNIEXPORT HepRepInstanceTree* JHepRepFactory::createHepRepInstanceTree(string name, string version, HepRepTreeID *typeTreeID) {
    jstring jname, jversion;
    NEWSTRING(jname, name)
    NEWSTRING(jversion, version)
    JHepRepTreeID* jtypeTreeID = dynamic_cast<JHepRepTreeID*>(typeTreeID);
    assert(jtypeTreeID != NULL);

    jobject obj = env->CallObjectMethod(ref, createInstanceTreeMethod, jname, jversion, jtypeTreeID->getRef());
    DELSTRING(jname)
    DELSTRING(jversion)
	return new JHepRepInstanceTree(this, obj);
}

JNIEXPORT HepRepType* JHepRepFactory::createHepRepType(HepRepType *parent, string name) {
	jstring jname;
	NEWSTRING(jname, name)
    jobject obj;
    if (parent == NULL) {
        obj = env->CallObjectMethod(ref, createTypeMethod1, NULL, jname);
    } else {
        JHepRepType* jparent = dynamic_cast<JHepRepType*>(parent);
        assert(jparent != NULL);

        obj = env->CallObjectMethod(ref, createTypeMethod1, jparent->getRef(), jname);
    }
    DELSTRING(jname)
	return new JHepRepType(this, obj);
}

JNIEXPORT HepRepType* JHepRepFactory::createHepRepType(HepRepTypeTree *parent, string name) {
	jstring jname;
	NEWSTRING(jname, name)
    jobject obj;

    JHepRepTypeTree* jparent = dynamic_cast<JHepRepTypeTree*>(parent);
    assert(jparent != NULL);

    obj = env->CallObjectMethod(ref, createTypeMethod2, jparent->getRef(), jname);

    DELSTRING(jname)
	return new JHepRepType(this, obj);
}

JNIEXPORT HepRepTypeTree* JHepRepFactory::createHepRepTypeTree(HepRepTreeID *treeID) {
    JHepRepTreeID* jtreeID = dynamic_cast<JHepRepTreeID*>(treeID);
    jobject obj = env->CallObjectMethod(ref, createTypeTreeMethod, jtreeID->getRef());
	return new JHepRepTypeTree(this, obj);
}

JNIEXPORT HepRep* JHepRepFactory::createHepRep() {
    jobject obj = env->CallObjectMethod(ref, createHepRepMethod);
	return new JHepRep(this, obj);
}

/*
int JHepRepFactory::save(IHepRep *heprep, string filename) {
    JHepRep* jheprep = dynamic_cast<JHepRep*>(heprep);
    assert(jheprep != NULL);

	jstring jfilename;
	NEWSTRING(jfilename, filename)
    jint result = env->CallStaticIntMethod(ioClass, saveMethod, jheprep->getRef(), jfilename);
    DELSTRING(jfilename)
    return (JNIUtil::exceptions(env) != 0) ? 1 : result;
}
*/
/*
void JHepRepFactory::error(string msg, ...) {
    char* s = (char*)malloc(255);
    va_list v;

    va_start(v, msg);
    vsprintf(s, msg.c_str(), v);
    va_end(v);
	env->FatalError(s);
	free(s);
}
*/
JNIEnv* JHepRepFactory::getEnv() {
	return env;
}

/*
void JHistogramFactory::log(string msg)
{
	jstring jmsg = env->NewStringUTF(msg.c_str());
	env->CallVoidMethod(job,log,jmsg);
}
*/
