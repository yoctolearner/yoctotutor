# using make
DESCRIPTION  = "receipe for libmodbus library using make" 

# license information
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING.LESSER;md5=4fbd65380cdd255951079008b364516c"

# source information
SRC_URI = "git://github.com/stephane/libmodbus.git;protocol=https;branch=master"
SRCREV = "783e651499e1b9de11136cd63d9a53163ab4d94d"

# work directory path
S = "${WORKDIR}/git"


inherit autotools pkgconfig
