#!/bin/bash

if [ "$#" -ne "2" ]; then
    echo "usage: $0 <old version> <new version>"
    exit 1
fi

OLD="$1"
NEW="$2"

OLDFULL1="de.topobyte:gradle-version-access-plugin:$OLD"
NEWFULL1="de.topobyte:gradle-version-access-plugin:$NEW"

OLDFULL2="'de.topobyte.version-access-gradle-plugin' version '$OLD'"
NEWFULL2="'de.topobyte.version-access-gradle-plugin' version '$NEW'"

find test test-local/ -name build.gradle | xargs sed -i -e "s/$OLDFULL1/$NEWFULL1/"
find test test-local/ -name build.gradle | xargs sed -i -e "s/$OLDFULL2/$NEWFULL2/"
