DESCRIPTION = "recipe for static lib creation"
LICENSE = "CLOSED"

SRC_URI = "\
        file://math.c \
        file://math.h \
        file://CMakeLists.txt \
"
S = "${WORKDIR}"

inherit cmake
