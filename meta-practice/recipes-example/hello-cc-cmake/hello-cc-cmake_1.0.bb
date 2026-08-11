DESCRIPTION = "recipe for hello with cmake in CPP"
LICENSE = "CLOSED"

SRC_URI = "\
    file://hello.cpp \
    file://CMakeLists.txt \
"

S = "${WORKDIR}"

inherit cmake
