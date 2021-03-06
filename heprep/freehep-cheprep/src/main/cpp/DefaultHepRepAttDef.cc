// Copyright FreeHEP, 2005.

#include <iostream>
#include <cstring>
#include <cctype>
#include <algorithm>

#include "cheprep/DefaultHepRepAttDef.h"

using namespace std;
using namespace HEPREP;

/**
 * @author Mark Donszelmann
 * @version $Id: DefaultHepRepAttDef.cc 8617 2006-08-16 07:39:12Z duns $
 */
namespace cheprep {

DefaultHepRepAttDef::DefaultHepRepAttDef(string name, string desc, string category, string extra)
    : name(name), desc(desc), category(category), extra(extra) {
}

DefaultHepRepAttDef::~DefaultHepRepAttDef() {
}

HepRepAttDef* DefaultHepRepAttDef::copy() {
    return new DefaultHepRepAttDef(name, desc, category, extra);
}

string DefaultHepRepAttDef::getName() {
    return name;
}

string DefaultHepRepAttDef::getLowerCaseName() {
    string s = name;
    transform(s.begin(), s.end(), s.begin(), (int(*)(int)) tolower);
    return s;
}

string DefaultHepRepAttDef::getDescription() {
    return desc;
}

string DefaultHepRepAttDef::getCategory() {
    return category;
}

string DefaultHepRepAttDef::getExtra() {
    return extra;
}

} // cheprep
