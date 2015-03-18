# What is logging? #

Many times, when using large amounts of code, a debugger or a variable watch is just not enough, or sometimes is too much of an information overload. Logging inserts some more code into the program which can output messages, exceptions, and comments to the console, to a remote logging service, or to a file. These messages, specified by the programmer himself (or herself), can be viewed separately and can give vital information such as the currently executing thread, executing class, method called, stack traces, and even the line number where the logging code was generated. Programmers can easily spot where the program went wrong, hung, or crashed. In large applications, logging typically makes up up to four percent of all code; and it really is helpful.

As Brian W. Kernighan and Rob Pike put it in their truly excellent book "The Practice of Programming",
> As personal choice, we tend not to use debuggers beyond getting a stack trace or the value of a variable or two. One reason is that it is easy to get lost in details of complicated data structures and control flow; we find stepping through a program less productive than thinking harder and adding output statements and self-checking code at critical places. Clicking over statements takes longer than scanning the output of judiciously-placed displays. It takes less time to decide where to put print statements than to single-step to the critical section of code, even assuming we know where that is. More important, debugging statements stay with the program; debugging sessions are transient.

# About logging in this project -- #

In this project, an open source logging utility, 'Apache Log4J', is used for all logging work. The jar for log4j has been imported into the project itself. Also, as Apache puts it --

> Logging does have its drawbacks. It can slow down an application. If too verbose, it can cause scrolling blindness. To alleviate these concerns, log4j is designed to be reliable, fast and extensible. Since logging is rarely the main focus of an application, the log4j API strives to be simple to understand and to use.

For more information on Apache Log4j, visit the [Apache Logging Homepage](http://logging.apache.org).

# Enabling logging -- #

Logging is disabled by default. Assuming you have the jar downloaded (the project jar), to enable logging, follow these steps from the command line (in Windows) --

  1. Switch to the jar's directory.
  1. Type `java -jar EasyJavaMedia.jar -enableLog`

Logging statements will be shown on the console as well as in a file named `log.log` inside the jar.

**The log file does not clear itself after every session. You need to clear it after every session yourself by deleting all content inside.**

## Thank you! ##