#ifndef RANDOM_H
#define RANDOM_H 1

#include <cstdlib>
#include <ctime>

#include "cheprep/config.h"

class Random {

    private:
        cheprep::uint64 seed;
        const cheprep::uint64 multiplier;
        const cheprep::uint64 addend;
        const cheprep::uint64 mask;
        const int BITS_PER_BYTE;
        const int BYTES_PER_INT;
        double nextNextGaussian;
        bool haveNextNextGaussian;

    protected:
        int next(int bits);

    public:
        Random(cheprep::uint64 seed = time(NULL));
        void setSeed(cheprep::uint64 seed);
        void nextBytes(unsigned char bytes[], int length);
        int nextInt();
        int nextInt(int n);
        cheprep::int64 nextLong();
        bool nextBoolean();
        float nextFloat();
        double nextDouble();
        double nextGaussian();
};

#endif // RANDOM_H
