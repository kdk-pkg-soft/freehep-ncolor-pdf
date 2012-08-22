#ifndef JHEPREP_POINT
#define JHEPREP_POINT

#include "HEPREP/HepRepPoint.h"
#include "HEPREP/HepRepFactory.h"

#include "JHepRepAttribute.hh"

class JHepRepPoint : public JHepRepAttribute, public virtual HEPREP::HepRepPoint {

    protected:
        inline JHepRepPoint() { };
        inline JHepRepPoint(const JHepRepPoint& r) { };
        inline JHepRepPoint& operator=(const JHepRepPoint&) { return *this; };

    public:
        JNIEXPORT JHepRepPoint(HEPREP::HepRepFactory *factory, jobject obj);
        JNIEXPORT virtual ~JHepRepPoint();

        JNIEXPORT virtual double getX();
        JNIEXPORT virtual double getY();
        JNIEXPORT virtual double getZ();
        JNIEXPORT virtual double getRho();
        JNIEXPORT virtual double getPhi();
        JNIEXPORT virtual double getTheta();
        JNIEXPORT virtual double getR();
        JNIEXPORT virtual double getEta();
        JNIEXPORT virtual double getX(double xVertex, double yVertex, double zVertex);
        JNIEXPORT virtual double getY(double xVertex, double yVertex, double zVertex);
        JNIEXPORT virtual double getZ(double xVertex, double yVertex, double zVertex);
        JNIEXPORT virtual double getRho(double xVertex, double yVertex, double zVertex);
        JNIEXPORT virtual double getPhi(double xVertex, double yVertex, double zVertex);
        JNIEXPORT virtual double getTheta(double xVertex, double yVertex, double zVertex);
        JNIEXPORT virtual double getR(double xVertex, double yVertex, double zVertex);
        JNIEXPORT virtual double getEta(double xVertex, double yVertex, double zVertex);
        JNIEXPORT virtual std::vector<double>* getXYZ(std::vector<double>* xyz);
        JNIEXPORT virtual HEPREP::HepRepInstance* getInstance();
        JNIEXPORT virtual HEPREP::HepRepPoint* copy(HEPREP::HepRepInstance* parent);
};

#endif
