=============================================================================
    MOXA UPort 11x0 USB to Serial Hub Family Driver Installation Guide
    for Linux Kernel 6.x
    Copyright (C) 2019, Moxa Inc.
=============================================================================
Date: 02/24/2023

CONTENTS

0. Note
1. Introduction
2. System Requirements
3. Installation
   3.1 Driver files   
   3.2 Device naming convention
   3.3 Module driver configuration
   3.4 Static driver configuration
   3.5 Verify driver installation
4. Uninstallation
5. Setserial
6. Troubleshooting

-----------------------------------------------------------------------------
0. Note

   The Moxa UPort 11x0 USB to Serial Hub driver can be used in the Linux
   kernel with the usbcore and usbserial modules. This two modules is
   built-in module in the Linux kernel version 2.6.x and follow. In the
   Linux kernel version, you must check this two build-in modules existed.
   Otherwise, you will fail to using MOXA UPort 11x0 USB to Serial Hub driver.

-----------------------------------------------------------------------------
1. Introduction

   The MOXA UPort 11x0 USB to Serial Hub Linux driver supports following 
   devices. 

    - UPort 1110, 1 port RS-232 USB to Serial Hub.
    - UPort 1130, 1 port RS-422/485 USB to Serial Hub.
    - UPort 1130I, 1 port RS-422/485 USB to Serial Hub with isolation
      protection.
    - UPort 1150, 1 port RS-232/422/485 USB to Serial Hub.
    - UPort 1150I, 1 port RS-232/422/485 USB to Serial Hub with isolation
      protection.
    - USB Console, 1 port RS-232 USB to Serial Hub.

   This driver supports x86 and x64(AMD64/EM64T) hardware platform. In 
   order to maintain compatibility, this version has also been properly 
   tested with several Linux distribution (see version.txt). However, 
   if compatibility problem occurs, please contact Moxa Technologies
   technical support. (support@moxa.com)

   All the drivers are published in form of source code under
   GNU General Public License in this version. Please refer to GNU General
   Public License announcement in each source code file for more detail.

   This version of driver can be only installed as Loadable Module (Module 
   driver). Before you install the driver, please refer to hardware installation
   procedure in the User's Manual.

