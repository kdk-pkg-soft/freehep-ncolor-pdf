#
# Setup for Windows with cygwin and msvc
#
export AIDAJNI_VERSION="3.2.6"
export AIDAJNI_NAME=freehep-aidajni-${AIDAJNI_VERSION}
export AIDAJNI_AOL=x86-Windows-msvc

# Check AIDAJNI_HOME is set sensibly
if ! test -e "${AIDAJNI_HOME}/lib/${AIDAJNI_AOL}/${AIDAJNI_NAME}.LIB"
then
   echo "The ${AIDAJNI_NAME}.LIB file seem to be missing, make sure AIDAJNI_HOME has been set properly"
   return
fi

if ! test -e "${JDK_HOME}/jre/bin/client/jvm.dll" 
then
   echo "The jvm.dll file seem to be missing, make sure JDK_HOME has been set properly"
   return
fi

chmod +x "${AIDAJNI_HOME}/bin/${AIDAJNI_AOL}/aida-config.exe"
chmod +x "${AIDAJNI_HOME}/examples/CC/build"
chmod +x "${AIDAJNI_HOME}/examples/g++/build"
chmod +x "${AIDAJNI_HOME}/examples/icc/build"

UPATH=`cygpath -u "${AIDAJNI_HOME}"`
DPATH=`cygpath -d "${AIDAJNI_HOME}"`
UJPATH=`cygpath -u "${JDK_HOME}"`
DJPATH=`cygpath -d "${JDK_HOME}"`

export PATH="${UPATH}/bin/${AIDAJNI_AOL}:${UPATH}/lib/${AIDAJNI_AOL}:${PATH}:${UJPATH}/jre/bin/client"
export CLASSPATH="${DPATH}/lib/${AIDAJNI_NAME}.jar;${CLASSPATH}"

export AIDAJNI_INCLUDES=-I${DPATH}\\include
export AIDAJNI_LIBS="/libpath:${DPATH}\\lib\\${AIDAJNI_AOL} ${AIDAJNI_NAME}.lib /libpath:${DJPATH}\\lib jvm.lib"
