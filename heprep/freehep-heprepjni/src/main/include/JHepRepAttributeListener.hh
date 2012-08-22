#ifndef HEPREP_JHEPREPATTRIBUTELISTENER_H
#define HEPREP_JHEPREPATTRIBUTELISTENER_H 1

// Copyright 2000-2002, FreeHEP.

#include <jni.h>
#include <string>
#include <vector>

#include "HEPREP/HepRepAttributeListener.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepRef.hh"

/**
 *
 * @author M.Donszelmann
 * @author C.Loomis
 *
 */
class JHepRepAttributeListener: public JHepRepRef, public virtual HEPREP::HepRepAttributeListener {

protected:
    inline JHepRepAttributeListener() { };
    inline JHepRepAttributeListener(const JHepRepAttributeListener& r) { };
    inline JHepRepAttributeListener& operator=(const JHepRepAttributeListener&) { return *this; };

public:
    /**
     * Default JNI Constructor
     */
    JNIEXPORT JHepRepAttributeListener(HEPREP::HepRepFactory * factory, jobject obj);

    /// Destructor.
    JNIEXPORT virtual ~JHepRepAttributeListener();

    JNIEXPORT virtual void setAttribute(HEPREP::HepRepInstance* instance, std::string key, std::string value, std::string lowerCaseValue, int showLabel);
    JNIEXPORT virtual void setAttribute(HEPREP::HepRepInstance* instance, std::string key, std::vector<double> value, int showLabel);
    JNIEXPORT virtual void setAttribute(HEPREP::HepRepInstance* instance, std::string key, long value, int showLabel);
    JNIEXPORT virtual void setAttribute(HEPREP::HepRepInstance* instance, std::string key, int value, int showLabel);
    JNIEXPORT virtual void setAttribute(HEPREP::HepRepInstance* instance, std::string key, double value, int showLabel);
    JNIEXPORT virtual void setAttribute(HEPREP::HepRepInstance* instance, std::string key, bool value, int showLabel);
    JNIEXPORT virtual void removeAttribute(HEPREP::HepRepInstance* instance, std::string key);
}; // class

#endif /* ifndef HEPREP_JHEPREPATTRIBUTELISTENER_H */
