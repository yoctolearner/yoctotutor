DESCRIPTION = "recipe for libmbpoll llibrary using cmake"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "https://github.com/epsilonrt/mbpoll/archive/refs/tags/v1.4.11.tar.gz"
SRC_URI[sha256sum] = "5d332e8f413163adfc80abc2407439210fff2eb06559344a3e491d3f6531b001"
SRCREV = "81af8ba119d50e63a6c2c2a5eb51a24c97f83a07"

DEPENDS += "libmodbus"

S = "${WORKDIR}/mbpoll-1.4.11"

inherit cmake pkgconfig
