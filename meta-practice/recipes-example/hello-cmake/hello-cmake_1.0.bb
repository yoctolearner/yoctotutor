DESCRIPTION = "recipe for hello using cmake"
LICENSE = "CLOSED"

SRC_URI = "\
           file://hello-cmake.c \
           file://CMakeLists.txt \
"

S = "${WORKDIR}"

inherit cmake
