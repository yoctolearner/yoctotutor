DESCRIPTION = "recipe for static lib in CPP with make"
LICENSE = "CLOSED"

SRC_URI = "\
        file://math.cpp \
        file://math.hpp \
        file://Makefile \
"

S = "${WORKDIR}"

do_compile(){
        oe_runmake
}

do_install(){
        install -d ${D}${libdir}
        install -m 777 ${S}/libstatic.a ${D}${libdir}
}
