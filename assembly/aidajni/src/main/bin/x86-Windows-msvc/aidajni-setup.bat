@echo off
set AIDAJNI_VERSION=3.2.6
set AIDAJNI_NAME=freehep-aidajni-%AIDAJNI_VERSION%
set AIDAJNI_AOL=x86-Windows-msvc
set AIDAJNI_HOME=%~dp0..\..
set PATH=%~dp0;%AIDAJNI_HOME%\lib\%AIDAJNI_AOL%;%JDK_HOME%\jre\bin\client;%PATH%
set CLASSPATH=%AIDAJNI_HOME%\lib\%AIDAJNI_NAME%.jar;%CLASSPATH%
set AIDAJNI_INCLUDES=-I%AIDAJNI_HOME%\include
set AIDAJNI_LIBS=/libpath:%AIDAJNI_HOME%\lib\%AIDAJNI_AOL% %AIDAJNI_NAME%.lib /libpath:%JAVA_HOME%\lib jvm.lib
