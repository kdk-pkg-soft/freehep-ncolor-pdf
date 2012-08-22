#ifndef HEPREP_JHEPREPSELECTFILTER_H
#define HEPREP_JHEPREPSELECTFILTER_H 1

// Copyright 2000-2002, FreeHEP.

#include <jni.h>

#include "HEPREP/HepRepInstance.h"
#include "HEPREP/HepRepSelectFilter.h"

#include "JHepRepRef.hh"

/**
 *
 * @author M.Donszelmann
 * @author J.Perl
 *
 */
class JHepRepSelectFilter: public JHepRepRef, public virtual HEPREP::HepRepSelectFilter {

protected:
    inline JHepRepSelectFilter() { };
    inline JHepRepSelectFilter(const JHepRepSelectFilter& r) { };
    inline JHepRepSelectFilter& operator=(const JHepRepSelectFilter&) { return *this; };

public:
    /**
     * Default JNI Constructor
     */
    JNIEXPORT JHepRepSelectFilter(HEPREP::HepRepFactory * factory, jobject obj);

    /// Destructor.
    JNIEXPORT virtual ~JHepRepSelectFilter();

    JNIEXPORT virtual bool select(HEPREP::HepRepInstance* instance);
}; // class

#endif /* ifndef HEPREP_JHEPREPSELECTFILTER_H */
