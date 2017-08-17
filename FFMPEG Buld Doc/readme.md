Buiding .so file for FFMPEG library 

Download latest FFMPEG C library from official repository 

Downlaod the latest NDK from google repo 

Put the downloaded FFMPEG to you NDK source file 
 and add the all files which is in FFMEPG_SOURCE 
Open build_yourArch.sh and make sure SYSROOT,TOOLCHAIN,PLATFORM is correct
Set execute permision to build_all and run it will generate all .so file inside android folder 


For generate libx264 lib  

Need to generate .h file for libx264 
refer this link to generete x264 https://yesimroy.gitbooks.io/android-note/content/compile_x264_for_android.html


build_yourArch.sh add followings 
" --enable-gpl --enable-libx264"

set the --extra-cflags in respective arch buil.sh file with -I../x264/android/arm/include 

Same with --extra-ldflags="-L../x264/android/arm/lib"


example 
./configure  --prefix=$PREFIX $COMMON $CONFIGURATION  --enable-gpl --enable-libx264 --cross-prefix=$TOOLCHAIN/bin/arm-linux-androideabi- --target-os=linux --arch=arm --enable-cross-compile --sysroot=$SYSROOT --extra-cflags="-Os -marm -I../x264/android/arm/include $ADDI_CFLAGS" --extra-ldflags="-L../x264/android/arm/lib"

