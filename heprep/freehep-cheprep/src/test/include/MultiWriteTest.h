// Copyright 2003-2005, FreeHEP.
#ifndef MULTIWRITETEST_H
#define MULTIWRITETEST_H 1

#include "HEPREP/HepRep.h"

#include "Random.h"

class MultiWriteTest {
    private:
        Random random;
    public:
        MultiWriteTest();
        ~MultiWriteTest();
        void write(HEPREP::HepRepFactory* factory, int nevents, std::string filename);

    private:
        HEPREP::HepRep* makeRandomHepRep(HEPREP::HepRepFactory* factory);
        double nextRandom();
};

#endif
