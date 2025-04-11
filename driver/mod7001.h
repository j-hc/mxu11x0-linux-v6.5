//
// These definitions are only for "MOXA USB-to-Serial Port Driver" device
//

#ifndef __MOD7001_H__
#define __MOD7001_H__

#ifndef DEFAULT_UART_MODE
#define DEFAULT_UART_MODE 0
#endif

#if (DEFAULT_UART_MODE==0)
#ifdef NOT_ALLOW_RS4852W
#error The default parameters conflict.
#endif
#endif

#if (DEFAULT_UART_MODE==1)
#ifdef NOT_ALLOW_RS232
#error The default parameters conflict.
#endif
#endif

#if (DEFAULT_UART_MODE==2)
#ifdef NOT_ALLOW_RS422
#error The default parameters conflict.
#endif
#endif

#if (DEFAULT_UART_MODE==3)
#ifdef NOT_ALLOW_RS4854W
#error The default parameters conflict.
#endif
#endif

#endif /* #ifndef __MOD7001_H__ */
