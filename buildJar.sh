#!/bin/bash

set -e

mkdir -p build
javac -d build src/controller/*.java src/model/*.java src/view/userInterface/*.java
mkdir -p build/res
cp src/res/* build/res/
cp src/view/userInterface/mainView.fxml build/view/userInterface/
cp src/config.properties build/
mkdir -p build/META-INF
cp META-INF/MANIFEST.MF build/META-INF/

cd build
jar cmvf META-INF/MANIFEST.MF CIS3260CarSimTeam2.jar *
cd ..
