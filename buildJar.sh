#!/bin/bash

set -e

mkdir -p build
javac -d build src/controller/*.java src/model/*.java src/view/userInterface/*.java
cp -r src/res/ build/
cp src/view/userInterface/mainView.fxml build/view/userInterface/
cp src/config.properties build/
cp -r META-INF/ build/

cd build
jar cmvf META-INF/MANIFEST.MF CIS3260CarSimTeam2.jar *
cd ..
