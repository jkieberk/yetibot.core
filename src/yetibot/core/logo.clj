(ns yetibot.core.logo
  (:require
    [yetibot.core.version :refer [version]]))

(def logo (format "
  101       10111011        101 0
  111     111      11       01110
  1101   1           1     111101
  1111   1                 011111
  1110  1              1  111111
  01111 1              1  01110
  1111011              10111101        WELCOME TO YETIBOT
   111101  11     11   0001111
   10110   00     00   111111
    1110               11101           %s
     100    11111001   1011
      11    1111011    1
       1               1               ☆╮╰☆╮╰☆╮ ☆╮╰☆╮╰☆╮
       0               1
       0               1
       1               1
       1               1
       1               1
       1               1
       10 1 1 11 0 0 110
         0000000010000111
         101111  11110
         1011011110110
       11101101111011011
        1110011111100111
           " version))
