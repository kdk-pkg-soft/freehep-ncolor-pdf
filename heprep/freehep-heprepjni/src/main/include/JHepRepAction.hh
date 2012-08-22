#ifndef HEPREP_JHEPREPACTION_H
#define HEPREP_JHEPREPACTION_H 1

// Copyright 2000-2002, FreeHEP.

#include <jni.h>

#include "HEPREP/HepRepAction.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepRef.hh"

/**
 *
 * @author M.Donszelmann
 * @author J.Perl
 *
 */
class JHepRepAction: public JHepRepRef, public virtual HEPREP::HepRepAction {

protected:
    inline JHepRepAction() { };
    inline JHepRepAction(const JHepRepAction& r) { };
    inline JHepRepAction& operator=(const JHepRepAction&) { return *this; };

public:
    /**
     * Default JNI Constructor
     */
    JNIEXPORT JHepRepAction(HEPREP::HepRepFactory *factory, jobject obj);

    /// Destructor.
    JNIEXPORT virtual ~JHepRepAction();

    JNIEXPORT virtual std::string getName();
    JNIEXPORT virtual std::string getExpression();
    JNIEXPORT virtual HEPREP::HepRepAction * copy();
}; // class

#endif /* ifndef HEPREP_JHEPREPACTION_H */
