
#include <cstdlib>
#include <iostream>

#include "HEPREP/HepRepFactory.h"
#include "HEPREP/HepRepInstance.h"

#include "JHepRepSelectFilter.hh"

using namespace HEPREP;


JNIEXPORT JHepRepSelectFilter::JHepRepSelectFilter(HepRepFactory * factory, jobject obj) {
}

JNIEXPORT JHepRepSelectFilter::~JHepRepSelectFilter() {
}

JNIEXPORT bool JHepRepSelectFilter::select(HepRepInstance* instance) {
    return false;
}
