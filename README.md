# homeworkjure

Helping my 7 year old daughter with math homework

## Overview

**Uses the Speech Recognition API, which is only available in Google Chrome at the moment.**

## Setup

To get an interactive development environment run:

    lein figwheel

and open your **Chrome** browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 

## License

Copyright © 2018 Anders Nilsson

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
