/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * TCP Sampler Client implementation which reads and writes binary data.
 *
 * Input/Output strings are passed as hex-encoded binary strings.
 *
 */
package org.apache.jmeter.protocol.tcp.sampler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.util.JOrphanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TCPClient implementation.
 * Reads data until the defined EOM byte is reached.
 * If there is no EOM byte defined, then reads until
 * the end of the stream is reached.
 * The EOM byte is defined by the property "tcp.BinaryTCPClient.eomByte".
 *
 * Input data is assumed to be in hex, and is converted to binary
 */
public class BinaryTCPClientSimpleImpl extends BinaryTCPClientImpl {

    private static final Logger log = LoggerFactory.getLogger(BinaryTCPClientSimpleImpl.class);
    private static final int eolPosition = JMeterUtils.getPropDefault("tcp.BinaryTCPClient.eomBytePositionFromEnd", 1000); // $NON_NLS-1$

    public BinaryTCPClientSimpleImpl() {
        super();
        log.info("Using eolPosition={}", eolPosition);
    }


    /**
     * Reads data until the defined EOM byte is reached.
     * If there is no EOM byte defined, then reads until
     * the end of the stream is reached.
     * Response data is converted to hex-encoded binary
     * @return hex-encoded binary string
     * @throws ReadException
     */
    @Override
    public String read(InputStream is, SampleResult sampleResult) throws ReadException {
        ByteArrayOutputStream w = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            int x = 0;
            boolean first = true;
            while ((x = is.read(buffer)) > -1) {
                if (first) {
                    sampleResult.latencyEnd();
                    first = false;
                }
                w.write(buffer, 0, x);
                if (useEolByte && (buffer[x - eolPosition] == eolByte)) {
                    break;
                }
            }
        } catch (SocketTimeoutException e) {
            if (useEolByte) {
                throw new ReadException("Socket timed out while looking for EOM", e,
                        JOrphanUtils.baToHexString(w.toByteArray()));
            }
            log.debug("Ignoring SocketTimeoutException, as we are not looking for EOM", e);
        } catch (IOException e) {
            throw new ReadException("Problems while trying to read", e, JOrphanUtils.baToHexString(w.toByteArray()));
        }
        final String hexString = JOrphanUtils.baToHexString(w.toByteArray());
        if(log.isDebugEnabled()) {
            log.debug("Read: {}\n{}", w.size(), hexString);
        }
        return hexString;
    }
}
