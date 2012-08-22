/*
** MODULE OVERVIEW
** The module allows the calling program to access PAW files.

** Created by Kevin garwood, major modifications by Tony Johnson, completed (?) 4/17/98
** This code is derived from (but by now very different from) Rene Brun's UTILS_h2root.cxx

** LIMITATIONS:
** Multi thread access to PAW files, and access to multiple files simultaneously is at best untested.

** FURTHER INFORMATION:
** HBOOK Functions are described at:
	http://ejsn.phys.sci.osaka-u.ac.jp/~ajimura/cern/hbook_html3/node168.html
** Other interesting links:
** example ntuples
	http://www.mppmu.mpg.de/Introduction/cernlib/hbook/section2_6_6.html#SECTION0066100000000000000
** HBook manual:
	http://www.mppmu.mpg.de/Introduction/cernlib/hbook/tableofcontents2_1.html
**
**
*/

#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include <stdio.h>

#include "hep_io_hbook_Hbook.h"
#include <jni.h>


// NB When linking under windows, the size for PAWC specified in HNT_VAR2.f seems to be
// what determines the memory allocated, so if the size of PAWC here is increased it is
// important to also increase the size in that routine.

// MD set the size to the same as in HNT_VAR2.f which is 1000000 + 11 
#define PAWC_SIZE 1000011


#ifdef INT16
typedef long           Int_t;       /*Signed integer 4 bytes*/
#else
typedef int            Int_t;       /*Signed integer 4 bytes*/
#endif



/*
**  Define the names of the Fortran common blocks for the different OSs
*/

#if defined(WIN32) && !defined(GNU_GCC)
#  define pawc   PAWC
#  define quest  QUEST
#  define hcbits HCBITS
#  define hcbook HCBOOK
#  define rzcl   RZCL
#  define zunit  ZUNIT
extern "C" int pawc[PAWC_SIZE];
extern "C" int quest[100];
extern "C" int hcbits[37];
extern "C" int hcbook[51];
extern "C" int rzcl[11];
#else
#  define pawc pawc_
#  define quest quest_
#  define hcbits hcbits_
#  define hcbook hcbook_
#  define rzcl rzcl_
int pawc[PAWC_SIZE];
int quest[100];
int hcbits[37];
int hcbook[51];
int rzcl[11];
#endif

static int *iq, *lq;
static float *q;

static Int_t golower  = 1;
static Int_t openCount = 0;
static Int_t nextid = 100;

/*
  Define the names of the Fortran subroutine and functions for the different OSs
*/

#if defined(WIN32) && !defined(GNU_GCC)
# define hlimit  HLIMIT
# define hropen  HROPEN
# define hrend   HREND
# define hrin    HRIN
# define hnoent  HNOENT
# define hgive   HGIVE
# define hgiven  HGIVEN
# define hgnpar  HGNPAR
# define hgnf    HGNF
# define hgntf   HGNTF
# define hgnt    HGNT
# define rzink   RZINK
# define hdcofl  HDCOFL
# define hdelet  HDELET
# define hntvar2 HNTVAR2
# define hbname  HBNAME
# define hbnam   HBNAM
# define hcdir   HCDIR
# define hi      HI
# define hie     HIE
# define hij     HIJ
# define hije    HIJE
# define hstati  HSTATI
# define zitoh   ZITOH
# define uhtoc   UHTOC
# define fclose  FCLOSE
# define type_of_call  _stdcall
# define DEFCHAR  const char*, const int
# define PASSCHAR(string) string, strlen(string)

#else
# define hlimit  hlimit_
# define hropen  hropen_
# define hrend   hrend_
# define hrin    hrin_
# define hnoent  hnoent_
# define hgive   hgive_
# define hgiven  hgiven_
# define hgnpar  hgnpar_
# define hgnf    hgnf_
# define hgnt    hgnt_
# define hgntf   hgntf_
# define rzink   rzink_
# define hdcofl  hdcofl_
# define hdelet  hdelet_
# define hntvar2 hntvar2_
# define hbname  hbname_
# define hbnam   hbnam_
# define hcdir   hcdir_
# define hi      hi_
# define hie     hie_
# define hij     hij_
# define hije    hije_
# define hstati  hstati_
# define zitoh   zitoh_
# define uhtoc   uhtoc_
# define fclose  fclose_

# define type_of_call
# define DEFCHAR  const char*
# define PASSCHAR(string) string
# define UNIXCALL 1

#endif

extern "C" void  type_of_call hlimit(const int&);
extern "C" void  type_of_call hdelet(const int&);
extern "C" void  type_of_call hropen(const int&,DEFCHAR,DEFCHAR,DEFCHAR,
                        const int&,const int&
#ifdef UNIXCALL
                        ,const int,const int,const int
#endif
                        );
