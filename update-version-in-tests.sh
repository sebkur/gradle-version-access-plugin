#!/bin/bash

if [ "$#" -ne "2" ]; then
    echo "usage: $0 <old version> <new version>"
    exit 1
fi

OLD="$1"
NEW="$2"

OLDFULL="de.topobyte:gradle-version-access-plugin:$OLD"
NEWFULL="de.topobyte:gradle-version-access-plugin:$NEW"

find test test-local/ -name build.gradle | xargs sed -i -e "s/$OLD/$NEW/"
