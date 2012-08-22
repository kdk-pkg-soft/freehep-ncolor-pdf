%module(directors="1") ConnexionAdapter

%{
#include "ConnexionCallback.h"
#include "Connexion.h"
%}

%ignore CONNEXION_CALLBACK_H; 
%feature("director") ConnexionCallback;

%include "ConnexionCallback.h"
%include "Connexion.h"
 
%pragma(java) jniclasscode=%{
    static {
        String libName = "freehep-connexion"+"-"+"1.0-SNAPSHOT";
        try {
            System.err.print("Loading JNI Library: "+libName+"...");
            System.loadLibrary(libName);
            System.err.println("OK.");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("\nJNI Library "+libName+" failed to load.\n" + e);
            System.exit(1);
        }
    }
%}

