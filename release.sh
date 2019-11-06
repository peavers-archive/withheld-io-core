#!/bin/bash

./gradlew clean build

docker build --no-cache . -t peavers/challenges-io:latest

docker push peavers/challenges-io:latest
