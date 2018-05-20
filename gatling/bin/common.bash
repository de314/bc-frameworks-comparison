#!/usr/bin/env bash
# Common options and functions that are shared across load tests.
#
# Each script must supply $SCRIPT_DIR when sourcing this file, ex:
# SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

[ ! -z "${SCRIPT_DIR:-}" ] || { echo 'Must set SCRIPT_DIR'; exit 1; }
SCRIPT_NAME=${SCRIPT_NAME:-}

JAVA_HOME=${JAVA_HOME:-}
JAVA_OPTS=${JAVA_OPTS:-}

BUILD_DIR="${SCRIPT_DIR}/../build"
GATLING_JAR="${BUILD_DIR}/libs/gatling-all.jar"
GRADLE="${SCRIPT_DIR}/../gradlew"

DEFAULT_JAVA_OPTS="-server"
DEFAULT_JAVA_OPTS="${DEFAULT_JAVA_OPTS} -Xmx1G"
DEFAULT_JAVA_OPTS="${DEFAULT_JAVA_OPTS} -XX:+UseG1GC -XX:MaxGCPauseMillis=30 -XX:G1HeapRegionSize=16m -XX:InitiatingHeapOccupancyPercent=75 -XX:+ParallelRefProcEnabled"
DEFAULT_JAVA_OPTS="${DEFAULT_JAVA_OPTS} -XX:+PerfDisableSharedMem -XX:+AggressiveOpts -XX:+OptimizeStringConcat"
DEFAULT_JAVA_OPTS="${DEFAULT_JAVA_OPTS} -XX:+HeapDumpOnOutOfMemoryError"
DEFAULT_JAVA_OPTS="${DEFAULT_JAVA_OPTS} -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv6Addresses=false"

log() {
  local readonly level="$1"
  local readonly message="$2"
  local readonly timestamp="$(date +"%Y-%m-%d %H:%M:%S")"
  >&2 echo -e "${timestamp} [${level}] [$SCRIPT_NAME] ${message}"
}

log_info() {
  log "INFO" "$@"
}

# Get java executable
# Will first look for java in $JAVA_HOME environment, then will use the first instance of 'java' found in your $PATH
_java() {
  if [ -n "$JAVA_HOME" ] ; then
    "$JAVA_HOME/bin/java" "$@"
  else
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
    java "$@"
  fi
}

# Compiles gatling shadow jar
# Run gradle task 'shadowJar', configured in build.gradle
compile_jar() {
  if [ ! -f "${GATLING_JAR}" ]; then
    log_info "Jar not found for gatling. Building now with gradle..."
    "${GRADLE}" shadowJar
    log_info "Done."
  else
    log_info "Jar found at ${GATLING_JAR}"
  fi
}

# Pass the compiled gatling jar to the classpath.
# Arguments that are passed should be valid gatling arguments: https://gatling.io/docs/2.3/general/configuration/#command-line-options
gatling() {
  _java $DEFAULT_JAVA_OPTS $JAVA_OPTS -cp $GATLING_JAR "io.gatling.app.Gatling" "$@"
}
