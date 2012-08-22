// Copyright 2003-2006, FreeHEP.

/**
 * @author The FreeHEP Team
 * @version $Id: minuitAdapter.cpp 8350 2006-06-09 22:26:34Z duns $
 */

#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <assert.h>

#include <stdio.h>

#include <jni.h>
#include "org_freehep_math_fminuit_FMinuitCommands.h"


/* #define DEBUG 1 */

#ifdef INT16
typedef long           Int_t;       /*Signed integer 4 bytes*/
#else
typedef int            Int_t;       /*Signed integer 4 bytes*/
#endif

#if defined(WIN32) && !defined(GNU_GCC)
# define mninit MNINIT
# define mnparm MNPARM
# define mnseti MNSETI
# define mnstat MNSTAT
# define mnerrs MNERRS
# define mnexcm MNEXCM
# define mnpout MNPOUT
# define mnemat MNEMAT
# define mncont MNCONT
# define minuit MINUIT
# define type_of_call  _stdcall
# define DEFCHAR  const char*, const int
# define PASSCHAR(string) string, strlen(string)
# define FUNCTIONPTR void*
#else
# define mninit mninit_
# define mnparm mnparm_
# define mnseti mnseti_
# define mnstat mnstat_
# define mnerrs mnerrs_
# define mnexcm mnexcm_
# define mnpout mnpout_
# define mnemat mnemat_
# define mncont mncont_
# define minuit minuit_
# define type_of_call
# define DEFCHAR  const char*
# define PASSCHAR(string) string
# define FUNCTIONPTR void (int&, double* , double& , double*, int&, void*)
# define UNIXCALL 1
#endif

extern "C" void  type_of_call mninit(const int&,const int&,const int&);
extern "C" void  type_of_call mnparm(const int&, DEFCHAR, const double&, const double&, const double&, const double&, const int&
#ifdef UNIXCALL
                        ,const int
#endif
                        );
extern "C" void  type_of_call mnseti(DEFCHAR
#ifdef UNIXCALL
                        ,const int
#endif
                        );
extern "C" void type_of_call mnstat(const double&, const double&, const double&, const int&, const int&, const int&);
extern "C" void type_of_call mnerrs(const int&, const double&, const double&, const double&, const double&);
extern "C" void type_of_call mnexcm(FUNCTIONPTR, DEFCHAR, const double&, const int&, const int&, const void* 
#ifdef UNIXCALL
				    ,const int
#endif
                        );

extern "C" void type_of_call minuit(FUNCTIONPTR, const void*);
extern "C" void type_of_call mnpout(const int&, DEFCHAR, const double&, const double&, const double&, const double&, const int&
#ifdef UNIXCALL
				    ,const int
#endif
                        );
extern "C" void type_of_call mnemat(double*, const int&);
extern "C" void type_of_call mncont(FUNCTIONPTR, const int&, const int&, const int&, const double&, const double&, const int&, const void*);

#ifdef WIN32
extern "C" void type_of_call function(int &np, double *gin, double &f, double *par, int &flag, void *futil);
#else
extern "C" void function(int &np, double *gin, double &f, double *par, int& flag, void *futil);
#endif

/* Runtime Exception */
static jclass runtimeExceptionClass;

/* These variable are only valid during a call to function */
static int dimension;
static JNIEnv* globalEnv;
static jclass minuitClass;
static jmethodID initFunc;
static jmethodID evalFunc;
static jmethodID evalDeriv;
static jmethodID finFunc;

#define SET_STATICS(_dim, _env, _cls) \
    dimension = _dim; \
    globalEnv = _env; \
    minuitClass = _cls; \
    initFunc   = _env->GetStaticMethodID(_cls, "initializeFunction", "()V"); \
    evalFunc   = _env->GetStaticMethodID(_cls, "evaluateFunction", "([D)D"); \
    evalDeriv  = _env->GetStaticMethodID(_cls, "evaluateDerivatives", "([D)[D"); \
    finFunc    = _env->GetStaticMethodID(_cls, "finalizeFunction", "()V");
 
#define CLEAR_STATICS \
    dimension = -1; \
    globalEnv = NULL; \
    minuitClass = NULL; \
    initFunc = NULL; \
    evalFunc = NULL; \
    evalDeriv = NULL; \
    finFunc = NULL;

