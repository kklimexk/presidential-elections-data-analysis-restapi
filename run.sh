#!/bin/sh

cd target/universal/presidential-elections-data-analysis-restapi-1.0-SNAPSHOT/bin/
nohup ./presidential-elections-data-analysis-restapi -Dplay.crypto.secret=abcdefghijk > out.log &
