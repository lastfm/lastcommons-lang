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
package fm.last.commons.lang.beans.testbeans;

import org.junit.Ignore;

@Ignore
public class TestBean {

  public String publicProperty;

  @SuppressWarnings("unused")
  private String privateProperty;

  private String accessorProperty;
  private String immutableProperty;
  private String settableProperty;
  private String wrongMethodsProperty;

  public String getAccessorProperty() {
    return accessorProperty;
  }

  public void setAccessorProperty(String accessorProperty) {
    this.accessorProperty = accessorProperty;
  }

  public String getImmutableProperty() {
    return immutableProperty;
  }

  private void setImmutableProperty(String immutableProperty) {
    this.immutableProperty = immutableProperty;
  }

  public void setSettableProperty(String settableProperty) {
    this.settableProperty = settableProperty;
  }

  private String getSettableProperty() {
    return settableProperty;
  }

  public void setWrongMethodsProperty() {
  }

  public String getWrongMethodsProperty(String dummy) {
    return wrongMethodsProperty;
  }
}
