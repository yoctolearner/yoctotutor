DESCRIPTION = "recipe for hello in CPP"
LICENSE = "CLOSED"

SRC_URI = "\
    file://hello.cpp \
    file://Makefile \
"

S = "${WORKDIR}"

do_cimpile(){
    oe_runmake
}

do_install(){
    install -d ${D}${bindir}
    install -m 777 ${S}/hello-cc-make ${D}${bindir}
}

