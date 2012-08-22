#ifndef HEPREP_JHEPREPATTVALUE_H
#define HEPREP_JHEPREPATTVALUE_H 1

// Copyright 2000-2002, FreeHEP.

#include <jni.h>
#include <string>

#include "HEPREP/HepRepAttValue.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepRef.hh"

/**
 *
 * @author M.Donszelmann
 * @author J.Perl
 *
 */
class JHepRepAttValue: public JHepRepRef, public virtual HEPREP::HepRepAttValue {

protected:
    inline JHepRepAttValue() { };
    inline JHepRepAttValue(const JHepRepAttValue& r) { };
    inline JHepRepAttValue& operator=(const JHepRepAttValue&) { return *this; };

public:
    /**
     * Default JNI Constructor
     */
    JNIEXPORT JHepRepAttValue(HEPREP::HepRepFactory* f, jobject obj);

    /// Destructor.
    JNIEXPORT virtual ~JHepRepAttValue();

    /**
     * @return a copy of this attribute value
     */
    JNIEXPORT virtual HEPREP::HepRepAttValue * copy();

    /**
     * @return Capitalized Name
     */
    JNIEXPORT virtual std::string getName();

    /**
     * @return Lowercased Name
     */
    JNIEXPORT virtual std::string getLowerCaseName();

    /**
     * @return type
     */
    JNIEXPORT virtual int getType();

    /**
     * @return type name
     */
    JNIEXPORT virtual std::string getTypeName();

    /**
     * @return flag bits if should be shown as label
     */
    JNIEXPORT virtual int showLabel();

    /**
     * @return value as string (if type is string)
     */
    JNIEXPORT virtual std::string getString();

    /**
     * @return value as LowerCased string (if type is string)
     */
    JNIEXPORT virtual std::string getLowerCaseString();

    /**
     * @return value (of any type) in string format
     */
    JNIEXPORT virtual std::string getAsString();

    /**
     * @return value as long
     */
    JNIEXPORT virtual HEPREP::int64 getLong();

    /**
     * @return value as integer
     */
    JNIEXPORT virtual int getInteger();

    /**
     * @return value as double
     */
    JNIEXPORT virtual double getDouble();

    /**
     * @return value as boolean
     */
    JNIEXPORT virtual bool getBoolean();
}; // class

#endif /* ifndef HEPREP_JHEPREPATTVALUE_H */
