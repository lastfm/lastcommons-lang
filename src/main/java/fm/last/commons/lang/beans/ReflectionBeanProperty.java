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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionBeanProperty<T> implements BeanProperty<T> {

  private final String propertyName;
  private final Method getterMethod;
  private final Method setterMethod;
  private final Field propertyField;

  public ReflectionBeanProperty(Class<?> beanClass, String propertyName) {
    this.propertyName = propertyName;
    this.getterMethod = findGetter(beanClass, propertyName);
    this.setterMethod = findSetter(beanClass, propertyName);

    if (getterMethod == null || setterMethod == null) {
      this.propertyField = findField(beanClass, propertyName);
    } else {
      this.propertyField = null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public T getProperty(Object bean) {
    T value = null;
    if (getterMethod == null) {
      if (propertyField == null) {
        throw new RuntimeException("There is no getter for property \"" + propertyName + "\"");
      } else {
        try {
          boolean accessible = propertyField.isAccessible();
          if (!accessible) {
            propertyField.setAccessible(true);
          }
          value = (T) propertyField.get(bean);
          if (!accessible) {
            propertyField.setAccessible(false);
          }
          return value;
        } catch (IllegalArgumentException e) {
          throw new RuntimeException("IllegalArgumentException getting property \"" + propertyName + "\"", e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException("IllegalAccessException getting property \"" + propertyName + "\"", e);
        }
      }
    }

    try {
      boolean accessible = getterMethod.isAccessible();
      if (!accessible) {
        getterMethod.setAccessible(true);
      }
      value = (T) getterMethod.invoke(bean);
      if (!accessible) {
        getterMethod.setAccessible(false);
      }
      return value;
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("IllegalArgumentException getting property \"" + propertyName + "\"", e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("IllegalAccessException getting property \"" + propertyName + "\"", e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException("Getter for property \"" + propertyName + "\" threw an exception", e.getCause());
    }
  }

  @Override
  public void setProperty(Object bean, T value) {
    if (setterMethod == null) {
      if (propertyField == null) {
        throw new RuntimeException("There is no setter for property \"" + propertyName + "\"");
      } else {
        try {
          propertyField.set(bean, value);
        } catch (IllegalArgumentException e) {
          throw new RuntimeException("IllegalArgumentException setting property \"" + propertyName + "\"", e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException("IllegalAccessException setting property \"" + propertyName + "\"", e);
        }
      }
    } else {
      try {
        setterMethod.invoke(bean, value);
      } catch (IllegalArgumentException e) {
        throw new RuntimeException("IllegalArgumentException setting property \"" + propertyName + "\"", e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("IllegalAccessException setting property \"" + propertyName + "\"", e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException("Setter for property \"" + propertyName + "\" threw an exception", e.getCause());
      }
    }
  }

  protected static Method findGetter(Class<?> beanClass, String propertyName) {
    String methodName = "get" + capitalise(propertyName);
    Method method = findMethod(beanClass, methodName);
    if (method == null || method.getParameterTypes().length != 0) {
      return null;
    }
    return method;
  }

  protected static Method findSetter(Class<?> beanClass, String propertyName) {
    String methodName = "set" + capitalise(propertyName);
    Method method = findMethod(beanClass, methodName);
    if (method == null || method.getParameterTypes().length != 1) {
      return null;
    }
    return method;
  }

  protected static Method findMethod(Class<?> beanClass, String methodName) {
    for (Method method : beanClass.getMethods()) {
      if (methodName.equals(method.getName())) {
        return method;
      }
    }
    return null;
  }

  protected static Field findField(Class<?> beanClass, String propertyName) {
    try {
      Field field = beanClass.getDeclaredField(propertyName);
      return field;
    } catch (NoSuchFieldException e) {
      return null;
    }
  }

  private static String capitalise(String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

}
