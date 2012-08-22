REM
REM Build file for Visual C++ (Windows)
REM
cl /c /EHsc /MD %AIDAJNI_INCLUDES% ..\AidaTest.cpp
link /out:AidaTest.exe AidaTest.obj %AIDAJNI_LIBS%