-----------------------------------------------------------------------------
2. System Requirements
     - Hardware platform: x86, x64 
     - Kernel version: 2.6.x and above
     - gcc version 3.x 
     - kernel source

   Additional requirements for Raspbian
     - gcc-4.8.3 or above
     - ncurses-devel-5.9 or above
     - rpi-source, read the instruction from it's website.
       (https://github.com/notro/rpi-source/wiki)

   Note:
     - rpi-source is a 3rd party package offering integrated kernel resouce
       for building driver. Real TTY are tested with this package and working
       well. However the requirements may be various for different Raspbian
       version. Please read the manual of Raspbian and rpi-source to
       understand the knowhow and limitation.
     - Real TTY driver with Raspbian Jessie(4.4.50) and Buster(4.19) are
       tested on Pi 2/3.

-----------------------------------------------------------------------------
3. Installation

   3.1 Driver files   
   3.2 Device naming convention
   3.3 Module driver configuration
   3.4 Static driver configuration 
   3.5 Verify driver installation
       
   3.1 Driver files

       The driver file may be obtained from website, under the product page.
       The first step is to copy driver file
       "driv_linux_uport1p_[VERSION]_[BUILD].tgz" into a user directory.
       e.g. /moxa. Please execute commands as below.

       # cd /moxa
       # tar xvfz driv_linux_uport1p_[VERSION]_[BUILD].tgz

   3.2 Device naming convention
   
       You may find all the driver files in /<driver directory>/mxu11x0/.
       Following installation procedure depends on the model you'd like to
       run the driver.

       Dialin and callout port
       -----------------------
       This driver remains traditional serial device properties. Because the
       limitation of the usb build-in modules that are usbcore and usbserial, 
       There is only one special file name for each serial port. This one is 
       dial-in port which is named "ttyUSBxx". 

   3.3 Module driver configuration

       To simplify the processes behind, we provide a single step to build, 
       install and load the MOXA driver. You may execute the ./mxinstall to 
       use the MOXA product. Once you successfully execute ./mxinstall, you
       can skip step 3.3.1 and 3.3.2.

       3.3.1 Build the MOXA driver
          Before using the MOXA driver, you need compile the all the source 
          code. This step is only need to be executed once.
          But you still re-compile the source code if you modify the source
          code. 
                    
          Find "Makefile" in /moxa/mxu11x0/driver, then run

          # make clean; make install

       The driver files "mxu11x0.ko" will be properly compiled
       and copied to system directories respectively. 
          
       3.3.2 Load the MOXA driver  

          The driver will be loaded automatically while pluging the 
          UPort 1110/1130/1130I/1150/1150I or USB console cable
          into you PC. Besides, you can load the driver manually.
  
          # modprobe mxu11x0 

          It will activate the module driver. You may run "lsmod" to check
          if "mxu11x0" is activated. Before you load this module driver, you
          have to run "lsmod" to check if "usbcore" and "usbserial" are 
          activated.

   3.4 Static driver configuration
       
       Note: To use static driver, you must install the linux kernel
             source package.
   
       3.4.1 Create link
          # cd /usr/src/<kernel-source directory>/drivers/usb/serial/
          # ln -s /moxa/mxu11x0/driver/mxu11x0.c mxu11x0.c
          # ln -s /moxa/mxu11x0/driver/mxu11x0.h mxu11x0.h
          # ln -s /moxa/mxu11x0/driver/mxu1110_fw.h mxu1110_fw.h
          # ln -s /moxa/mxu11x0/driver/mxu1130_fw.h mxu1130_fw.h
          # ln -s /moxa/mxu11x0/driver/mxu1131_fw.h mxu1131_fw.h
          # ln -s /moxa/mxu11x0/driver/mxu1150_fw.h mxu1150_fw.h
          # ln -s /moxa/mxu11x0/driver/mxu1151_fw.h mxu1151_fw.h
          # ln -s /moxa/mxu11x0/driver/mxu3001_fw.h mxu3001_fw.h
          # ln -s /moxa/mxu11x0/driver/mxu7001_fw.h mxu7001_fw.h

       3.4.2 Modify kernel configuration file.
          Add the following line into configuration file.
          /usr/src/<kernel-source directory>/drivers/usb/serial/Kconfig
          ...
          config USB_SERIAL_CONSOLE
          ...
          config USB_SERIAL_GENERIC
          ...
          config MOXA_UPORT_11X0    <-- Add the lines.
          tristate "USB Moxa UPort 11x0 Driver" <-- 
          depends on USB_SERIAL     <--
          ...

       3.4.3 Modify the kernel Makefile 
          Add the following line to the last line of Makefile.
          /usr/src/<kernel-source directory>/drviers/usb/serial/Makefile
          ...
          ...
          ...
          obj-$(CONFIG_MOXA_UPORT_11X0) += mxu11x0.o <-- Add the line.

       3.4.4 Setup kernel configuration
          
          Configure the kernel:

          # cd /usr/src/<kernel-source directory>
          # make menuconfig
            
          You will go into a menu-driven system. Please select [Device Drivers]
          [USB Support], [USB Serial Converter support], enable both the 
          [USB Serial Converter support] and the [USB MOXA UPORT 11x0 Driver]
          drivers with "[*]" by pressing space bar for built-in (not "[M]"),
          then select [Exit] to exit this program and save kernel
          configurations. 
          
       3.4.5 Rebuild kernel
          The following are for Linux kernel rebuilding, for your 
          reference only.
          For appropriate details, please refer to the Linux document.

          a. cd /usr/src/<kernel-source directory>
          b. make
          c. make modules
          d. make modules_install
          e. make install

   3.5 Verify driver installation
       You may refer to /var/log/syslog to check the latest status log reported
       by this driver whenever it's activated or type command "dmesg" to get 
       driver information.

       Following demonstrates the messages when installing UPort 1150.

         usbcore: registered new interface driver mxu11x0
         usbserial: USB Serial support registered for MOXA UPort 1110
         usbserial: USB Serial support registered for MOXA UPort 1130
         usbserial: USB Serial support registered for MOXA UPort 1150
         usbserial: USB Serial support registered for MOXA UPort 1150I
         usbserial: USB Serial support registered for MOXA UPort 1130I
         usbserial: USB Serial support registered for MOXA USB Console
         usbserial: USB Serial support registered for MOXA USB-to-Serial Port Driver
         mxu11x0 1-2.2:1.0: MOXA UPort 1150 converter detected
         usb 1-2.2: MOXA UPort 1150 converter now attached to ttyUSB0
         mxu11x0: Ver6.0: MOXA UPort 11x0 USB to Serial Hub Driver

      Above message indicates /dev/ttyUSB0 are installed successfully.
       
-----------------------------------------------------------------------------
4. Uninstallation

   # cd /moxa/mxu11x0
   # make remove

-----------------------------------------------------------------------------
5. Setserial

   NOTE: To use the setserial tools, you have to install it first.
   The setserial is a free software, you can download it on 
   http://sourceforge.net/projects/setserial/ .

   MOXA UPort 1130/1130I provides three interfaces(RS-485 2W, RS-485 4W, RS-422) 
   and UPort 1150/1150I provides four interfaces(RS-232, RS-485 2W, RS-485 4W,
   RS-422). If you want to switch different interface, you can execute the
   setserial command to set the port's interface. 
   
   UPort driver takes advantage of the setserial tool to configure the UPort.
   Most of the setserial features are not supported because they are depended
   on the UART hardware. The supported Setserial parameters are listed as below.

   parameter   value   interface

   port        0       RS-232
               1       RS-485 2W
               2       RS-422
               3       RS-485 4W

   For example:
     To set the port's informations:
     # setserial /dev/ttyUSB0 port 1

     To get the port's informations:
     # setserial -G /dev/ttyUSB0
   
-----------------------------------------------------------------------------
6. Limitation 
   1. Max. ports
      If you want to install more than one UPort USB serial Hub, the best
      way is to connect to USB port directly. In lab test, four USB ports 
      with four UPorts still work very well. If we connect to USB hub, the
      throughput will be dropped down. To get good performance,please
      connecting UPort 1110/1130/1150/1150I or USB console to USB port 
      directly .    

   2. Compile error
      To build Moxa driver, it needs kernel header files. If you got
      some compile error, please run "rpm -qa | grep kernel" to check 
      whether the kernel-source package is installed properly. If not,
      please get the kernel-source irpm package and run "rpm -ivh <pacakge>"
      to install. For Ubuntu and Debian, if there is no rpm in your system, try
      the following commands to install kernel headers: 

      # apt-get install linux-headers-$(uname -r)
  
      You also need to make sure the build tool is ready, including make/gcc/lib.
      Take Ubuntu and Debian for example, type the commands below to install 
      GNU GCC:

      # apt-get install build-essential

      Please see Linux relevant document to get more information.
      
   3. Probe error
      The UPort 11x0 series USB to Serial Hub need firmware downloaded after 
      attaching to PC. Hence, the driver will reset the UPort after downloading
      firmware and probe the UPort again. After that, the UPort will attach to
      the /dev/ttyUSBxx. 
      Therefore, the message "mxusb:probe of x-x:x.x failed with error -5" 
      will show only once before successfully installing the device. You may 
      refer to /dev/ttyUSBxx to see if the UPort is set correctly.

-----------------------------------------------------------------------------
