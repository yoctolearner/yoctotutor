DESCRIPTION = "recipe for static library"
LICENSE  = "CLOSED"

SRC_URI = "\
        file://math.h \
        file://math.c \
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
