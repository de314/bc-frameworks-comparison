#!/bin/bash

OUTPUT_FILENAME=`date +%Y-%m-%d-%H-%M-%S-short-perf-results.txt`
OUTPUT_PATH="results/$OUTPUT_FILENAME"
exec 1>$OUTPUT_PATH
exec 2>&1

function run_test() {
  echo "|| Spring Boot $1"
  echo '============================================'
  echo "siege -r$2 -c$3 -d1 http://localhost:13030/$4"
  siege -r$2 -c$3 -d1 "http://localhost:13030/$4" >> $OUTPUT_PATH

  sleep 1
  echo ''

  echo "|| Node JS $1"
  echo '============================================'
  echo "siege -r$2 -c$3 -d1 http://localhost:13031/$4"
  siege -r$2 -c$3 -d1 "http://localhost:13031/$4" >> $OUTPUT_PATH

  sleep 1
  echo ''

  echo "|| Church Reactive Spring Boot $1"
  echo '============================================'
  echo "siege -r$2 -c$3 -d1 http://localhost:13032/$4"
  siege -r$2 -c$3 -d1 "http://localhost:13032/$4" >> $OUTPUT_PATH

  sleep 1
  echo ''

  echo "|| Perkins Vert.x $1"
  echo '============================================'
  echo "siege -r$2 -c$3 -d1 http://localhost:13033/$4"
  siege -r$2 -c$3 -d1 "http://localhost:13033/$4" >> $OUTPUT_PATH
}

function reset_for_next_test() {
  sleep 2

  echo ''
  echo ''
  echo ''
}

run_test 'Noop' 10 25 'noop'
reset_for_next_test
run_test 'CPU Bound Processing' 5 10 'cpu'
reset_for_next_test
run_test 'Artificial Delay' 10 25 'sleep'
reset_for_next_test
run_test 'MySQL Write' 10 25 'write'
reset_for_next_test
run_test 'MySQL Read' 5 10 'read'
