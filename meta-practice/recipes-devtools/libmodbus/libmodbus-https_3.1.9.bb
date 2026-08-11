DESCRRIPTION = "recipe for libmodbus library using cmake"

LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING.LESSER;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "https://github.com/stephane/libmodbus/releases/download/v3.1.9/libmodbus-3.1.9.tar.gz"
SRC_URI[sha256sum] = "78ce6e6bd7e4058146f89f3673cc8c08408b7042cb1b79d9e135baad663c5323"
SRCREV = "783e651499e1b9de11136cd63d9a53163ab4d94d"

S = "${WORKDIR}/libmodbus-3.1.9"

inherit autotools pkgconfig
