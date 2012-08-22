#
# Setup for Windows with cygwin and msvc
#
setenv AIDAJNI_VERSION "3.2.6"
setenv AIDAJNI_NAME "freehep-aidajni-${AIDAJNI_VERSION}"
setenv AIDAJNI_AOL "x86-Windows-msvc"

# Check AIDAJNI_HOME is set sensibly
if (! $?AIDAJNI_HOME) then
   echo "AIDAJNI_HOME has not been set"
   exit
endif

if (! -e "${AIDAJNI_HOME}/lib/${AIDAJNI_AOL}/${AIDAJNI_NAME}.LIB") then
   echo "The ${AIDAJNI_NAME}.LIB file seem to be missing, make sure AIDAJNI_HOME has been set properly"
   exit
endif

if (! $?JDK_HOME) then
   echo "JDK_HOME has not been set, make sure it is set to the Java Runtime (JRE/JDK) directory"
   exit
endif

if (! -e "${JDK_HOME}/jre/bin/client/jvm.dll") then
   echo "The jvm.dll file seem to be missing, make sure JDK_HOME has been set properly"
   exit
endif

chmod +x "${AIDAJNI_HOME}/bin/${AIDAJNI_AOL}/aida-config.exe"
chmod +x "${AIDAJNI_HOME}/examples/CC/build"
chmod +x "${AIDAJNI_HOME}/examples/g++/build"
chmod +x "${AIDAJNI_HOME}/examples/icc/build"

set UPATH=`cygpath -u "${AIDAJNI_HOME}"`
set DPATH=`cygpath -d "${AIDAJNI_HOME}"`
set UJPATH=`cygpath -u "${JDK_HOME}"`
set DJPATH=`cygpath -d "${JDK_HOME}"`

if (! $?PATH) then
   setenv PATH ""
endif
setenv PATH "${UPATH}/bin/${AIDAJNI_AOL}:${UPATH}/lib/${AIDAJNI_AOL}:${PATH}:${UJPATH}/jre/bin/client"

if (! $?CLASSPATH) then
   setenv CLASSPATH ""
endif
setenv CLASSPATH "${DPATH}/lib/${AIDAJNI_NAME}.jar;${CLASSPATH}"

setenv AIDAJNI_INCLUDES "-I${DPATH}/include"
setenv AIDAJNI_LIBS "/libpath:${DPATH}\lib\${AIDAJNI_AOL} ${AIDAJNI_NAME}.lib /libpath:${DJPATH}\lib jvm.lib"
