DESCRIPTION = "recipe for dynamic library creation using cmake in CPP"
LICENSE = "CLOSED"

SRC_URI = "\
    file://math.cpp \
    file://math.hpp \
    file://CMakeLists.txt \
"

S = "${WORKDIR}"

inherit cmake
