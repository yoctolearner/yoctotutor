DESCRIPTION = "recipe for the libiec61850 with cmake"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/mz-automation/libiec61850.git;nobranch=1;protocol=https"
SRCREV = "fcefc746fea286aeaa40d2f62240216da81c85e5"

S = "${WORKDIR}/git"

inherit cmake
