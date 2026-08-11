DESCRIPTION = "recipe for libiec61850 library"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/mz-automation/libiec61850.git;protocol=https;branch=${BRANCH}"
#SRCREV = "fcefc746fea286aeaa40d2f62240216da81c85e5"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "DESTDIR=${D}  PREFIX=${libdir}"

do_compile(){
        oe_runmake
}

do_install(){
    oe_runmake ${EXTRA_OEMAKE} install
}

