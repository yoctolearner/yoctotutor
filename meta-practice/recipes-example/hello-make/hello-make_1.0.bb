DESCRIPTION = "recipe for hello world using make in C"
LICENSE = "CLOSED"

SRC_URI = "\
        file://hello-make.c \ 
        file://Makefile \
"

S = "${WORKDIR}"

do_compile(){
        oe_runmake 
}

do_install(){
        install -d ${D}${bindir}
        install -m 777 ${S}/hello-make ${D}${bindir}
}

INSANE_SKIP:${PN} = "ldflags"
