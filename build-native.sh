#!/bin/bash

THIS_DIR=$(pwd)
echo $THIS_DIR

cd native
~/bin/genie gmake
cd build
arch -x86_64 make

cd $THIS_DIR