extern "C" void  type_of_call hrend(DEFCHAR
#ifdef UNIXCALL
                        ,const int
#endif
                        );
extern "C" void  type_of_call hrin(const int&,const int&,const int&);
extern "C" void  type_of_call hnoent(const int&,const int&);
extern "C" void  type_of_call hgive(const int&,DEFCHAR,const int&,const float&,const float&,
   const int&,const float&,const float&,const int&,const int&
#ifdef UNIXCALL
   ,const int
#endif
   );
extern "C" void  type_of_call hgiven(const int&,DEFCHAR,const int&,DEFCHAR,
   const float&,const float&
#ifdef UNIXCALL
  ,const int,const int
#endif
  );
extern "C" void  type_of_call hntvar2(const int&,const int&,DEFCHAR,DEFCHAR,DEFCHAR,int&,int&,int&,int&
#ifdef UNIXCALL
   ,const int,const int, const int
#endif
   );
extern "C" void  type_of_call hbnam(const int&,DEFCHAR,const int&,DEFCHAR,const int&
#ifdef UNIXCALL
   ,const int, const int
#endif
   );

extern "C" float type_of_call hi(const int&, const int&);
extern "C" float type_of_call hie(const int &, const int&);
extern "C" float type_of_call hij(const int&, const int&, const int&);
extern "C" float type_of_call hije(const int &, const int&, const int&);

extern "C" float type_of_call hstati(const int&, const int&, DEFCHAR, const int&
#ifdef UNIXCALL
          ,const int
#endif
          );

extern "C" void  type_of_call hgnpar(const int&,const char *,const int);
extern "C" void  type_of_call hgnf(const int&,const int&,const float&,const int&);
extern "C" void  type_of_call hgnt(const int&,const int&,const int&);
extern "C" void  type_of_call hgntf(const int&,const int&,const int&);
extern "C" void  type_of_call rzink(const int&,const int&,const char *,const int);
extern "C" void  type_of_call hdcofl();
extern "C" void  type_of_call fclose(const int&);

extern "C" void  type_of_call hcdir(DEFCHAR,DEFCHAR
#ifdef UNIXCALL
          ,const int,const int
#endif
          );

extern "C" void  type_of_call zitoh(const int&,const int&,const int&);
extern "C" void  type_of_call uhtoc(const int&,const int&,DEFCHAR,int&
#ifdef UNIXCALL
   ,const int
#endif
  );

/*
***************************************************************************************************
** DEFINES
***************************************************************************************************
*/
#define FOLDER 0
#define CWTUPLE 1
#define RWTUPLE 2
#define VERSION 2


/*
***************************************************************************************************
** PROTOTYPES
***************************************************************************************************
*/

extern void getChildren(Int_t id);


extern int visitCWTuple(Int_t id, JNIEnv *env, jobject ntuple);
extern int visitRWTuple(Int_t id, JNIEnv *env, jobject ntuple);
extern int visitDirectory(const char *dir, JNIEnv *env, jobject parent);


static char *getTupleName(Int_t id);


/*
*************************************************************
*openFile
*************************************************************
*/


JNIEXPORT jobject JNICALL Java_hep_io_hbook_Hbook_openFile
	(JNIEnv *env, jclass hbk, jstring file, jint lun, jint record_size)
{
	/*
	** PART I:  Open an Hbook file.
	*/

	/*
	** We have to convert the jstring in the header of this method to a regular string to
	** satisfy a parameter type in hropen
	*/


	const char *str = env->GetStringUTFChars(file, 0);

	/*
	** open the file
	*/
	int ier = 0;
	char root[9];
	sprintf(root,"r%7.7d",openCount++);
	hropen(lun,PASSCHAR(root),PASSCHAR(str),PASSCHAR("p"),record_size,ier
#ifdef UNIXCALL
         ,strlen(root),strlen(str),1
#endif
        );
	/* release the memory we used for getting the str */
	env->ReleaseStringUTFChars(file, str);

	if (ier != 0) return NULL; // indicates file open failure

	/*
	** PART II:  create an HbookObject for the new file we opened
	*/

	/*
	** we have to find the class that corresponds to the string HbookObject.  Then we have to call
	** the constructor we want.  Then construct the arguments that will be fed to that constructor
	** Then invoke NewObject
	*/

	jclass cls = env->FindClass("hep/io/hbook/HbookFileHObj");

	/* get constructor for HbookObject with constructor signature void(String String Int) */
	jmethodID jm = env->GetMethodID(cls, "<init>", "(Ljava/lang/String;ILjava/lang/String;)V");
	jstring jroot = env->NewStringUTF(root);
	return env->NewObject(cls, jm, file, lun, jroot);
}


/*
*************************************************************
*getChildren
*************************************************************
*/


