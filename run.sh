#!/bin/sh
rm -rf mvn-package-demo
mkdir mvn-package-demo
mvn clean package
tar -xzvf ./target/mvn-package-demo-1.0.tar.gz -C mvn-package-demo
reset
sleep 1
echo 'start......'
mvn-package-demo/mvn-package-demo-1.0/bin/app.sh start

