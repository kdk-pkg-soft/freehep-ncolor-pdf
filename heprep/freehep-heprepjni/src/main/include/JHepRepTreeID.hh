#ifndef HEPREP_JHEPREPTREEID_H
#define HEPREP_JHEPREPTREEID_H 1

// Copyright 2000-2002, FreeHEP.

#include <jni.h>

#include "HEPREP/HepRepTreeID.h"

#include "JHepRepRef.hh"

/**
 *
 * @author M.Donszelmann
 * @author J.Perl
 *
 */
class JHepRepTreeID: public JHepRepRef , public virtual HEPREP::HepRepTreeID {

protected:
    inline JHepRepTreeID() { };
    inline JHepRepTreeID(const JHepRepTreeID& r) { };
    inline JHepRepTreeID& operator=(const JHepRepTreeID&) { return *this; };

public:
    JNIEXPORT JHepRepTreeID(HEPREP::HepRepFactory* f, jobject obj);

    /// Destructor.
    JNIEXPORT virtual ~JHepRepTreeID();

    JNIEXPORT virtual std::string getQualifier();
    JNIEXPORT virtual void setQualifier(std::string qualifier);
    JNIEXPORT virtual std::string getName();
    JNIEXPORT virtual std::string getVersion();
}; // class

#endif /* ifndef HEPREP_JHEPREPTREEID_H */