JNIEXPORT jint JNICALL Java_hep_io_hbook_Hbook_visitChildren(JNIEnv *env, jclass xcls, jobject parent)
{
	/*
	** extract parentType from object
	*/
	jclass cls = env->GetObjectClass(parent);
	jfieldID fid = env->GetFieldID(cls, "type", "I");
	int parentType = (int) env->GetIntField(parent, fid);

	int nc = 0;

	if      (parentType == FOLDER)
	{
		jmethodID jm = env->GetMethodID(cls, "getDirectoryPath", "()Ljava/lang/String;");
		jstring jpath = (jstring) env->CallObjectMethod(parent, jm);
 		const char *path = env->GetStringUTFChars(jpath, 0);
		nc = visitDirectory(path, env, parent);
		env->ReleaseStringUTFChars(jpath, path);
	}
 	else if (parentType == CWTUPLE)
	{
		fid = env->GetFieldID(cls, "id", "I");
		int tupleID = (int) env->GetIntField(parent,fid);
		nc = visitCWTuple(tupleID, env, parent);
	}
	else if (parentType == RWTUPLE)
	{
		fid = env->GetFieldID(cls, "id", "I");
		int tupleID = (int) env->GetIntField(parent,fid);
		nc = visitRWTuple(tupleID, env, parent);
	}
	return nc;
}
JNIEXPORT void JNICALL Java_hep_io_hbook_Hbook_setRWEvent
  (JNIEnv *env, jclass cls, jint id, jint event, jlong in)
{
	int ier = 0;
	const float *buffer = (float *)(jint)in;
        hgnpar(id,"?",1); // todo: what is this??
        hgnf(id,event,buffer[0],ier);
}
JNIEXPORT void JNICALL Java_hep_io_hbook_Hbook_setCWEvent
  (JNIEnv *env, jclass cls, jint id, jint event)
{
	int ier = 0;
    hgntf(id,event,ier);

}
JNIEXPORT jdouble JNICALL Java_hep_io_hbook_Hbook_getRWData
  (JNIEnv *env, jclass cls, jlong in, jint row)
{
	float *buffer = (float *)(jint)in;
    return buffer[row];
}
JNIEXPORT jdouble JNICALL Java_hep_io_hbook_Hbook_getCWDataDouble
  (JNIEnv *env, jclass cls, jlong in, jint offset, jint size)
{
	char *buffer = (char *)(jint)in;
	char *data = buffer + offset;
    if (size == 8) return *((double*) data);
	if (size == 4) return *((float*) data);
	return 0;
}
JNIEXPORT jint JNICALL Java_hep_io_hbook_Hbook_getCWDataInt
  (JNIEnv *env, jclass cls, jlong in, jint offset, jint size)
{
	char *buffer = (char *)(jint)in;
	char *data = buffer + offset;
    if (size == 4) return *((int*) data);
	if (size == 2) return *((short*) data);
	return 0;
}
JNIEXPORT jlong JNICALL Java_hep_io_hbook_Hbook_getCWDataLong
  (JNIEnv *env, jclass cls, jlong in, jint offset, jint size)
{
	char *buffer = (char *)(jint)in;
	char *data = buffer + offset;
    if (size == 8) return *((long*) data);
    if (size == 4) return *((int*) data);
	if (size == 2) return *((short*) data);
	return 0;
}
JNIEXPORT jstring JNICALL Java_hep_io_hbook_Hbook_getCWDataString
  (JNIEnv *env, jclass cls, jlong in, jint offset, jint size)
{
	char *buffer = (char *)(jint)in;
	char *data = buffer + offset;
	int l;
	for (l=size; l>0; l--) if (data[l-1] != ' ') break;
//	char* temp = new char[l+1];
    char* temp = (char*)malloc((l+1)*sizeof(char));
	strncpy(temp,data,l);
	temp[l] = '\0';
	jstring result = env->NewStringUTF(temp);
//	delete temp;
    free(temp);
	return result;
}


/*
*************************************************************
*closeFile
*************************************************************
*/
JNIEXPORT void JNICALL Java_hep_io_hbook_Hbook_closeFile
  (JNIEnv *env, jclass cls, jstring jroot, jint lun)
{
	const char *root = env->GetStringUTFChars(jroot, 0);
	hrend(PASSCHAR(root)
#ifdef UNIXCALL
         ,strlen(root)
#endif
        );
	fclose(lun);
	env->ReleaseStringUTFChars(jroot, root);
}

JNIEXPORT void JNICALL Java_hep_io_hbook_Hbook_delete
  (JNIEnv *env, jclass cls, jint id)
{
	hdelet(id);
}

/*
*************************************************************
*init
*************************************************************
*/

