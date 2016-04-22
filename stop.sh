#!/bin/sh

cd target/universal/presidential-elections-data-analysis-restapi-1.0-SNAPSHOT/
kill `cat RUNNING_PID`
rm -rf RUNNNING_PID
