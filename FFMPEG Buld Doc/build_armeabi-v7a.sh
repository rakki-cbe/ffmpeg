
SYSROOT=$NDK/platforms/android-21/arch-arm/
TOOLCHAIN=$NDK/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64
rm -f $(pwd)/compat/strtod.o
function build_one
{
./configure  --prefix=$PREFIX $COMMON $CONFIGURATION  --enable-gpl --enable-libx264 --cross-prefix=$TOOLCHAIN/bin/arm-linux-androideabi- --target-os=linux --arch=arm --enable-cross-compile --sysroot=$SYSROOT --extra-cflags="-Os -marm -I../x264/android/arm/include $ADDI_CFLAGS" --extra-ldflags="-L../x264/android/arm/lib"

make clean
make -j2
make install
}

export CPU=armeabi-v7a
PREFIX=$(pwd)/android/$CPU
build_one
cp Android.mk $PREFIX/Android.mk
cd $PROJECT_JNI
export ABI=$CPU
export PLATFORM="android-21"
ndk-build
cp -r "$PROJECT_LIBS/$CPU" "$PROJECT_LIBS/../out" 
cd $DIR
