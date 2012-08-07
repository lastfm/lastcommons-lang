/*
 * Copyright 2012 Last.fm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fm.last.commons.lang.templates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;

import fm.last.commons.lang.templates.RetryTemplate.RetryCallback;

/**
 * Unit test for the RetryTemplate class
 */
public class RetryTemplateTest {
  private static Logger log = Logger.getLogger(RetryTemplateTest.class);

  static public class CountCallback extends RetryCallback<Void> {
    public int count = 0;
    public int onExceptionCount = 0;
    public RetryCallback<Void> callback = null;

    public CountCallback(RetryCallback<Void> callback) {
      this.callback = callback;
    }

    @Override
    public Void doWithRetry() throws Exception {
      count++;
      return callback.doWithRetry();
    }

    @Override
    public void onException(Exception e) {
      onExceptionCount++;
    }

    public void reset() {
      count = 0;
      onExceptionCount = 0;
    }
  }

  public class ExceptionCallback extends RetryCallback<Void> {
    @Override
    public Void doWithRetry() throws Exception {
      throw new Exception("bla");
    }
  }

  public class ErrorCallback extends RetryCallback<Void> {
    @Override
    public Void doWithRetry() throws Exception {
      throw new Error("bla");
    }
  }

  public class DoNothingCallback extends RetryCallback<Void> {
    @Override
    public Void doWithRetry() throws Exception {
      return null;
    }
  }

  @Test
  public void zeroAttempts() {
    CountCallback counter = new CountCallback(new ExceptionCallback());
    // test we always try at least once.
    try {
      new RetryTemplate(0, 10).execute(counter);
    } catch (Exception e) {
    }
    assertEquals(0, counter.count);
  }

  @Test
  public void negativeAttempts() {
    CountCallback counter = new CountCallback(new ExceptionCallback());
    // test we always try at least once.
    try {
      new RetryTemplate(-1, 10).execute(counter);
    } catch (Exception e) {
    }
    assertEquals(0, counter.count);
  }

  @Test
  public void doesNotExceedMaxAttempts() {
    CountCallback counter = new CountCallback(new ExceptionCallback());
    // test that we stop after we exceed max retries.
    try {
      new RetryTemplate(5, 0).execute(counter);
      fail("did not throw exception");
    } catch (Exception e) {
    }
    assertEquals(5, counter.count);
  }

  @Test
  public void negativeSleepTime() {
    CountCallback counter = new CountCallback(new ExceptionCallback());
    try {
      new RetryTemplate(2, -1).execute(counter);
      fail("did not throw exception");
    } catch (Exception e) {
    }
    assertEquals(2, counter.count);
  }

  @Test
  public void positiveSleepTime() {
    CountCallback counter = new CountCallback(new ExceptionCallback());
    try {
      new RetryTemplate(2, 1).execute(counter);
      fail("did not throw exception");
    } catch (Exception e) {
    }
    assertEquals(2, counter.count);
  }

  @Test
  public void onlyOneAttemptOnSuccess() {
    CountCallback counter = new CountCallback(new DoNothingCallback());
    try {
      new RetryTemplate(2, 2).execute(counter);
    } catch (Exception e) {
      fail("threw an exception");
    }
    // test it was only called once.
    assertEquals(1, counter.count);
  }

  @Test
  public void executeReturn() {
    final String hello = "hello";
    RetryCallback<String> callback = new RetryCallback<String>() {
      @Override
      public String doWithRetry() throws Exception {
        return hello;
      }
    };
    // do we return hello?
    try {
      assertEquals(hello, new RetryTemplate(1, 0).execute(callback));
    } catch (Exception e) {
      fail("threw an exception");
    }
  }

  /**
   * Tests the on throwable function is called for each failure.
   */
  @Test
  public void onException() {
    CountCallback counter = new CountCallback(new ExceptionCallback());
    try {
      new RetryTemplate(5, 0).execute(counter);
      fail("did not throw exception");
    } catch (Exception e) {
    }
    assertEquals(5, counter.count);
    assertEquals(5, counter.onExceptionCount);
  }

  @Test
  public void errorsIgnored() {
    CountCallback counter = new CountCallback(new ErrorCallback());
    try {
      new RetryTemplate(5, 0).execute(counter);
      fail("did not throw exception");
    } catch (Exception e) {
      fail("threw an ordinary exception");
    } catch (Error err) {
      log.error(err);
    }
    // we tried to excute the callback once.
    assertEquals(1, counter.count);
    // the Error should mean we never reach the onException method.
    assertEquals(0, counter.onExceptionCount);
  }
}
