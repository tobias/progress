# progress - A progress indication library for Clojure

`progress` provides progress indicators, mostly focused around displaying
the progress of file copy/download operations.

## Usage

The current release is [1.0.0](http://clojars.org/progress). You can
add it to a Leiningen project via:

    [progress "1.0.0"]
    
To use it for file progress, use the `with-file-progress` macro from the
`progress.file` namespace:

    (ns my.app
      (:require [progress.file :as progress]))
       
    (let [f "/tmp/foobar.zip"]
      (progress/with-file-progress f 
        ;; do your business here, maybe with:
        (with-open [input (io/input-stream "http://example.com/foobar.zip")]
          (io/copy input f))))
    
This will result in a simple incrementing size counter on \*out\*:

    123.45KB
    
If you know the size of the file being downloaded, you can specify
it (in bytes) and get a fancy pants progress bar:

    (let [f "/tmp/foobar.zip"]
      (progress/with-file-progress f :filesize 1234567
        ;; do your business here, maybe with:
        (with-open [input (io/input-stream "http://example.com/foobar.zip")]
          (io/copy input f))))

    
Which looks like:
    
    [===>                                             ] 5% 60.28KB / 1.18MB
    
The `with-file-progress` macro takes a few options - check out its docstring 
for details. You can also disable its monitoring entirely by setting the 
`progress.monitor` sytem property to "false".

There are progress indicators that are potentially useful for things other
than file downloads - see the `progress.bar` and `progress.file` namespaces.

## Development

There is an entirely inadequate [midje](https://github.com/marick/Midje) 
test suite that you can run via `lein midje` if you have the
[lein-Midje](https://github.com/marick/lein-Midje) plugin installed.

## License

Copyright (C) 2011 Tobias Crawley

Distributed under the Eclipse Public License.
