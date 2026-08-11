DESCRIPTION = "recipe for dynamic library"
LICENSE = "CLOSED"

SRC_URI = "\
        file://math.c \
        file://math.h \
        file://CMakeLists.txt \
"

S = "${WORKDIR}"

inherit cmake