JNIEXPORT jint JNICALL Java_hep_io_hbook_Hbook_init(JNIEnv *env, jclass cls)
{
	/*
	** When run without a console (e.g. as a service or as a windows application, fortran output
	** to unix 6 will crash the program. This fix gets rid of the output.
	*/

#ifdef SILENT
    putenv("FORT6=NUL:");
#endif

	/*
	** there are a number of global variables Brun sets in his code.  I still don't entirely know
	** what they do
	*/

	lq = &pawc[9];
	iq = &pawc[17];
	void *qq = iq;
	q = (float*)qq;

	/*
	** sets how big file will be
	*/

	int pawc_size = PAWC_SIZE;
	hlimit(pawc_size);

        return VERSION;
}
JNIEXPORT jdoubleArray JNICALL Java_hep_io_hbook_Hbook_RWrebin
  (JNIEnv *env, jclass cls, jint id, jint col, jint size, jint bins, jdouble min, jdouble max)
{
//	double *hist = new double[bins];
	double *hist = (double*)malloc(sizeof(double)*bins);
	double binWidth = (max - min)/bins;
//	float *x = new float[size];
	float *x = (float*)malloc(sizeof(float)*size);
	int nentries;
	int ier = 0;

	for (int j=0; j<bins; j++) hist[j] = 0;

	hnoent(id,nentries);

	for (int i = 0; i < nentries; )
	{
		hgnf(id,++i,x[0],ier); // NB i starts at 1!
		double d = x[col];

		if (d>=min && d<max) hist[(int) (  (d - min)/binWidth  )]++;
	}

	/*
	** return array of doubles
	*/

	jdoubleArray result = env->NewDoubleArray(bins);
	env->SetDoubleArrayRegion(result, 0, bins, hist);
//	delete hist;
	free(hist);

//	delete x;
	free(x);
	return result;
}
JNIEXPORT jdoubleArray JNICALL Java_hep_io_hbook_Hbook_CWgetMinMax
  (JNIEnv *env, jclass cls, jint id, jlong in, jint offset, jint type, jint size, jint fixed, jint indexOffset)
{
	double min = 0, max = -1;
	int nentries, ier=0;
	char *buffer = (char *)(jint)in;

	hnoent(id,nentries);

	for (int i = 0; i < nentries; )
	{
           hgntf(id,++i,ier);

	   int elem = fixed;
	   if (indexOffset >= 0)
	   {
		   char *data = buffer + indexOffset;
           int count = *((int*) data);
		   elem *= count;
	   }
	   char *data = buffer + offset;
	   for (int j=0; j<elem; j++)
	   {
		   double d = 0;
		   if (type == 1 && size == 4)
		   {
              d = *((float*) data);
		   }
		   else if (type == 1 && size == 8)
		   {
              d = *((double*) data);
		   }
		   else if (type == 2 || type == 4)
		   {
              d = *((int*) data);
		   }
		   else if (type == 3)
		   {
              d = *((long*) data);
		   }
		   if (max < min)
		   {
			   min = d;
			   max = d;
		   }
		   else
		   {
			   min = d<min ? d : min;
			   max = d>max ? d : max;
		   }
		   data += size;
	   }
	}

	/*
	** return array of doubles
	*/

	jdoubleArray result = env->NewDoubleArray(2);
	env->SetDoubleArrayRegion(result, 0, 1, &min);
	env->SetDoubleArrayRegion(result, 1, 1, &max);
	return result;
}
JNIEXPORT jlong JNICALL Java_hep_io_hbook_Hbook_allocBuffer
  (JNIEnv * env, jclass cls, jint size)

{
//	char *buffer = new char[size];
	char *buffer = (char*)malloc(sizeof(char)*size);
	return (long) buffer;
}
JNIEXPORT void JNICALL Java_hep_io_hbook_Hbook_freeBuffer
  (JNIEnv * env, jclass cls, jlong in)
{
	char *buffer = (char *)(jint)in;
//	delete buffer;
	free(buffer);
}
JNIEXPORT void JNICALL Java_hep_io_hbook_Hbook_CWMap
  (JNIEnv *env, jclass cls, jint id, jstring jblock, jlong in, jstring jsetName, jint offset, jint type)
{
	char *buffer = (char *)(jint)in;
	buffer += offset;
    Int_t add= (Int_t) buffer;

	const char *block = env->GetStringUTFChars(jblock, 0);
	const char *setName = env->GetStringUTFChars(jsetName, 0);

	Int_t isachar = type==5 ? 1 : 0;

	hbnam(id,PASSCHAR(block),add,PASSCHAR(setName),isachar
#ifdef UNIXCALL
         ,strlen(block),strlen(setName)
#endif
    );

	// Called here so we can use hgntf (faster) later (also if map is called after setCWvent)
	int ier = 0;
    hgnt(id,1,ier);

	env->ReleaseStringUTFChars(jblock, block);
	env->ReleaseStringUTFChars(jsetName, setName);
}
JNIEXPORT void JNICALL Java_hep_io_hbook_Hbook_CWClearMap
  (JNIEnv *env, jclass cls, jint id, jstring jblock, jlong in)
{
	char *buffer = (char *)(jint)in;
    Int_t add= (Int_t) buffer;

	const char *block = env->GetStringUTFChars(jblock, 0);

	hbnam(id,PASSCHAR(block),add,PASSCHAR("$CLEAR"),0
#ifdef UNIXCALL
   ,strlen(block),6
#endif
	);
	env->ReleaseStringUTFChars(jblock, block);
}

