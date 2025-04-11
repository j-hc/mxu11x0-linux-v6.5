DESCRIPTION = "Linux kernel module for Moxa UPort 11x0 series"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING-GPLV2.TXT;md5=5205bcd21ef6900c98e19cf948c26b41"

inherit module

SRC_URI = "file://Makefile \
           file://mxu1110_fw.h \
           file://mxu1130_fw.h \
           file://mxu1131_fw.h \
           file://mxu1150_fw.h \
           file://mxu1151_fw.h \
           file://mxu11x0.h \
           file://mxu3001_fw.h \
           file://mxu7001_fw.h \
           file://mx_ver.h \
           file://usb-serial.h \
           file://mxu11x0.c \
           file://COPYING-GPLV2.TXT \
           "

S = "${WORKDIR}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES_${PN} += "kernel-module-mxu11x0"
