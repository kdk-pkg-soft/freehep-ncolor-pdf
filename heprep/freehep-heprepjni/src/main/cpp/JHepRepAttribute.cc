
#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepAttValue.h"

#include "JHepRepAttribute.hh"
#include "JHepRepFactory.hh"

#include "JNIUtil.hh"
#include "JNIMacros.hh"

using namespace HEPREP;
using namespace std;


JNIEXPORT JHepRepAttribute::JHepRepAttribute(HepRepFactory* f, jobject obj)
        : JHepRepRef(f, obj) {

    GETMETHOD("JHepRepAttribute", addStringValueMethod,
       	      "addAttValue", "(Ljava/lang/String;Ljava/lang/String;I)V" )
    GETMETHOD("JHepRepAttribute", addIntValueMethod,
              "addAttValue", "(Ljava/lang/String;II)V" )
    GETMETHOD("JHepRepAttribute", addLongValueMethod,
              "addAttValue", "(Ljava/lang/String;JI)V" )
    GETMETHOD("JHepRepAttribute", addDoubleValueMethod,
              "addAttValue", "(Ljava/lang/String;DI)V" )
    GETMETHOD("JHepRepAttribute", addBooleanValueMethod,
              "addAttValue", "(Ljava/lang/String;ZI)V" )
    GETMETHOD("JHepRepAttribute", addColorStringMethod,
              "addAttColor", "(Ljava/lang/String;Ljava/lang/String;I)V" )
    GETMETHOD("JHepRepAttribute", addColorValueMethod,
              "addAttColor", "(Ljava/lang/String;DDDDI)V" )
}

JNIEXPORT JHepRepAttribute::~JHepRepAttribute() {
}

JNIEXPORT void JHepRepAttribute::addAttValue(string key, char *value, int showLabel) {
    addAttValue(key, (string)value, showLabel);
}

JNIEXPORT void JHepRepAttribute::addAttValue(string key, string value, int showLabel) {
    jstring jkey, jvalue;
    NEWSTRING(jkey, key)
    NEWSTRING(jvalue, value)
	env->CallVoidMethod(ref, addStringValueMethod, jkey, jvalue, showLabel);
	DELSTRING(jkey)
	DELSTRING(jvalue)
	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepAttribute::addAttValue(string key, int value, int showLabel) {
    jstring jkey;
    NEWSTRING(jkey, key)
	env->CallVoidMethod(ref, addIntValueMethod, jkey, value, showLabel);
    DELSTRING(jkey)
	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepAttribute::addAttValue(string key, HEPREP::int64 value, int showLabel) {
    jstring jkey;
    NEWSTRING(jkey, key)
	env->CallVoidMethod(ref, addLongValueMethod, jkey, value, showLabel);
    DELSTRING(jkey)
	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepAttribute::addAttValue(string key, double value, int showLabel) {
    jstring jkey;
    NEWSTRING(jkey, key)
	env->CallVoidMethod(ref, addDoubleValueMethod, jkey, value, showLabel);
    DELSTRING(jkey)
	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepAttribute::addAttValue(string key, bool value, int showLabel) {
    jstring jkey;
    NEWSTRING(jkey, key)
	env->CallVoidMethod(ref, addBooleanValueMethod, jkey, value, showLabel);
    DELSTRING(jkey)
	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepAttribute::addAttValue(string key, vector<double>, int showLabel) {
/* FIXME
    jstring jkey;
    NEWSTRING(jkey, key)
	env->CallVoidMethod(ref, addColorValueMethod, jkey, r, g, b, alpha, showLabel);
    DELSTRING(jkey)
	JNIUtil::checkExceptions(env);
*/
}

JNIEXPORT void JHepRepAttribute::addAttValue(string key, double red, double green, double blue, double alpha, int showLabel) {
    jstring jkey;
    NEWSTRING(jkey, key)
	env->CallVoidMethod(ref, addColorValueMethod, jkey, red, green, blue, alpha, showLabel);
    DELSTRING(jkey)
	JNIUtil::checkExceptions(env);
}

JNIEXPORT void JHepRepAttribute::addAttValue(HepRepAttValue * hepRepAttValue) {
}

JNIEXPORT HepRepAttValue * JHepRepAttribute::removeAttValue(std::string key) {
    return NULL;
}

JNIEXPORT HepRepAttValue * JHepRepAttribute::getAttValue(std::string name) {
    return NULL;
}


JNIEXPORT std::set<HepRepAttValue *> JHepRepAttribute::getAttValuesFromNode() {
    set<HepRepAttValue*> s;
    return s;
}


JNIEXPORT HepRepAttValue * JHepRepAttribute::getAttValueFromNode(std::string lowerCaseName) {
    return NULL;
}