JNIEXPORT jdoubleArray JNICALL Java_hep_io_hbook_Hbook_CWrebin
  (JNIEnv *env, jclass cls, jint id, jlong in, jint offset, jint type, jint size, jint fixed, jint indexOffset, jint bins, jdouble min, jdouble max)
{
//	double *hist = new double[bins];
	double *hist = (double*)malloc(sizeof(double)*bins);
	double binWidth = (max - min)/bins;
	int nentries, ier;
	char *buffer = (char *)(jint)in;

	for (int j=0; j<bins; j++) hist[j] = 0;

	hnoent(id,nentries);

	for (int i = 0; i < nentries; )
	{
       hgntf(id,++i,ier);

	   int elem = fixed;
	   if (indexOffset >= 0)
	   {
		   char *data = buffer + indexOffset;
           int count = *((int*) data);
		   elem *= count;
	   }
	   char *data = buffer + offset;
	   for (int j=0; j<elem; j++)
	   {
		   double d = 0;
		   if (type == 1 && size == 4)
		   {
              d = *((float*) data);
		   }
		   else if (type == 1 && size == 8)
		   {
              d = *((double*) data);
		   }
		   else if (type == 2 || type == 4)
		   {
              d = *((int*) data);
		   }
		   else if (type == 3)
		   {
              d = *((long*) data);
		   }
		   if (d>=min && d<max) hist[(int) (  (d - min)/binWidth  )]++;
		   data += size;
	   }
	}

	/*
	** return array of doubles
	*/
	jdoubleArray result = env->NewDoubleArray(bins);
	env->SetDoubleArrayRegion(result, 0, bins, hist);
//	delete hist;
	free(hist);
	return result;
}

/*
**********************************************************************
** LOWER LEVEL METHODS
***********************************************************************
*/

