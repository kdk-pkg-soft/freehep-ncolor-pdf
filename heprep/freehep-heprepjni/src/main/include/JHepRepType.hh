#ifndef JHEPREP_TYPE
#define JHEPREP_TYPE

#include <string>
#include <vector>
#include <cstdlib>

#include <jni.h>

#include "HEPREP/HepRepType.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepDefinition.hh"

class JHepRepType : public JHepRepDefinition, public virtual HEPREP::HepRepType {

    private:
        jmethodID addTypeMethod;

    protected:
        inline JHepRepType() { };
        inline JHepRepType(const JHepRepType& r) { };
        inline JHepRepType& operator=(const JHepRepType&) { return *this; };

    public:
        JNIEXPORT JHepRepType(HEPREP::HepRepFactory *factory, jobject obj);
        JNIEXPORT virtual ~JHepRepType();

        JNIEXPORT virtual void addType(HEPREP::HepRepType *type);

        JNIEXPORT virtual std::string getName();
        JNIEXPORT virtual std::string getFullName();
        JNIEXPORT virtual std::string getDescription();
        JNIEXPORT virtual void setDescription(std::string description);
        JNIEXPORT virtual std::string getInfoURL();
        JNIEXPORT virtual void setInfoURL(std::string description);
        JNIEXPORT virtual HEPREP::HepRepType* getSuperType();
        JNIEXPORT virtual std::vector<HEPREP::HepRepType *> getTypeList();
        JNIEXPORT virtual HEPREP::HepRepType * copy(HEPREP::HepRepType * parent);
};

#endif
