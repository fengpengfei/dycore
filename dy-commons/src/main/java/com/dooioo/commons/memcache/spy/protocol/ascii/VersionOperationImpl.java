/**
 * Copyright (C) 2006-2009 Dustin Sallings
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */

package com.dooioo.commons.memcache.spy.protocol.ascii;

import java.nio.ByteBuffer;

import com.dooioo.commons.memcache.spy.ops.NoopOperation;
import com.dooioo.commons.memcache.spy.ops.OperationCallback;
import com.dooioo.commons.memcache.spy.ops.OperationState;
import com.dooioo.commons.memcache.spy.ops.OperationStatus;
import com.dooioo.commons.memcache.spy.ops.VersionOperation;


/**
 * Operation to request the version of a memcached server.
 */
final class VersionOperationImpl extends OperationImpl implements
    VersionOperation, NoopOperation {

  private static final byte[] REQUEST = "version\r\n".getBytes();

  public VersionOperationImpl(OperationCallback c) {
    super(c);
  }

  @Override
  public void handleLine(String line) {
    assert line.startsWith("VERSION ");
    getCallback().receivedStatus(
        new OperationStatus(true, line.substring("VERSION ".length())));
    transitionState(OperationState.COMPLETE);
  }

  @Override
  public void initialize() {
    setBuffer(ByteBuffer.wrap(REQUEST));
  }

  @Override
  public String toString() {
    return "Cmd: version";
  }
}