/**
 * Initialize minuit.
 * @param inputUnit  Unit number for input to Minuit (default 5)
 * @param outputUnit Unit number for output from Minuit (default 6)
 * @param saveUnit   Unit number to be used for SAVE command (default 7)
 */
JNIEXPORT void JNICALL Java_org_freehep_math_fminuit_FMinuitCommands_jmninit
(JNIEnv *env, jclass min, jint inputUnit, jint outputUnit, jint saveUnit)
{
#ifdef DEBUG
  printf("jmninit(...);\n");
#endif
  runtimeExceptionClass = env->FindClass("java/lang/RuntimeException");
  if (runtimeExceptionClass == NULL) {
    printf("jmninit(): Fatal error, cannot find class 'java.lang.RuntimeException\n");
    return;
  }  
  mninit( inputUnit, outputUnit, saveUnit );
}

/**
 * Set the title of the minimization session.
 * @param title The title.
 *
 */
JNIEXPORT void JNICALL Java_org_freehep_math_fminuit_FMinuitCommands_jmnseti
  (JNIEnv *env, jclass min, jstring title)
{
#ifdef DEBUG
  printf("jmnseti(...);\n");
#endif
  const char *titleChar = env->GetStringUTFChars(title, 0);
  if (titleChar == NULL) env->ThrowNew(runtimeExceptionClass, "Could not convert title string to UTF");
  if ( strlen(titleChar) > 50 ) printf("The title \"%s\" is too long, it will be cut\n",titleChar);
  mnseti(PASSCHAR(titleChar)
#if !defined(WIN32) || defined(GNU_GCC)
	 ,strlen(titleChar)
#endif
	 );
  env->ReleaseStringUTFChars(title,titleChar);
}

/**
 * Define a new fit parameter.
 * @param parNum   The parameter number (+1) as referenced by the IFunction to be
 *                 minimized.
 * @param parName  The parameter's name. (maximum 10 characters).
 * @param parValue The parameter's starting value.
 * @param stepSize Starting step size or approximate error.
 * @param minValue The parameter's lower limit.
 * @param maxValue The parameter's upper limit. If the upper and the lower limits are set to 0,
 *                 the parameter is considered unbounded.
 * @return Error code. 0 if no error, > 0 if request failed.
 *
 */
JNIEXPORT jint JNICALL Java_org_freehep_math_fminuit_FMinuitCommands_jmnparm
  (JNIEnv *env, jclass min, jint parNum, jstring parName, jdouble parValue, jdouble stepSize, jdouble minValue, jdouble maxValue)
{
#ifdef DEBUG
  printf("jmnparm(...);\n");
#endif
  int status = 99;
  const char *name = env->GetStringUTFChars(parName, 0);
  if (name == NULL) env->ThrowNew(runtimeExceptionClass, "Could not convert parname string to UTF");
  if ( strlen(name) > 10 ) printf("The parameter's name \"%s\" is too long, it will be cut\n",name);
  mnparm(parNum,PASSCHAR(name),parValue,stepSize,minValue,maxValue,status
#ifdef UNIXCALL
	 ,strlen(name)
#endif
	 );
  env->ReleaseStringUTFChars(parName,name);
  return status;
}

/**
 * Execute a Minuit command, the command is executed on the current function.
 * @param command The Minuit command to be executed.
 * @param argList Array containing the numeric arguments to the command.
 * @param nArg    The number of arguments specified.
 * @return Error code. 0 if no error, > 0 otherwise.
 *
 */
JNIEXPORT jint JNICALL Java_org_freehep_math_fminuit_FMinuitCommands_jmnexcm
  (JNIEnv *env, jclass min, jstring command, jdoubleArray argList, jint nArg)
{
#ifdef DEBUG
  printf("jmnexcm(...);\n");
#endif
  jdouble *arg = env->GetDoubleArrayElements(argList, 0);
  if (arg == NULL) env->ThrowNew(runtimeExceptionClass, "Could not access argList array");
  const char *commandChar = env->GetStringUTFChars(command, 0);
  if (commandChar == NULL) env->ThrowNew(runtimeExceptionClass, "Could not convert command string to UTF");
  int status = 99;

  double v[3];
  int i1,dim;
  mnstat(v[0],v[1],v[2],i1,dim, status);

  SET_STATICS(dim, env, min)
  mnexcm(function, PASSCHAR(commandChar), arg[0], nArg, status, 0
#ifdef UNIXCALL
	 ,strlen(commandChar)
#endif
	 );
  CLEAR_STATICS;
  if (env->ExceptionOccurred()) { return status; }

  env->ReleaseStringUTFChars(command,commandChar);
  env->ReleaseDoubleArrayElements(argList,arg,0);

  return status;
}

