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

import org.apache.log4j.Logger;

/**
 * Simple(ish) class for retrying something on exceptions. Useful for doing things which may timeout or stop responding
 * temporarily. It logs a warning on each retry (with log4j). If it exceeds the maximum attempts the last exception is
 * thrown. It catches all exceptions, and does not catch an Error. Here is an example of usage, it tries 10 times with a
 * 5 second pause between each try.
 * 
 * <pre>
 * try {
 *   new RetryTemplate(10, 5).execute(new RetryTemplate.RetryCallback() {
 *     public Object doWithRetry() throws Exception {
 *       // dosome processing
 *       return null;
 *     }
 *   });
 * } catch (Exception e) {
 *   log.fatal(e);
 *   throw e;
 * }
 * </pre>
 * 
 * If you wish to return a value, you can return it as an object and cast it.
 * 
 * <pre>
 * String hello = (String) new RetryTemplate(10, 5).execute(new RetryTemplate.RetryCallback() {
 *   public Object doWithRetry() throws Exception {
 *     return &quot;hello world&quot;;
 *   }
 * });
 * System.out.println(hello);
 * </pre>
 * 
 * To use variables within the function either declare it final, or extend RetryCallback. Eg,
 * 
 * <pre>
 * final String hello = &quot;hello world&quot;;
 * new RetryTemplate(10, 5).execute(new RetryTemplate.RetryCallback() {
 *   public Object doWithRetry() throws Exception {
 *     System.out.println(hello);
 *     return null;
 *   }
 * });
 * </pre>
 * 
 * or
 * 
 * <pre>
 * public class PrintCallback extends RetryTemplate.RetryCallback {
 *   private String str = null;
 * 
 *   public PrintCallback(String str) {
 *     this.str = str;
 *   }
 * 
 *   public Object doWithRetry() throws Exception {
 *     System.out.println(str);
 *     return null;
 *   }
 * }
 * new RetryTemplate(10, 5).execute(new PrintCallback(&quot;hello world&quot;));
 * </pre>
 * 
 * You can optionally override the RetryCallback onException function if for example you wish to log each exception or
 * close and reopen a connection. Eg:
 * 
 * <pre>
 * SomeConnectionClass connection = bla bla;
 * 
 * new RetryTemplate(10,5).execute(new RetryTemplate.RetryCallback(){
 *   public Object doWithRetry() throws Exception {
 *     // dosome processing
 *     return null;
 *   }
 *   
 *   public void onException(Exception e) {
 *     log.warn(e);
 *     connection.close();
 *     connection.open();
 *   }
 * });
 * </pre>
 */
public class RetryTemplate {
  private static Logger log = Logger.getLogger(RetryTemplate.class);

  /**
   * Abstract base class for use with RetryTemplate
   */
  abstract public static class RetryCallback<T> {
    /**
     * Override this.
     */
    abstract public T doWithRetry() throws Exception;

    /**
     * Does nothing. Override this if you want to do something after each exception, eg. logging.
     */
    public void onException(Exception e) {
    }
  }

  private int maxAttempts = 10;
  private int sleepTimeInSeconds = 10;

  public RetryTemplate() {
  }

  public RetryTemplate(int maxAttempts, int sleepSecs) {
    this.maxAttempts = maxAttempts;
    sleepTimeInSeconds = sleepSecs;
  }

  /**
   * Calls doWithRetry on the callback object while an exception occurs or we reach the maximum retries.
   */
  public <T> T execute(RetryCallback<T> callback) throws Exception {
    int attempts = 0;
    T result = null;
    while (attempts++ < maxAttempts) {
      try {
        result = callback.doWithRetry();
        break;
      } catch (Exception e) {
        // user may override this method for doing logging, or connection closing / reopening.
        callback.onException(e);
        if (attempts >= maxAttempts) {
          log.warn("Attempt failed, max attempts reached");
          throw e;
        } else {
          log.warn("Attempt failed, retry " + attempts + " in " + sleepTimeInSeconds + " seconds");
          retrySleep();
        }
      }
    }

    return result;
  }

  protected void retrySleep() {
    if (sleepTimeInSeconds > 0) {
      try {
        Thread.sleep(sleepTimeInSeconds * 1000);
      } catch (InterruptedException interupt) {
        log.warn("sleep time interupted");
      }
    }
  }
}
