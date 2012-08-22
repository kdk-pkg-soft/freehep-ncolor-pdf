
#include <string>
#include <cstdlib>

#include <jni.h>

#include "HEPREP/HepRep.h"
//#include "JHepRepFactory.h"

using namespace HEPREP;
using namespace std;

int main(int argc, char** argv) {

    int result = 0;

    printf("Creating Factory...\n");
	HepRepFactory *factory = NULL; // FIXME new JHepRepFactory();

    printf("Creating HepRep...\n");
	HepRep *root = factory->createHepRep();

    printf("Creating and adding Layer...\n");
	root->addLayer("Detector");

//    <heprep:typetree name="CylinderType" version="1.0">
    printf("Creating HepRepTypeTree...\n");
    HepRepTreeID *treeID = factory->createHepRepTreeID("CylinderType", "1.0");
    HepRepTypeTree *typeTree = factory->createHepRepTypeTree(treeID);
    root->addTypeTree(typeTree);

    printf("Creating HepRepType...\n");
    HepRepType *type = factory->createHepRepType(typeTree, "Cylinder");
//    <heprep:attvalue name="banner" value="true" />
//    <heprep:attvalue name="framed" value="true" />
//    <heprep:attvalue name="drawAs" value="Cylinder" />
//    <heprep:attvalue name="radius1" value="2" />
//    <heprep:attvalue name="radius2" value="2" />
    type->addAttValue("banner", true);
    type->addAttValue("framed", true);
    type->addAttValue("drawAs", string("Cylinder"));
    type->addAttValue("radius1", 2);
    type->addAttValue("radius2", 2, HepRepConstants::SHOW_VALUE + HepRepConstants::SHOW_NAME + 0x0400);

    printf("Creating HepRepInstanceTree...\n");
//    <heprep:instancetree name="TestCylinder" version="CPlusPlus Generated" typename="CylinderType" typeversion="1.0">
    HepRepInstanceTree *instanceTree = factory->createHepRepInstanceTree("TestCylinder", "CPlusPlus Generated", typeTree);
    root->addInstanceTree(instanceTree);

    printf("Creating HepRepInstance...\n");
//    <heprep:instance type="Cylinder">
    HepRepInstance *instance1 = factory->createHepRepInstance(instanceTree, type);

//    <heprep:attvalue name="Color" value="cyan" />
//    <heprep:attvalue name="label" value="x-axis" showLabel="VALUE" />
    instance1->addAttValue("Color", string("cyan"));
    instance1->addAttValue("label", string("x-axis"), HepRepConstants::SHOW_VALUE);

//    <heprep:point x="0.0" y="0.0" z="0.0" />
//    <heprep:point x="4.0" y="0.0" z="0.0" />
    factory->createHepRepPoint(instance1, 0, 0, 0);
    factory->createHepRepPoint(instance1, 4, 0, 0);

//    <heprep:instance type="Cylinder">
    HepRepInstance *instance2 = factory->createHepRepInstance(instanceTree, type);

//    <heprep:attvalue name="Color" value="orange" />
//    <heprep:attvalue name="label" value="y-axis" showLabel="VALUE" />
    instance2->addAttValue("Color", string("orange"), 0);
    instance2->addAttValue("label", string("y-axis"), HepRepConstants::SHOW_VALUE);

//    <heprep:point x="0.0" y="0.0" z="0.0" />
//    <heprep:point x="0.0" y="4.0" z="0.0" />
    factory->createHepRepPoint(instance2, 0, 0, 0);
    factory->createHepRepPoint(instance2, 0, 4, 0);

//    <heprep:instance type="Cylinder">
    HepRepInstance *instance3 = factory->createHepRepInstance(instanceTree, type);

//    <heprep:attvalue name="Color" value="green" />
//    <heprep:attvalue name="label" value="z-axis" showLabel="VALUE" />
    instance3->addAttValue("Color", string("green"), 0);
    instance3->addAttValue("label", string("z-axis"), HepRepConstants::SHOW_VALUE);

//    <heprep:point x="0.0" y="0.0" z="0.0" />
//    <heprep:point x="0.0" y="0.0" z="4.0" />
    factory->createHepRepPoint(instance3, 0, 0, 0);
    factory->createHepRepPoint(instance3, 0, 0, 4);

//    <heprep:instance type="Cylinder">
    HepRepInstance *instance4 = factory->createHepRepInstance(instanceTree, type);

//    <heprep:attvalue name="Phi1" value="0.2" showLabel="false" />
//    <heprep:attvalue name="Phi2" value="0.3" showLabel="false" />
    instance4->addAttValue("Phi1", 0.2, 0);
    instance4->addAttValue("Phi2", 0.3, 0);

//    <heprep:point x="10.0" y="10.0" z="10.0" />
//    <heprep:point x="12.3" y="12.3" z="12.3" />
    factory->createHepRepPoint(instance4, 10, 10, 10);
    factory->createHepRepPoint(instance4, 12.3, 12.3, 12.3);

/*
    printf("Saving HepRep as xml ...\n");
    if ((result = factory->save(root, "HepRepTest.xml")) > 0) {
//        cout << "Could not write XML file: " << result << endl;
    }

    printf("Saving HepRep as xml.gz ...\n");
    if ((result = factory->save(root, "HepRepTest.xml.gz")) > 0) {
//        cout << "Could not write XML.gz file: " << result << endl;
    }

    printf("Saving HepRep as ser ...\n");
    if ((result = factory->save(root, "HepRepTest.ser")) > 0) {
//        cout << "Could not write Object file: " << result << endl;
    }

    printf("Saving HepRep as ser.gz ...\n");
    if ((result = factory->save(root, "HepRepTest.ser.gz")) > 0) {
//        cout << "Could not write Object.gz file: " << result << endl;
    }
*/

/*
    printf("Running WIRED...\n");
//    int rc = factory.runWired(1, "-verbose");
    int rc = factory.runWired(root);
    if (rc != 0) {
        switch (rc) {
            case 1:  fprintf(stderr, "Cannot find class 'ch/cern/wired/application/HepRepWired'\n");
                break;
            case 2:  fprintf(stderr, "Cannot find constructor 'HepRepWired(hep.graphics.heprep.HepRep root, String wiredHome)'\n");
                break;
            case 3:  fprintf(stderr, "Cannot find method 'void run(boolean verbose)'\n");
                break;
            case 4:  fprintf(stderr, "Cannot instantiate HepRepWired (out of memory)'\n");
                break;
            case 5:  fprintf(stderr, "Exception thrown in 'void run()'\n");
                break;
            case 6:  fprintf(stderr, "Could not find environment variable WIRED_HOME'\n");
                break;
            case 7:  fprintf(stderr, "Cannot instantiate jwiredHome (out of memory)'\n");
                break;
            default: fprintf(stderr, "Unknown error code: %i\n", rc);
                break;
        }
        factory.error("Could not start WIRED");
    }
*/
    printf("Test finished ok.\n");

	return result;
}
