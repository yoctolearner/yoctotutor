DESCRIPTION = "recipe for static library with make"
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
        install -m 777 ${S}/libdynamic.so.1.0.0 ${D}${libdir}
        
        # soft link
        ln -sf libdynamic.so.1.0.0 ${D}${libdir}/libdynamic.so.1
        ln -sf libdynamic.so.1 ${D}${libdir}/libdynamic.so
}

INSANE_SKIP:${PN} = "ldflags"
