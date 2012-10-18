>> android create project -k org.wl.test -a MyTest -t 8 -p .
-k package
-a activity name
-t target
-p path

>> android update project --name MyTest -t 10 -p .
>> android list targets
>> ant debug
>> adb devices
>> emulator -avd

wang:mytest wangliang$ ant debug
Buildfile: /Users/wangliang/Documents/open/android_source_apps/mytest/build.xml

-set-mode-check:

-set-debug-files:

-check-env:
 [checkenv] Android SDK Tools Revision 20.0.3
 [checkenv] Installed at /Users/wangliang/Downloads/android-sdk-macosx

-setup:
     [echo] Project Name: MyTest
  [gettype] Project Type: Application

-set-debug-mode:

-debug-obfuscation-check:

-build-setup:
     [echo] Resolving Build Target for MyTest...
[gettarget] Project Target:   Android 3.0
[gettarget] API level:        11
[gettarget] WARNING: No minSdkVersion value set. Application will install on all Android versions.
     [echo] ----------
     [echo] Creating output directories if needed...
    [mkdir] Created dir: /Users/wangliang/Documents/open/android_source_apps/mytest/bin/res
    [mkdir] Created dir: /Users/wangliang/Documents/open/android_source_apps/mytest/gen
    [mkdir] Created dir: /Users/wangliang/Documents/open/android_source_apps/mytest/bin/classes
     [echo] ----------
     [echo] Resolving Dependencies for MyTest...
[dependency] Library dependencies:
[dependency] No Libraries
[dependency] 
[dependency] ------------------
[dependency] API<=15: Adding annotations.jar to the classpath.
     [echo] ----------
     [echo] Building Libraries with 'debug'...
   [subant] No sub-builds to iterate on

-pre-build:

-code-gen:
[mergemanifest] Merging AndroidManifest files into one.
[mergemanifest] Manifest merger disabled. Using project manifest only.
     [echo] Handling aidl files...
     [aidl] No AIDL files to compile.
     [echo] ----------
     [echo] Handling RenderScript files...
[renderscript] No RenderScript files to compile.
     [echo] ----------
     [echo] Handling Resources...
     [aapt] Generating resource IDs...
     [echo] ----------
     [echo] Handling BuildConfig class...
[buildconfig] Generating BuildConfig class.

-pre-compile:

-compile:
    [javac] Compiling 3 source files to /Users/wangliang/Documents/open/android_source_apps/mytest/bin/classes

-post-compile:

-obfuscate:

-dex:
      [dex] Converting compiled files and external libraries into /Users/wangliang/Documents/open/android_source_apps/mytest/bin/classes.dex...

-crunch:
   [crunch] Crunching PNG Files in source dir: /Users/wangliang/Documents/open/android_source_apps/mytest/res
   [crunch] To destination dir: /Users/wan
   [crunch] gliang/Documents/open/android_source_apps/mytest/bin/res
   [crunch] Crunched 0 PNG files to update cache

-package-resources:
     [aapt] Creating full resource package...

-package:
[apkbuilder] Current build type is different than previous build: forced apkbuilder run.
[apkbuilder] Creating MyTest-debug-unaligned.apk and signing it with a debug key...

-post-package:

-do-debug:
 [zipalign] Running zip align on final apk...
     [echo] Debug Package: /Users/wangliang/Documents/open/android_source_apps/mytest/bin/MyTest-debug.apk
[propertyfile] Creating new property file: /Users/wangliang/Documents/open/android_source_apps/mytest/bin/build.prop
[propertyfile] Updating property file: /Users/wangliang/Documents/open/android_source_apps/mytest/bin/build.prop
[propertyfile] Updating property file: /Users/wangliang/Documents/open/android_source_apps/mytest/bin/build.prop
[propertyfile] Updating property file: /Users/wangliang/Documents/open/android_source_apps/mytest/bin/build.prop

-post-build:

debug:

BUILD SUCCESSFUL
Total time: 3 seconds

------------------------------------------------------------------------------------------------------------
[android create project]
       Usage:
       android [global options] create project [action options]
       Global options:
  -h --help       : Help on a specific command.
  -v --verbose    : Verbose mode, shows errors, warnings and all messages.
     --clear-cache: Clear the SDK Manager repository manifest cache.
  -s --silent     : Silent mode, shows errors only.

                         Action "create project":
  Creates a new Android project.
