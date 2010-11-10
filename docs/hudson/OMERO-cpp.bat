
call "C:\Program Files\Microsoft Visual Studio 9.0\VC\vcvarsall.bat" x86
if errorlevel 1 exit /b 1

set OMERO_BUILD=r%SVN_REVISION%-d%BUILD_NUMBER%
set OMERO_CONFIG=%JOB_NAME%
set ICE_HOME=c:\Ice-3.3.1-VC90
set CXXFLAGS=/DBOOST_TEST_SOURCE
set CPPPATH=c:\progra~1\boost\boost_1_39\
set LIBPATH=%CPPPATH%\lib
REM set PATH=%ICE_HOME%\bin\x64;%ICE_HOME%\bin;%PATH%
set VERBOSE=1
REM set J=8 Possibly causes strange build issues in blitz

set

python build.py clean
if errorlevel 1 exit /b 1

python build.py build-all
if errorlevel 1 exit /b 1

set RELEASE=Os
python build.py build-cpp
if errorlevel 1 exit /b 1

python build.py -f components\tools\OmeroCpp\build.xml test
if errorlevel 1 exit /b 1

python build.py -f components\tools\OmeroCpp\build.xml integration
if errorlevel 1 exit /b 1

REM
REM Unpack to target
REM
python C:\hudson\unzip.py -z src\target\OMERO.cpp*dbg.zip -o target\
FOR /F %I IN ('DIR target\OMERO.cpp*dbg /B') DO SET OMERO_CPP=%I
SET OMERO_CPP=%cd%\%OMERO_CPP%
if not exist %OMERO_CPP%\etc mkdir %OMERO_CPP%\etc
copy %OMERO_BRANCH%.config %OMERO_CPP%\etc\

cd examples
python ..\target\scons\scons.py builddir=%OMERO_CPP% run_cpp=1
if errorlevel 1 exit /b 1

exit /b 0
:ERROR
  echo Failed %ERRORLEVEL%
  exit /b %ERRORLEVEL%