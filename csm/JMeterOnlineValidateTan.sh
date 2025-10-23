#! /bin/bash

# Copyright 2015 the original author or authors.
# Licensed under the Apache License, Version 2.0 (https://www.apache.org/licenses/LICENSE-2.0)

# Set the script root directory (directory of this script)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Path to JMeter executable
JMETER_BIN="/home/johannes/dev/tools/apache-jmeter-5.6.3-SNAPSHOT/bin/jmeter.sh"

# Arguments
ARGUMENTS=()
ARGUMENTS+=("--addprop" "$SCRIPT_DIR/csmjmeterOnline.properties")
ARGUMENTS+=("--testfile" "$SCRIPT_DIR/CsmJMeterOnlineLasttest.jmx")

# Execute JMeter
"$JMETER_BIN" "${ARGUMENTS[@]}"
