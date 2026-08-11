DESCRIPTION = "recipe for hello world in CPP"
LICENSE = "CLOSED"

SRC_URI = "\
    file://hello.cpp \
"
S = "${WORKDIR}"

do_compile(){
    cd ${S}
    ${CC} ${LDFLAGS} -c hello.cpp -o hello-cc
}

do_install(){
    install -d ${D}${bindir}
    install -m 777 ${S}/hello-cc ${D}${bindir}
}

