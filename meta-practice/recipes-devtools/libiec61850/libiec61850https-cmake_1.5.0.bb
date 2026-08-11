DESCRIPTION = "recipe for libiec61850 library"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "https://github.com/mz-automation/libiec61850/archive/refs/tags/v1.5.0.tar.gz"
SRCREV = "fcefc746fea286aeaa40d2f62240216da81c85e5"
SRC_URI[sha256sum] = "7b832c195ae9f42faa1ccfe1b82b9ff187103155ce45aaca08881be98459d164"

S = "${WORKDIR}/libiec61850-1.5.0"

inherit cmake
