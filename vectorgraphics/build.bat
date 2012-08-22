@echo off
CLS
setlocal

call mvn install
echo finish building
copy /y "c:\Documents and Settings\RLin\.m2\repository\org\freehep\freehep-graphicsio-pdf\2.1.2-SNAPSHOT\freehep-graphicsio-pdf-2.1.2-SNAPSHOT.jar" c:\PerforceSource\ValueInPrint\main\lib
copy /y "C:\Documents and Settings\RLin\.m2\repository\org\freehep\freehep-graphicsio\2.1.2-SNAPSHOT\freehep-graphicsio-2.1.2-SNAPSHOT.jar" c:\PerforceSource\ValueInPrint\main\lib
echo end building
exit /b %ERRORLEVEL%