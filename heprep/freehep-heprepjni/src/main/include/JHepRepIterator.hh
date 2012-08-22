#ifndef HEPREP_JHEPREPITERATOR_H
#define HEPREP_JHEPREPITERATOR_H 1

// Copyright 2000-2002, FreeHEP.

#include <jni.h>
#include <string>

#include "HEPREP/HepRepIterator.h"
#include "HEPREP/HepRepAttValue.h"
#include "HEPREP/HepRepInstance.h"
#include "HEPREP/HepRepAttributeListener.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepRef.hh"

/**
 *
 * @author M.Donszelmann
 * @author C.Loomis
 *
 */
class JHepRepIterator: public JHepRepRef, public virtual HEPREP::HepRepIterator {

protected:
    inline JHepRepIterator() { };
    inline JHepRepIterator(const JHepRepIterator& r) { };
    inline JHepRepIterator& operator=(const JHepRepIterator&) { return *this; };

public:
    /**
     * Default JNI Constructor
     */
    JNIEXPORT JHepRepIterator(HEPREP::HepRepFactory * factory, jobject obj);

    /// Destructor.
    JNIEXPORT virtual ~JHepRepIterator();

    JNIEXPORT virtual HEPREP::HepRepInstance * nextInstance();
    JNIEXPORT virtual bool hasNext();
    JNIEXPORT virtual bool addHepRepIteratorListener(std::string name, HEPREP::HepRepAttributeListener * listener);
    JNIEXPORT virtual void removeHepRepIteratorListener(std::string name, HEPREP::HepRepAttributeListener * listener);
//    JNIEXPORT virtual HEPREP::HepRepAttValue * getAttValue(std::string key);
}; // class

#endif /* ifndef HEPREP_JHEPREPITERATOR_H */
