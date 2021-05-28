# Scala ADT support for Apache Flink

This is a prototype of Magnolia-based serializer framework for Apache Flink, with
more Scala-specific TypeSerializer & TypeInformation derivation support.

* can support ADTs (Algebraic data types, sealed trait hierarchies)
* correctly handles `case object` 
* can be extended with custom serializers even for deeply-nested types
* has no fallback to Kryo
* reuses all the low-level serialization code from Flink for basic Java and Scala types

TODO:
* moar testing.
* not all Scala and Java types are supported out of the box yet.

Issues:
* as this project relies on macro to derive TypeSerializer instances, if you're using IntelliJ, it may
highlight your code with red, hinting that it cannot find corresponding implicits. And this is fine, the code
compiles OK.

## Usage

`flink-adt` is released to Maven-central. For SBT, add this snippet to `build.sbt`:
```scala
libraryDependencies += "io.findify" %% "flink-adt" % "0.3.0"
```

To use the library, you need to change the way you import Flink's serialization support. 

So you should never do a wildcard import `import org.apache.flink.api.scala._`, but import 
only specific required classes from the `api.scala` package. 
And also add yet another set of imports:
```scala
  import io.findify.flinkadt.api.typeinfo._
  import io.findify.flinkadt.api.serializer._
  import io.findify.flinkadt.instances.all._
```

Then you can write your code as usual, so Flink will use the serialization support 
from this library.

## Licence

The MIT License (MIT)

Copyright (c) 2019 Findify AB

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.