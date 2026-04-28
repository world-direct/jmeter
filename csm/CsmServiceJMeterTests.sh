#! /bin/bash

# Copyright 2015 the original author or authors.
# Licensed under the Apache License, Version 2.0 (https://www.apache.org/licenses/LICENSE-2.0)

# Set the script root directory (directory of this script)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Path to JMeter executable: use first argument if provided, otherwise fall back to default
JMETER_BIN="${1:-/home/johannes/dev/jmeter/bin/jmeter.sh}"

# Validate JMeter executable
if [[ ! -f "$JMETER_BIN" ]]; then
    echo "Error: JMeter executable not found at '$JMETER_BIN'" >&2
    exit 1
fi

if [[ ! -x "$JMETER_BIN" ]]; then
    echo "Error: JMeter executable at '$JMETER_BIN' is not executable" >&2
    exit 1
fi

# Arguments
ARGUMENTS=()
ARGUMENTS+=("--addprop" "$SCRIPT_DIR/CsmServiceJMeterTests.properties")
ARGUMENTS+=("--testfile" "$SCRIPT_DIR/CsmServiceJMeterTests.jmx")

# Execute JMeter
"$JMETER_BIN" "${ARGUMENTS[@]}"