#!/bin/bash

JREPATH="/usr/lib/jvm/java-8-openjdk-amd64/jre"
USERNAME="<user name in computer>"
PATH_TO_PROJECT="/home/${USERNAME}/.../prog3-proyecto_client"

${JREPATH}/bin/keytool -keystore ${JREPATH}/lib/security/cacerts -import -alias server -file ${PATH_TO_PROJECT}/src/test/resources/keys/server.cer