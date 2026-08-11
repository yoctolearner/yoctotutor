DESCRIPTION = "recipe for static lib using cmake"
LICENSE ="CLOSED"

SRC_URI = "\
        file://math.cpp \
        file://math.hpp \
        file://CMakeLists.txt \
"

S = "${WORKDIR}"

inherit cmake