/*
*************************************************************
*visitDirectory
*************************************************************
*/
int visitDirectory(const char *dir, JNIEnv *env, jobject parent)
{
  Int_t id, lcid, lcont, nentries;

  int i999 = 999;
  int count = 0;

  hcdir(PASSCHAR(dir),PASSCHAR(" ")
#ifdef UNIXCALL
           ,strlen(dir),1
#endif
  );

  for (Int_t key=1;key<1000000;key++)
  {
     int z0 = 0;
     rzink(key,z0,"S",1);

     if (quest[0]) break;
	 if (quest[13] & 8) continue;

     int idx = quest[20];

     hrin(idx, i999, nextid-idx);
	 id = nextid; // we use our own private numbering scheme to avoid collisions
     hdcofl();

     lcid  = hcbook[10];
     lcont = lq[lcid-1];

     if (hcbits[3])
     {
        char *str = getTupleName(id);

        /*
        ** create a new child
        */
        if (str == NULL)
        {
           printf("Visiting Directory - bad id - couldn't get tuple name\n");
           return(-1);
        }
		hnoent(id,nentries);

		count++;

        /*
        ** fill up a new child object
        */

		if (iq[lcid - 2] == 2)
		{
			// Row wise n-tuple - create an appropriate object and add it to parent
			jclass fcls = env->FindClass("hep/io/hbook/RowwiseTuple");
			jmethodID fjm = env->GetMethodID(fcls, "<init>","(Lhep/io/hbook/CompositeHbookObject;Ljava/lang/String;III)V");
			jstring jname = env->NewStringUTF(str);
			env->NewObject(fcls, fjm, parent, jname, nentries, id, idx);
		}
		else
		{
			// Column wise n-tuple -
			jclass fcls = env->FindClass("hep/io/hbook/ColumnwiseTuple");
			jmethodID fjm = env->GetMethodID(fcls, "<init>","(Lhep/io/hbook/CompositeHbookObject;Ljava/lang/String;III)V");
			jstring jname = env->NewStringUTF(str);
			env->NewObject(fcls, fjm, parent, jname, nentries, id, idx);
		}
//		delete str;
		free(str);
		nextid ++;
    }
	else if (hcbits[1] || hcbits[2]) // Two D Histogram
	{
		int nx, ny, l, loc;
		float xmin, xmax, ymin, ymax;
                float xmean, xrms, ymean, yrms, xequiv, yequiv;
		char chtitl[128];

#ifdef UNIXCALL
		hgive(id,chtitl,nx,xmin,xmax,ny,ymin,ymax,l,loc,80);
#else
		hgive(id,chtitl,80,nx,xmin,xmax,ny,ymin,ymax,l,loc);
#endif

                chtitl[4*l] = 0;
		for (int i=l*4; --i>=0; )
		{
			if (chtitl[i] == ' ') chtitl[i]='\0';
			else break;
		}
                hnoent(id,nentries);
                xmean = hstati(id,1,PASSCHAR("PROX"),0
#ifdef UNIXCALL
         ,4
#endif
);
                xrms = hstati(id,2,PASSCHAR("PROX"),0
#ifdef UNIXCALL
         ,4
#endif
);
                xequiv = hstati(id,3,PASSCHAR("PROX"),0
#ifdef UNIXCALL
         ,4
#endif
);
                ymean = hstati(id,1,PASSCHAR("PROY"),0
#ifdef UNIXCALL
         ,4
#endif
);
                yrms = hstati(id,2,PASSCHAR("PROY"),0
#ifdef UNIXCALL
         ,4
#endif
);
                yequiv = hstati(id,3,PASSCHAR("PROY"),0
#ifdef UNIXCALL
         ,4
#endif
);
                jclass fcls = env->FindClass("hep/io/hbook/TwoDHistogram");
		jmethodID fjm = env->GetMethodID(fcls, "<init>","(Lhep/io/hbook/CompositeHbookObject;Ljava/lang/String;IIIFFIFFIFFFFFF)V");
		jstring jname = env->NewStringUTF(chtitl);
		env->NewObject(fcls, fjm, parent, jname, id, idx, nx, xmin, xmax, ny, ymin, ymax, nentries, xmean, ymean, xrms, yrms, xequiv, yequiv);
		nextid++;
		count++;
	}
	else if (hcbits[0]) // One D Histogram
	{
		int nx, ny, l, loc;
		float xmin, xmax, ymin, ymax;
		char chtitl[128];
                float mean, rms, equiv;
#ifdef UNIXCALL
		hgive(id,chtitl,nx,xmin,xmax,ny,ymin,ymax,l,loc,80);
#else
		hgive(id,chtitl,80,nx,xmin,xmax,ny,ymin,ymax,l,loc);
#endif
                chtitl[4*l] = 0;
		for (int i=l*4; --i>=0; )
		{
			if (chtitl[i] == ' ') chtitl[i]='\0';
			else break;
		}
                hnoent(id,nentries);
                mean = hstati(id,1,PASSCHAR("HIST"),0
#ifdef UNIXCALL
         ,4
#endif
);
                rms = hstati(id,2,PASSCHAR("HIST"),0
#ifdef UNIXCALL
         ,4
#endif
);
                equiv = hstati(id,3,PASSCHAR("HIST"),0
#ifdef UNIXCALL
         ,4
#endif
);
		jclass fcls = env->FindClass("hep/io/hbook/OneDHistogram");
		jmethodID fjm = env->GetMethodID(fcls, "<init>","(Lhep/io/hbook/CompositeHbookObject;Ljava/lang/String;IIIFFIFFF)V");
		jstring jname = env->NewStringUTF(chtitl);
		env->NewObject(fcls, fjm, parent, jname, id, idx, nx, xmin, xmax, nentries, mean, rms, equiv);
		nextid++;
		count++;
	}
    else
	{
		//printf("idx=%d bits=%d %d %d %d %d %d %d %d %d\n",idx,
		//	hcbits[0],hcbits[1],hcbits[2],hcbits[3],hcbits[4],
		//	hcbits[5],hcbits[6],hcbits[7],hcbits[8]);
		hdelet(id); // not an ntuple
	}
  }

/*
** visiting subdirectories of this directory
*/
  const Int_t KLS = 26;
  const Int_t KNSD = 23;
  Int_t lcdir = rzcl[2];
  Int_t ls = iq[lcdir+KLS];
  Int_t ndir = iq[lcdir+KNSD];
  Int_t nch=16;
  Int_t ihdir[4];
  Int_t ncw = 4;

  Int_t i;

  char chdir[17];

  /*
  ** produce the names of all subdirectories
  */

  for (Int_t k=0;k<ndir;k++)
  {
	count++;

    lcdir = rzcl[2];
    zitoh(iq[lcdir+ls+7*k],ihdir[0],ncw);
    for (i=0;i<17;i++) chdir[i] = 0;
#ifdef UNIXCALL
    uhtoc(ihdir[0],ncw,chdir,nch,16);
#else
    uhtoc(ihdir[0],ncw,chdir,16,nch);
#endif

    for (i=16;i>0;i--)
	{
        if (chdir[i] == 0) continue;
        if (chdir[i] != ' ') break;
        chdir[i] = 0;
    }

	/*
     ** create a new child object
     */
	jclass fcls = env->FindClass("hep/io/hbook/CompositeHbookObject");
	jmethodID fjm = env->GetMethodID(fcls, "<init>","(Lhep/io/hbook/CompositeHbookObject;Ljava/lang/String;I)V");
	jstring jname = env->NewStringUTF(chdir);
	env->NewObject(fcls, fjm, parent, jname, FOLDER);
  }
  return count;
}