/**
 * Get the current value of a parameter.
 * @param parNum  The parameter's number (+1) as referenced in the function to be minimized.
 * @param parName The parameter's name.
 * @param parVals The parameter's values. This array should contain four entries: the
 *                current value of the parameter, the current error on the parameter,
 *                the parameter's lower and upper bound (both 0 if parameter is unbounded).
 * @return Internal parameter's number (as used by the fit); 0 if parameter is a constant,
 *         a negative number if parameter is not defined.
 *
 */

JNIEXPORT jint JNICALL Java_org_freehep_math_fminuit_FMinuitCommands_jmnpout
  (JNIEnv *env, jclass min, jint parNum, jobjectArray parName, jdoubleArray parVals)
{
#ifdef DEBUG
  printf("jmnpout(...);\n");
#endif
  int intNum;
  const char name[10] = "";
  double vals[4];
  mnpout( parNum, PASSCHAR(name), vals[0], vals[1], vals[2], vals[3], intNum
#ifdef UNIXCALL
	  ,10
#endif
	  );
  env->SetDoubleArrayRegion(parVals, 0, 4, vals);
  env->SetObjectArrayElement(parName,0,env->NewStringUTF(name));
  return intNum;
}

/**
 * Get the current status of minimization.
 * @param vals The values of the minimization. This array contains the following three
 *             entries: the current best value of the function, the estimated vertical distance
 *             to the minimum, the value of UP defining the errors.
 * @param pars The parameters information. This array contains the following two entries:
 *             the number of variables in the fit, the highest parameter number (as seen
 *             by the function to be minimized).
 * @return The status of the fit telling at what stage the covariance matrix is:
 *         0 not calculated, 1 diagonal approximation, 2 full matrix but forced positive-definite,
 *         3 full accurate matrix.
 *
 */
JNIEXPORT jint JNICALL Java_org_freehep_math_fminuit_FMinuitCommands_jmnstat
  (JNIEnv *env, jclass min, jdoubleArray vals, jintArray pars)
{
#ifdef DEBUG
  printf("jmnstat(...);\n");
#endif
  int stat = 99;
  double v[3];
  int i1,i2;
  mnstat(v[0],v[1],v[2],i1,i2, stat);
  env->SetDoubleArrayRegion(vals, 0, 3, v);
  jint p[2];
  p[0] = i1;
  p[1] = i2;
  env->SetIntArrayRegion(pars, 0, 2, p);
  return stat;
}

/**
 * Get the covariance matrix.
 * @param errorMatrix The error matrix.
 * @param nDim        The dimension of the error matrix (at least the number of free variables).
 *
 */
JNIEXPORT void JNICALL Java_org_freehep_math_fminuit_FMinuitCommands_jmnemat
  (JNIEnv *env, jclass min, jdoubleArray errorMatrix, jint nDim)
{
#ifdef DEBUG
  printf("jmnemat(...);\n");
#endif
  double* errMat;
//  errMat = new double[nDim*nDim];
  errMat = (double*)malloc(nDim*nDim*sizeof(double));
  mnemat(errMat, nDim);
  env->SetDoubleArrayRegion(errorMatrix, 0, nDim*nDim, errMat);
  free(errMat);
}

/**
 * Get the current parameter's errors.
 * @param parNum The parameter's number; if positive it is the number as referenced
 *               by the function to be minimized, if negative it is the negative of the
 *               parameter number as used within Minuit (the internal representation)
 * @param errors The parameter's errors. This array has four entries: the positive MINOS
 *               error, the negative MINOS error, the parabolic error from the error matrix
 *               and the global correlation coerricient.
 *
 */
JNIEXPORT void JNICALL Java_org_freehep_math_fminuit_FMinuitCommands_jmnerrs
  (JNIEnv *env, jclass min, jint parNum, jdoubleArray errors)
{
#ifdef DEBUG
  printf("jmnerrs(...);\n");
#endif
  double errs[4];
  mnerrs(parNum, errs[0],errs[1],errs[2],errs[3]);
  env->SetDoubleArrayRegion(errors, 0, 4, errs);
}


