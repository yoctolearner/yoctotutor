DESCRIPTION = "recipe for libmath using cmake"
LICENSE = "CLOSED"

SRC_URI = "\
       file://hellomath.c \
       file://CMakeLists.txt \
"

S = "${WORKDIR}"

inherit cmake