Options:
  -n --name    : Project name.
  -t --target  : Target ID of the new project. [required]
  -p --path    : The new project's directory. [required]
  -k --package : Android package name for the application. [required]
  -a --activity: Name of the default Activity that is created. [required]
  
[update project] Usage:
       android [global options] update project [action options]
       Global options:
  -h --help       : Help on a specific command.
  -v --verbose    : Verbose mode, shows errors, warnings and all messages.
     --clear-cache: Clear the SDK Manager repository manifest cache.
  -s --silent     : Silent mode, shows errors only.

                         Action "update project":
  Updates an Android project (must already have an AndroidManifest.xml).
Options:
  -l --library    : Directory of an Android library to add, relative to this
                    project's directory.
  -p --path       : The project's directory. [required]
  -n --name       : Project name.
  -t --target     : Target ID to set for the project.
  -s --subprojects: Also updates any projects in sub-folders, such as test
                    projects.
[Android Ant Build]
help:
     [echo] Android Ant Build. Available targets:
     [echo]    help:      Displays this help.
     [echo]    clean:     Removes output files created by other targets.
     [echo]               The 'all' target can be used to clean dependencies
     [echo]               (tested projects and libraries)at the same time
     [echo]               using: 'ant all clean'
     [echo]    debug:     Builds the application and signs it with a debug key.
     [echo]               The 'nodeps' target can be used to only build the
     [echo]               current project and ignore the libraries using:
     [echo]               'ant nodeps debug'
     [echo]    release:   Builds the application. The generated apk file must be
     [echo]               signed before it is published.
     [echo]               The 'nodeps' target can be used to only build the
     [echo]               current project and ignore the libraries using:
     [echo]               'ant nodeps release'
     [echo]    instrument:Builds an instrumented package and signs it with a
     [echo]               debug key.
     [echo]    test:      Runs the tests. Project must be a test project and
     [echo]               must have been built. Typical usage would be:
     [echo]                   ant [emma] debug install test
     [echo]    emma:      Transiently enables code coverage for subsequent
     [echo]               targets.
     [echo]    install:   Installs the newly build package. Must either be used
     [echo]               in conjunction with a build target (debug/release/
     [echo]               instrument) or with the proper suffix indicating
     [echo]               which package to install (see below).
     [echo]               If the application was previously installed, the
     [echo]               application is reinstalled if the signature matches.
     [echo]    installd:  Installs (only) the debug package.
     [echo]    installr:  Installs (only) the release package.
     [echo]    installi:  Installs (only) the instrumented package.
     [echo]    installt:  Installs (only) the test and tested packages (unless
     [echo]               nodeps is used as well.
     [echo]    uninstall: Uninstalls the application from a running emulator or
     [echo]               device. Also uninstall tested package if applicable
     [echo]               unless 'nodeps' is used as well.
     
     
     
Android中Android.mk文件的使用

1 概述
大家都知道在Linux下编辑经常要写一个Makefile文件， 可以把这个Makefile文件理解成一个编译配置文件，它保存着如何编译的配置信息，即指导编译器如何来编译程序，并决定编译的结果是什么。而在 Android下的Android.mk文件也是类型的功能，顾名思义，从名字上就可以猜测得到，Android.mk文件是针对Android的 Makefile文件.具体来说：该文件是GNU Makefile的一小部分，会被编译系统解析一次或多次。你可以在每一个Android.mk file中定义一个或多个模块，你也可以在几个模块中使用同一个源代码文件。编译系统为你处理许多细节问题。
2 Android.mk的整体结构
例如一个Android.mk的内容如下:
--------------------------------------------------------------------------------

LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
 
LOCAL_SRC_FILES:= \
    android_media_MediaPlayer.cpp \
    android_media_MediaRecorder.cpp \
    android_media_MediaScanner.cpp \
    android_media_MediaMetadataRetriever.cpp \
    android_media_ResampleInputStream.cpp \
    android_media_MediaProfiles.cpp \
    android_media_AmrInputStream.cpp \
    android_media_Utils.cpp \
    android_mtp_MtpDatabase.cpp \
    android_mtp_MtpDevice.cpp \
    android_mtp_MtpServer.cpp \
 
LOCAL_SHARED_LIBRARIES := \
    libandroid_runtime \
    libnativehelper \
    libutils \
    libbinder \
    libmedia \
    libskia \
    libui \
    libcutils \
    libgui \
    libstagefright \
    libcamera_client \
    libsqlite \
    libmtp \
    libusbhost \
    libexif
 
LOCAL_C_INCLUDES += \
    external/jhead \
    external/tremor/Tremor \
    frameworks/base/core/jni \
    frameworks/base/media/libmedia \
    frameworks/base/media/libstagefright/codecs/amrnb/enc/src \
    frameworks/base/media/libstagefright/codecs/amrnb/common \
    frameworks/base/media/libstagefright/codecs/amrnb/common/include \
    frameworks/base/media/mtp \
    $(PV_INCLUDES) \
    $(JNI_H_INCLUDE) \
    $(call include-path-for, corecg graphics)
 
LOCAL_CFLAGS +=
 
LOCAL_LDLIBS := -lpthread
 
LOCAL_MODULE:= libmedia_jni
 
include $(BUILD_SHARED_LIBRARY)
 
# build libsoundpool.so
# build libaudioeffect_jni.so
include $(call all-makefiles-under,$(LOCAL_PATH))
--------------------------------------------------------------------------------

首先必须定义好LOCAL_PATH变量。然后清除所有LOCAL_XX变量的值，当然LOCAL_PATH的值除外，然后定义源文件，头文件，接着是编译选项参数，紧接着是编译生成的文件名，最后是生成的文件类型.
3 Android.mk内的变量定义
3.1  LOCAL_PATH
一个Android.mk file首先必须定义好LOCAL_PATH变量。它用于在开发树中查找源文件。例如:
1
LOCAL_PATH:= $(call my-dir)
宏函数’my-dir’, 由编译系统提供，用于返回当前路径（即包含Android.mk file文件的目录）
3.2 include $( CLEAR_VARS)
宏CLEAR_VARS 由编译系统提供，指定让GNU MAKEFILE为你清除许多LOCAL_XXX变量（例如 LOCAL_MODULE, LOCAL_SRC_FILES,LOCAL_STATIC_LIBRARIES, 等等...),除LOCAL_PATH 。这是必要的，因为所有的编译控制文件都在同一个GNU MAKE执行环境中，所有的变量都是全局的。
3.3 LOCAL_SRC_FILES
本次需要编译的源文件
3.4 LOCAL_SHARED_LIBRARIES
本次编译需要链接的动态链接库文件，即.so文件
3.5 LOCAL_STATIC_LIBRARIES
静态链接库.
3.6 LOCAL_C_INCLUDES
本次编译需要包含的头文件,一个相对于当前目录可选的路径名单，当编译所有的源文件（C,C++和汇编）时，它将被添加进include搜索路径。例如：
    LOCAL_C_INCLUDES := sources/foo
