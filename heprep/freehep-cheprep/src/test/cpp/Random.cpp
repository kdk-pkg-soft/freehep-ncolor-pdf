// Copyright 2003-2005, FreeHEP.

#include <cmath>
#include <iostream>

#include "Random.h"

using namespace std;
using namespace cheprep;

Random::Random(uint64 _seed) :
        seed(0),
        multiplier(0x5DEECE66DLL),
        addend(0xBL),
//        mask((1L << 48) - 1),
        mask(0x0000FFFFFFFFFFFFLL),
        BITS_PER_BYTE(8),
        BYTES_PER_INT(4),
        nextNextGaussian(0),
        haveNextNextGaussian(false) {

    setSeed(_seed);
}

void Random::setSeed(uint64 _seed) {
    _seed = (_seed ^ multiplier) & mask;
    seed = _seed;
	haveNextNextGaussian = false;
}

int Random::next(int bits) {
    seed = (seed * multiplier + addend) & mask;
    return (int)(seed >> (48 - bits));
}

void Random::nextBytes(unsigned char bytes[], int length) {
	int numRequested = length;

	int numGot = 0, rnd = 0;

	while (true) {
	    for (int i = 0; i < BYTES_PER_INT; i++) {
    		if (numGot == numRequested)
    		    return;

    		rnd = (i==0 ? next(BITS_PER_BYTE * BYTES_PER_INT)
    		            : rnd >> BITS_PER_BYTE);
    		bytes[numGot++] = (unsigned char)rnd;
	    }
	}
}

int Random::nextInt() {
    return next(32);
}

int Random::nextInt(int n) {
//    if (n<=0)
//        throw new IllegalArgumentException("n must be positive");

    if ((n & -n) == n) { // i.e., n is a power of 2
        return (int)((n * (int64)next(31)) >> 31);
    }

    int bits, val;
    do {
        bits = next(31);
        val = bits % n;
    } while(bits - val + (n-1) < 0);
    return val;
}

int64 Random::nextLong() {
    return ((int64)(next(32)) << 32) + next(32);
}

bool Random::nextBoolean() {
    return next(1) != 0;
}

float Random::nextFloat() {
    int i = next(24);
    return i / ((float)(1 << 24));
}

double Random::nextDouble() {
    int64 l = ((int64)(next(26)) << 27) + next(27);
    int64 x = 1;
    double r = l / (double)(x << 53);
    return r;
}

double Random::nextGaussian() {
    if (haveNextNextGaussian) {
	    haveNextNextGaussian = false;
	    return nextNextGaussian;
	} else {
        double v1, v2, s;
	    do {
            v1 = 2 * nextDouble() - 1; // between -1 and 1
        	v2 = 2 * nextDouble() - 1; // between -1 and 1
            s = v1 * v1 + v2 * v2;
	    } while (s >= 1 || s <= 0);
	    double gaussianMultiplier = sqrt(-2 * log(s)/s);
	    nextNextGaussian = v2 * gaussianMultiplier;
	    haveNextNextGaussian = true;
	    return v1 * gaussianMultiplier;
    }
}

