#include "HEPREP/HepRepFactory.h"

#include "JHepRepDefinition.hh"
#include "JHepRepFactory.hh"

#include "JNIUtil.hh"
#include "JNIMacros.hh"

using namespace HEPREP;
using namespace std;

JNIEXPORT JHepRepDefinition::JHepRepDefinition(HepRepFactory* f, jobject obj)
        : JHepRepAttribute(f, obj) {

    GETMETHOD("JHepRepDefinition", addDefMethod,
              "addAttDef", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V" )
}

JNIEXPORT JHepRepDefinition::~JHepRepDefinition() {
}

JNIEXPORT void JHepRepDefinition::addAttDef(string name, string desc, string type, string extra) {
    jstring jname, jdesc, jtype, jextra;
    NEWSTRING(jname, name)
    NEWSTRING(jdesc, desc)
    NEWSTRING(jtype, type)
    NEWSTRING(jextra, extra)
	env->CallVoidMethod(ref, addDefMethod, jname, jdesc, jtype, jextra);
    DELSTRING(jname)
    DELSTRING(jdesc)
    DELSTRING(jtype)
    DELSTRING(jextra)
	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepDefinition::addAttDef(HepRepAttDef * hepRepAttDef) {
}

JNIEXPORT HepRepAttDef * JHepRepDefinition::getAttDef(std::string name) {
    return NULL;
}

JNIEXPORT std::set<HepRepAttDef *> JHepRepDefinition::getAttDefsFromNode() {
    set<HepRepAttDef*> s;
    return s;
}

JNIEXPORT HepRepAttDef * JHepRepDefinition::getAttDefFromNode(std::string lowerCaseName) {
    return NULL;
}
