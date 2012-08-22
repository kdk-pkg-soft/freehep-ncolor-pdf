#
# Setup for MacOS ppc using sh
#
export AIDAJNI_VERSION="3.2.6"
export AIDAJNI_NAME=freehep-aidajni-${AIDAJNI_VERSION}
export AIDAJNI_AOL=ppc-MacOSX-g++

# Check AIDAJNI_HOME is set sensibly
if ! test -e "${AIDAJNI_HOME}/lib/${AIDAJNI_AOL}/lib${AIDAJNI_NAME}.a"
then
   echo "The lib${AIDAJNI_NAME}.a file seem to be missing, make sure AIDAJNI_HOME has been set properly"
   return
fi

chmod +x "${AIDAJNI_HOME}/bin/${AIDAJNI_AOL}/aida-config"
chmod +x "${AIDAJNI_HOME}/examples/CC/build"
chmod +x "${AIDAJNI_HOME}/examples/g++/build"
chmod +x "${AIDAJNI_HOME}/examples/icc/build"
ranlib "${AIDAJNI_HOME}/lib/${AIDAJNI_AOL}/lib${AIDAJNI_NAME}.a" 

export PATH="${AIDAJNI_HOME}/bin/${AIDAJNI_AOL}:${PATH}"

export CLASSPATH="${AIDAJNI_HOME}/lib/${AIDAJNI_NAME}.jar:${CLASSPATH}"

export DYLD_LIBRARY_PATH="${AIDAJNI_HOME}/lib/${AIDAJNI_AOL}:${DYLD_LIBRARY_PATH}"

export AIDAJNI_INCLUDES=-I${AIDAJNI_HOME}/include
export AIDAJNI_LIBS="-L${AIDAJNI_HOME}/lib/${AIDAJNI_AOL} -l${AIDAJNI_NAME} -framework JavaVM"
