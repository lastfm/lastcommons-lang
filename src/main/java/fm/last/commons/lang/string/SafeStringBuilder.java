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
package fm.last.commons.lang.string;

import java.util.Date;

/**
 * Utility class for building Strings with default values for null
 */
public class SafeStringBuilder {
  private final StringBuilder builder = new StringBuilder();
  private final String delimiter;
  private final String stringDefault;
  private final String numberDefault;
  private final String dateDefault;

  /**
   * Creates a new SafeStringBuilder
   * 
   * @param delimiter characters as delimiter between each value appended
   * @param stringDefault default value for null Strings
   * @param numberDefault default value for null numbers (java.lang.Integer, java.lang.Long)
   * @param dateDefault default value for null java.util.Date
   */
  public SafeStringBuilder(String delimiter, String stringDefault, String numberDefault, String dateDefault) {
    this.delimiter = delimiter;
    this.stringDefault = stringDefault;
    this.numberDefault = numberDefault;
    this.dateDefault = dateDefault;
  }

  /**
   * Appends an Object to the final string, using stringDefault if null
   * 
   * @param value Object to be appended
   * @return SafeStringBuilder for method chaining
   */
  public SafeStringBuilder append(Object value) {
    this.appendObjectWithDefault(value, this.stringDefault);
    return this;
  }

  /**
   * Appends a String to the final string, using stringDefault if null
   * 
   * @param value String to be appended
   * @return SafeStringBuilder for method chaining
   */
  public SafeStringBuilder append(String value) {
    this.appendObjectWithDefault(value, this.stringDefault);
    return this;
  }

  /**
   * Appends an Integer to the final string, using numberDefault if null
   * 
   * @param value Integer to be appended
   * @return SafeStringBuilder for method chaining
   */
  public SafeStringBuilder append(Integer value) {
    this.appendObjectWithDefault(value, this.numberDefault);
    return this;
  }

  /**
   * Appends a Long to the final string, using numberDefault if null
   * 
   * @param value Long to be appended
   * @return SafeStringBuilder for method chaining
   */
  public SafeStringBuilder append(Long value) {
    this.appendObjectWithDefault(value, this.numberDefault);
    return this;
  }

  /**
   * Appends a Date to the final string, using dateDefault if null
   * 
   * @param value Date to be appended
   * @return SafeStringBuilder for method chaining
   */
  public SafeStringBuilder append(Date value) {
    this.appendObjectWithDefault(value, this.dateDefault);
    return this;
  }

  /**
   * Appends an Object to the final string, using stringDefault if null
   * 
   * @param value Object to be appended
   * @return SafeStringBuilder for method chaining
   */
  public SafeStringBuilder appendAsString(Object value) {
    this.appendObjectWithDefault(value, this.stringDefault);
    return this;
  }

  /**
   * Appends an Object to the final string, using numberDefault if null
   * 
   * @param value Object to be appended
   * @return SafeStringBuilder for method chaining
   */
  public SafeStringBuilder appendAsNumber(Object value) {
    this.appendObjectWithDefault(value, this.numberDefault);
    return this;
  }

  /**
   * Appends an Object to the final string, using dateDefault if null
   * 
   * @param value Object to be appended
   * @return SafeStringBuilder for method chaining
   */
  public SafeStringBuilder appendAsDate(Object value) {
    this.appendObjectWithDefault(value, this.dateDefault);
    return this;
  }

  /**
   * Appends an Object to the final string, using the defaultValue if null
   * 
   * @param value Object to be appended
   * @param defaultValue appended if value is null
   * @return SafeStringBuilder for method chaining
   */
  public SafeStringBuilder appendObjectWithDefault(Object value, String defaultValue) {
    if (value == null) {
      builder.append(defaultValue);
    } else {
      builder.append(value);
    }
    builder.append(this.delimiter);
    return this;
  }

  
  /**
   * Generates the final string
   * 
   * @return the final String containing all values separated by delimiter
   */
  @Override
  public String toString() {
    builder.setLength(builder.length() - this.delimiter.length());
    return builder.toString();
  }
}