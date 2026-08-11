require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL:append = "\
                hello \
                hello-cc \
                hello-make \
                hello-cmake  \
                hello-cc-make  \
                hellomath-cmake \
                libdynamic-cmake \
                libiec61850-cmake \
                libmodbus \
"