/**
 * Get the two dimensional contour for the current configuration.
 * @param parNum1 The external number of the first parameter.
 * @param parNum2 The external number of the second parameter.
 * @param nPoints The number of points that are to be calculated on the contour (>4).
 * @param xPoints The array of the x-coordinate of the found points.
 * @param yPoints The array of the y-coordinate of the found points.
 * @param nFound  The number of points that have been found.
 *
 */
JNIEXPORT void JNICALL Java_org_freehep_math_fminuit_FMinuitCommands_jmncont
  (JNIEnv *env, jclass min, jint parNum1, jint parNum2, jint nPoints, jdoubleArray xPoints, jdoubleArray yPoints, jintArray nFound)
{
#ifdef DEBUG
  printf("jmncont(...);\n");
#endif
//  double *x = new double[nPoints];
//  double *y = new double[nPoints];
  double* x = (double*)malloc(nPoints*sizeof(double));
  double* y = (double*)malloc(nPoints*sizeof(double));
  int found = -99;

  double v[3];
  int i1,dim,status;
  mnstat(v[0],v[1],v[2],i1,dim, status);

  SET_STATICS(dim, env, min);
  mncont(function, parNum1, parNum2, nPoints, x[0], y[0], found, 0);
  CLEAR_STATICS;
  if (env->ExceptionOccurred()) { return; }
  
  env->SetDoubleArrayRegion(xPoints, 0, found, x);
  env->SetDoubleArrayRegion(yPoints, 0, found, y);
  free(x);
  free(y);

  jint jfound[1] = {found};
  env->SetIntArrayRegion(nFound, 0, 1, jfound);
}

void type_of_call function(int &np, double *gin, double &f, double *par, int &flag, void *futil)
{
#ifdef DEBUG
  printf("function(...);\n");
#endif
    assert(globalEnv != NULL);

// FIXME, it looks we could move this allocation to the calling minuit function, as the dimension
// never changes.
    jdoubleArray parArray = globalEnv->NewDoubleArray((jint)(dimension));
    if (parArray == NULL) {
        f = 0;
        globalEnv->ThrowNew(runtimeExceptionClass, "Could not allocate array for parameters");
        return;
    }
    //    globalEnv->ExceptionDescribe();
    globalEnv->SetDoubleArrayRegion(parArray, 0, dimension, par);
    if (globalEnv->ExceptionOccurred()) {
        f = 0;
        return;
    }

    assert(minuitClass != NULL);
    switch(flag) {
        case 1: {
            // Read input data, calculate any necessary configuration variable.
            assert(initFunc != NULL);
            globalEnv->CallStaticVoidMethod(minuitClass,initFunc);
            if (globalEnv->ExceptionOccurred()) {
                f = 0;
                return;
            }
            break;
        }
        case 2: {
            // Calculate the first derivatives of the function
            jdoubleArray derArray = globalEnv->NewDoubleArray((jint)(dimension));
            assert(evalDeriv != NULL);
            derArray =  (jdoubleArray)globalEnv->CallStaticObjectMethod(minuitClass,evalDeriv,parArray);
            if (globalEnv->ExceptionOccurred()) {
                f = 0;
                return;
            }

            jboolean isCopy;

//            globalEnv->GetDoubleArrayRegion(derArray,1,(jint)dimension, gin);
            gin = globalEnv->GetDoubleArrayElements(derArray,&isCopy);
            globalEnv->ReleaseDoubleArrayElements(derArray,gin,JNI_COMMIT);
            break;
        }
        case 3: {
            // Finalization phase. This is called after the function has been minimized.
            assert(finFunc != NULL);
            globalEnv->CallStaticVoidMethod(minuitClass, finFunc);
            if (globalEnv->ExceptionOccurred()) {
                f = 0;
                return;
            }
            break;
        }
        case 4: {
            // Fit
            break;
        }
        default: {
            printf("Illegal value of flag: %d in function. Out of range [1..3]\n", flag);
            f = 0;
            return;
        }
    }        

    assert(evalFunc != NULL);
    f = globalEnv->CallStaticDoubleMethod(minuitClass,evalFunc,parArray);
    if (globalEnv->ExceptionOccurred()) {
        f = 0;
        return;
    }
}
