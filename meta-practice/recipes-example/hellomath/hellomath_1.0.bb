DESCRIPTION = "recipe for hello math in C using make"
LICENSE = "CLOSED"

SRC_URI = "\
        file://hellomath.c \
        file://Makefile \
"
S = "${WORKDIR}"

do_compile(){
    oe_runmake
}

do_install(){
    install -d ${D}${bindir}
    install -m 777 ${S}/hellomath ${D}${bindir}
}