/*
***************************************************************************
* visitCWTuple
***************************************************************************
*/
int visitCWTuple(Int_t id, JNIEnv *env, jobject ntuple)
{
  int nvar=0;
  int i,j;
  int nsub,itype,isize,ielem;
  float rmin[1], rmax[1];
  char chtitl[128];

  // The type of children we are going to create


  jclass cls = (env)->FindClass("hep/io/hbook/ColumnwiseTupleColumn");
  jmethodID jm = env->GetMethodID(cls, "<init>",
	  "(Lhep/io/hbook/ColumnwiseTuple;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIII)V");

  hgiven(id,PASSCHAR(chtitl),nvar,PASSCHAR(""),rmin[0],rmax[0]
#ifdef UNIXCALL
         ,80,0
#endif
        );

  char fullname[64];
  char name[32];
  char block[32];
  char oldblock[32];
  strcpy(oldblock,"OLDBLOCK");
  Int_t oldischar = -1;
  Int_t offset = 0;

  /*
  ** fill Column list
  */

  for(i=1; i<=nvar;i++) {
     memset(name,' ',sizeof(name));
     name[sizeof(name)-1] = 0;
     memset(block,' ',sizeof(block));
     block[sizeof(block)-1] = 0;
     memset(fullname,' ',sizeof(fullname));
     fullname[sizeof(fullname)-1]=0;

     /*
     ** returns the tag, block, type, size and array length of the variable with index IVAR and
     ** in N-tuple Id1.  N-tuple must already be in memory.  This routine is a modification of the
     ** HBOOK routine HNTVAR
     */

     hntvar2(id,i,PASSCHAR(name),PASSCHAR(fullname),PASSCHAR(block),nsub,itype,isize,ielem
#ifdef UNIXCALL
         ,32,64,32
#endif
        );

     for (j=30;j>0;j--) {
        if(golower) name[j] = tolower(name[j]);
        if (name[j] == ' ') name[j] = 0;
     }
     for (j=62;j>0;j--) {
        if(golower && fullname[j-1] != '[') fullname[j] = tolower(fullname[j]);
        if (fullname[j] == ' ') fullname[j] = 0;
     }
     for (j=30;j>0;j--)
     {
        if (block[j] == ' ') block[j] = 0;
        else break;
     }

     // A new block starts if block name changes or ischar changes

     Int_t ischar = (itype == 5);

	 if (ischar != oldischar || strcmp(oldblock,block) != 0)
	 {
        strcpy(oldblock,block);
        oldischar = ischar;
		offset = 0;
     }

  	/*
    ** Create a HbookObject object for this column
	*/

	jstring jname = env->NewStringUTF(name);
	jstring jfull = env->NewStringUTF(fullname);
	jstring jblok = env->NewStringUTF(block);

	env->NewObject(cls, jm, ntuple, jname, jfull, jblok,
		(jint) i, (jint) nsub, (jint) itype, (jint) isize, (jint) ielem);

	offset += ielem * isize;
  }
  return nvar;
}

/*
 ***************************************************************************
 * visitRWTuple
 * Create an HbookObject describing each column in the row wise N-tuple.
 ***************************************************************************
 */
int visitRWTuple(Int_t id, JNIEnv *env, jobject ntuple)
{
  const int Nchar=9;
  char chtitl[128];

  int nvar;
  int i;
  char *chtag_out;
  float rmin[1000], rmax[1000];

  // The type of children we are going to create

  jclass cls = (env)->FindClass("hep/io/hbook/RowwiseTupleColumn");
  jmethodID jm = env->GetMethodID(cls, "<init>","(Lhep/io/hbook/CompositeHbookObject;Ljava/lang/String;IFF)V");

  // We need to call hgiven twice, since the first time we dont know
  // what nvar is, so we dont know how much memory to allocate for the
  // output arrays.

  nvar=0;
  hgiven(id,PASSCHAR(chtitl),nvar,PASSCHAR(""),rmin[0],rmax[0]
#ifdef UNIXCALL
         ,80,0
#endif
        );

//  chtag_out = new char[nvar*Nchar+1];
  chtag_out = (char*)malloc(sizeof(char)*(nvar*Nchar+1));
  chtag_out[nvar*Nchar]=0;

#ifdef UNIXCALL
  hgiven(id,chtitl,nvar,chtag_out,rmin[0],rmax[0],80,Nchar);
#else
  hgiven(id,chtitl,80,nvar,chtag_out,Nchar,rmin[0],rmax[0]);
#endif

  hgnpar(id,"?",1); // todo: what is this??
  char *name = chtag_out;

  // Loop over the columns

  for (i=0; i<nvar;i++)
  {
    name[Nchar-1] = 0;
    for (Int_t j=Nchar-2;j>0;j--)
	{
		if (golower) name[j] = tolower(name[j]);
		if (name[j] == ' ') name[j] = 0;
	}

  	/*
    ** Create a HbookObject object for this column
	*/

	jstring jname = env->NewStringUTF(name);
	env->NewObject(cls, jm, ntuple, jname, (jint) i, (jfloat) rmin[i], (jfloat) rmax[i]);
	name += Nchar;
  }
//  delete chtag_out;
  free(chtag_out);
  return nvar;
}

