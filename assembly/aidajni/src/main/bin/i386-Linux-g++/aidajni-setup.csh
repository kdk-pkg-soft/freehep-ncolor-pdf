#
# Setup for i386 Linux with csh
#
setenv AIDAJNI_VERSION "3.2.6"
setenv AIDAJNI_NAME "freehep-aidajni-${AIDAJNI_VERSION}"
setenv AIDAJNI_AOL "i386-Linux-g++"

# Check AIDAJNI_HOME is set sensibly
if (! $?AIDAJNI_HOME) then
   echo "AIDAJNI_HOME has not been set"
   exit
endif

if (! -e "${AIDAJNI_HOME}/lib/${AIDAJNI_AOL}/lib${AIDAJNI_NAME}.a") then
   echo "The lib${AIDAJNI_NAME}.a file seem to be missing, make sure AIDAJNI_HOME has been set properly"
   exit
endif

if (! $?JDK_HOME) then
   echo "JDK_HOME has not been set, make sure it is set to the Java Runtime (JRE/JDK) directory"
   exit
endif

if (! -e "${JDK_HOME}/jre/lib/i386/client/libjvm.so") then
   echo "The libjvm.so file seem to be missing, make sure JDK_HOME has been set properly"
   exit
endif

chmod +x "${AIDAJNI_HOME}/bin/${AIDAJNI_AOL}/aida-config"
chmod +x "${AIDAJNI_HOME}/examples/CC/build"
chmod +x "${AIDAJNI_HOME}/examples/g++/build"
chmod +x "${AIDAJNI_HOME}/examples/icc/build"
ranlib "${AIDAJNI_HOME}/lib/${AIDAJNI_AOL}/lib${AIDAJNI_NAME}.a" 

if (! $?PATH) then
   setenv PATH ""
endif
setenv PATH "${AIDAJNI_HOME}/bin/${AIDAJNI_AOL}:${PATH}"

if (! $?CLASSPATH) then
   setenv CLASSPATH ""
endif
setenv CLASSPATH "${AIDAJNI_HOME}/lib/${AIDAJNI_NAME}.jar:${CLASSPATH}"

if (! $?LD_LIBRARY_PATH) then
   setenv LD_LIBRARY_PATH ""
endif
setenv LD_LIBRARY_PATH "${AIDAJNI_HOME}/lib/${AIDAJNI_NAME}:${LD_LIBRARY_PATH}"
setenv LD_LIBRARY_PATH "${JDK_HOME}/jre/lib/i386/client:${LD_LIBRARY_PATH}"
setenv LD_LIBRARY_PATH "${JDK_HOME}/jre/lib/i386:${LD_LIBRARY_PATH}"

setenv AIDAJNI_INCLUDES -I${AIDAJNI_HOME}/include
setenv AIDAJNI_LIBS "-L${AIDAJNI_HOME}/lib/${AIDAJNI_AOL} -l${AIDAJNI_NAME} -L${JDK_HOME}/jre/lib/i386/client -ljvm"
