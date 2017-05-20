#!/bin/bash

JREPATH="/usr/lib/jvm/java-8-openjdk-amd64/jre"
USERNAME="ewvem"
PATH_TO_PROJECT="/home/${USERNAME}/code/binmonkey/p3p-client"

${JREPATH}/bin/keytool -keystore ${JREPATH}/lib/security/cacerts -import -alias server -file ${PATH_TO_PROJECT}/src/test/resources/keys/server.cer