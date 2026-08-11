FILESEXTRAPATHS:prepend := "${THISDIR}/files/:"

SRC_URI += "file://world.c"

do_compile:append(){
    ${CC} world.c -o world
}

do_install:append(){
        install -m 777 ${S}/world ${D}/usr/bin
}

INSANE_SKIP:${PN} = "ldflags"