/*
 ***************************************************************
 * 1D Histogram support
 */


JNIEXPORT jdoubleArray JNICALL Java_hep_io_hbook_Hbook_Hist1DData
  (JNIEnv *env , jclass cls, jint id, jint bins)
{
//	double *hist = new double[bins+2];
	double *hist = (double*)malloc(sizeof(double)*(bins+2));
	for (int i=0; i<bins+2; i++) hist[i] = hi(id,i);

	jdoubleArray result = env->NewDoubleArray(bins+2);
	env->SetDoubleArrayRegion(result, 0, bins+2, hist);
//	delete hist;
	free(hist);

	return result;
}

JNIEXPORT jdoubleArray JNICALL Java_hep_io_hbook_Hbook_Hist1DErrors
  (JNIEnv *env , jclass cls, jint id, jint bins)
{
//	double *hist = new double[bins+2];
	double *hist = (double*)malloc(sizeof(double)*(bins+2));
	for (int i=0; i<bins+2; i++) hist[i] = hie(id,i);

	jdoubleArray result = env->NewDoubleArray(bins+2);
	env->SetDoubleArrayRegion(result, 0, bins+2, hist);
//	delete hist;
	free(hist);

	return result;
}

/*
 ***************************************************************
 * 2D Histogram support
 */


JNIEXPORT jobjectArray JNICALL Java_hep_io_hbook_Hbook_Hist2DData
  (JNIEnv *env , jclass cls, jint id, jint xbins, jint ybins)
{
	jobjectArray result = NULL;
//	double *hist = new double[ybins+2];
	double *hist = (double*)malloc(sizeof(double)*(ybins+2));

	for (int j=0; j<xbins+2; j++)
	{
		for (int i=0; i<ybins+2; i++) hist[i] = hij(id,j,i);

		jdoubleArray x = env->NewDoubleArray(ybins+2);
		env->SetDoubleArrayRegion(x, 0, ybins+2, hist);
		if (result == NULL) result = (jobjectArray)env->NewObjectArray(xbins+2,env->GetObjectClass(x),NULL);
		env->SetObjectArrayElement(result,j,x);
	}
//	delete hist;
	free(hist);
	return result;
}

JNIEXPORT jobjectArray JNICALL Java_hep_io_hbook_Hbook_Hist2DErrors
  (JNIEnv *env , jclass cls, jint id, jint xbins, jint ybins)
{
	jobjectArray result = NULL;
//	double *hist = new double[ybins+2];
	double *hist = (double*)malloc(sizeof(double)*(ybins+2));

	for (int j=0; j<xbins+2; j++)
	{
		for (int i=0; i<ybins+2; i++) hist[i] = hije(id,j,i);

		jdoubleArray x = env->NewDoubleArray(ybins+2);
		env->SetDoubleArrayRegion(x, 0, ybins+2, hist);
		if (result == NULL) result = (jobjectArray)env->NewObjectArray(xbins+2,env->GetObjectClass(x),NULL);
		env->SetObjectArrayElement(result,j,x);
	}
//	delete hist;
	free(hist);
	return result;
}


/*
 ***************************************************************************
 * getTupleName
 ***************************************************************************
 */
static char * getTupleName(Int_t id)
{

  float rmin[1000], rmax[1000];
//  char* chtitl = new char[128]; // must be freed by caller
  char* chtitl = (char*)malloc(sizeof(char)*128); // must be freed by caller
  int nvar = 0;

#ifdef UNIXCALL
  hgiven(id,chtitl,nvar,"",rmin[0],rmax[0],80,0);
#else
  hgiven(id,chtitl,80,nvar,"",0,rmin[0],rmax[0]);
#endif
  for (int i=80; --i>=0; )
  {
	  if (chtitl[i] == ' ') chtitl[i]='\0';
	  else break;
  }
  return chtitl;
}

