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
package fm.last.commons.lang.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import fm.last.commons.lang.beans.testbeans.TestBean;

public class ReflectionBeanPropertyTest {

  private final String newValue = "new value";
  private final String publicProperty = "public property";
  private final String privateProperty = "private property";
  private final String immutableProperty = "immutable property";
  private final String accessorProperty = "accessor property";
  private final String settableProperty = "settable property";
  private final String wrongMethodsProperty = "wrong methods property";

  private TestBean testBean;

  @Before
  public void createTestBean() {
    testBean = new TestBean();
    testBean.publicProperty = publicProperty;
    ReflectionTestUtils.setField(testBean, "privateProperty", privateProperty);
    ReflectionTestUtils.setField(testBean, "immutableProperty", immutableProperty);
    ReflectionTestUtils.setField(testBean, "wrongMethodsProperty", wrongMethodsProperty);
    testBean.setAccessorProperty(accessorProperty);
    testBean.setSettableProperty(settableProperty);
  }

  @Test
  public void publicProperty() throws Exception {
    BeanProperty<String> property = new ReflectionBeanProperty<String>(TestBean.class, "publicProperty");
    assertEquals(publicProperty, property.getProperty(testBean));
    property.setProperty(testBean, newValue);
    assertEquals(newValue, testBean.publicProperty);
  }

  @Test(expected = RuntimeException.class)
  public void privateProperty() throws Exception {
    BeanProperty<String> property = new ReflectionBeanProperty<String>(TestBean.class, "privateProperty");
    assertEquals(privateProperty, property.getProperty(testBean));
    property.setProperty(testBean, newValue);
    assertEquals(newValue, ReflectionTestUtils.getField(testBean, "privateProperty"));
  }

  @Test(expected = RuntimeException.class)
  public void immutableProperty() {
    ReflectionBeanProperty<String> property = new ReflectionBeanProperty<String>(TestBean.class, "immutableProperty");
    assertEquals(immutableProperty, property.getProperty(testBean));
    property.setProperty(testBean, newValue);
    assertEquals(newValue, testBean.getImmutableProperty());
  }

  @Test
  public void settableProperty() {
    ReflectionBeanProperty<String> property = new ReflectionBeanProperty<String>(TestBean.class, "settableProperty");
    assertEquals(settableProperty, property.getProperty(testBean));
    property.setProperty(testBean, newValue);
    assertEquals(newValue, ReflectionTestUtils.getField(testBean, "settableProperty"));
  }

  @Test
  public void accessorProperty() throws Exception {
    BeanProperty<String> property = new ReflectionBeanProperty<String>(TestBean.class, "accessorProperty");
    assertEquals(accessorProperty, property.getProperty(testBean));
    property.setProperty(testBean, newValue);
    assertEquals(newValue, property.getProperty(testBean));
  }

  @Test(expected = RuntimeException.class)
  public void getNonExistentProperty() {
    ReflectionBeanProperty<String> property = new ReflectionBeanProperty<String>(TestBean.class, "nonexistent");
    property.getProperty(testBean);
  }

  @Test(expected = RuntimeException.class)
  public void setNonExistentProperty() {
    ReflectionBeanProperty<String> property = new ReflectionBeanProperty<String>(TestBean.class, "nonexistent");
    property.setProperty(testBean, "value");
  }

  @Test(expected = RuntimeException.class)
  public void wrongMethodsProperty() {
    ReflectionBeanProperty<String> property = new ReflectionBeanProperty<String>(TestBean.class, "wrongMethodsProperty");
    assertEquals(wrongMethodsProperty, property.getProperty(testBean));
    property.setProperty(testBean, newValue);
    assertEquals(newValue, ReflectionTestUtils.getField(testBean, "settableProperty"));
  }
}