或者甚至：
    LOCAL_C_INCLUDES := $(LOCAL_PATH)/../foo
3.6 LOCAL_LDLIBS
本次编译的链接选项，相当于gcc -l后的参数
3.7  LOCAL_CFLAGS
同样是编译选项，相当于gcc -O后面的参数
3.8 LOCAL_MODULE
生成的模块名，这个变量必须定义，表示make后将要生成的文件的名字
3.9 LOCAL_PACKAGE_NAME
apk文件名
4 include
include可Android多以这样的形式出现,如:include $( CLEAR_VARS),include $(BUILD_SHARED_LIBRARY).其实这个include可以理解成"执行"的意思，那么执行什么呢?当然是看后边的宏了.
宏CLEAR_VARS已经在3.2节中介绍过了，表示清除一些变量.
宏BUILD_SHARED_LIBRARY表示生成共享库，即生成.so文件
因此include $(BUILD_SHARED_LIBRARY)就是指定在/system/lib/目录下生成一个lib$(LOCAL_MOUDULE).so文件,同样类型的宏如下:
CLEAR_VARS                                             清除LOCAL_xxx变量
BUILD_SHARED_LIBRARY                            在/system/lib/目录下生成lib$(LOCAL_MOUDULE).so文件
BUILD_STATIC_LIBRARY                             生成lib$(LOCAL_MOUDULE).a文件
BUILD_EXECUTABLE                                   在/system/bin/目录下生成可执行文件
BUILD_PACKAGE                                        编译成一个apk文件
原文链接：http://blog.csdn.net/flydream0/article/details/7164501