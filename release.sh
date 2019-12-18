#!/usr/bin/env bash

./gradlew clean build

docker build -t peavers/challenges-io:latest .

docker push peavers/challenges-io:latest
