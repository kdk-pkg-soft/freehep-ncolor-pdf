#ifndef JHEPREP_DEFINITION
#define JHEPREP_DEFINITION

#include <string>
#include <cstdlib>

#include <jni.h>

#include "HEPREP/HepRepAttDef.h"
#include "HEPREP/HepRepDefinition.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepAttribute.hh"

class JHepRepDefinition : public JHepRepAttribute, public virtual HEPREP::HepRepDefinition {

    private:
        jmethodID addDefMethod;

    protected:
        inline JHepRepDefinition() { };
        inline JHepRepDefinition(const JHepRepDefinition& r) { };
        inline JHepRepDefinition& operator=(const JHepRepDefinition&) { return *this; };

    public:
        JNIEXPORT JHepRepDefinition(HEPREP::HepRepFactory *factory, jobject obj);
        JNIEXPORT virtual ~JHepRepDefinition();

        JNIEXPORT virtual void addAttDef(std::string name, std::string desc, std::string type, std::string extra);
        JNIEXPORT virtual void addAttDef(HEPREP::HepRepAttDef * hepRepAttDef);

        JNIEXPORT virtual HEPREP::HepRepAttDef * getAttDef(std::string name);
        JNIEXPORT virtual std::set<HEPREP::HepRepAttDef *> getAttDefsFromNode();
        JNIEXPORT virtual HEPREP::HepRepAttDef * getAttDefFromNode(std::string lowerCaseName);
};

#endif
