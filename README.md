# About

This Gradle Plugin makes the Gradle project version number available
for runtime access by generating a Java source file.

# License

This software is released under the terms of the GNU Lesser General Public
License.

See  [LGPL.md](LGPL.md) and [GPL.md](GPL.md) for details.

# Usage

In your `build.gradle` file, add this:

    buildscript {
        repositories {
            maven { url 'http://mvn.topobyte.de' }
        }
        dependencies {
            classpath 'de.topobyte:gradle-version-access-plugin:0.1.0'
        }
    }

    apply plugin: 'de.topobyte.version-access-gradle-plugin'

    generateVersionAccessSource {
        packageName = "de.topobyte.example"
    }

This will create a task `generateVersionAccessSource` that will generate
a class `de.topobyte.example.Version`:

    // this file is generated, do not edit
    package de.topobyte.example;

    public class Version
    {

        public static String getVersion()
        {
            return "1.0.7";
        }

    }

It will also set up dependencies such that `compileJava` and `eclipse*`
tasks depend on the new task.
