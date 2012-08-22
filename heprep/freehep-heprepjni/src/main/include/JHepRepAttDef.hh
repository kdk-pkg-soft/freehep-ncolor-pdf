#ifndef HEPREP_JHEPREPATTDEF_H
#define HEPREP_JHEPREPATTDEF_H 1

// Copyright 2000-2002, FreeHEP.

#include <jni.h>
#include <string>

#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepAttDef.h"

#include "JHepRepRef.hh"

/**
 *
 * @author M.Donszelmann
 * @author J.Perl
 *
 */
class JHepRepAttDef: public JHepRepRef, public virtual HEPREP::HepRepAttDef {

private:

protected:
    inline JHepRepAttDef() { };
    inline JHepRepAttDef(const JHepRepAttDef& r) { };
    inline JHepRepAttDef& operator=(const JHepRepAttDef&) { return *this; };

public:
    JNIEXPORT JHepRepAttDef(HEPREP::HepRepFactory* f, jobject obj);

    /// Destructor.
    JNIEXPORT virtual ~JHepRepAttDef();

    JNIEXPORT virtual std::string getName();
    JNIEXPORT virtual std::string getLowerCaseName();
    JNIEXPORT virtual std::string getDescription();
    JNIEXPORT virtual std::string getCategory();
    JNIEXPORT virtual std::string getExtra();
    JNIEXPORT virtual HEPREP::HepRepAttDef * copy();
}; // class
#endif /* ifndef HEPREP_JHEPREPATTDEF_H */
