package fm.last.commons.lang.string;

import java.util.Date;

public class SafeStringBuilder {
  private final StringBuilder builder = new StringBuilder();
  private final String delimiter;
  private final String stringDefault;
  private final String numberDefault;
  private final String dateDefault;

  public SafeStringBuilder(String delimiter, String stringDefault, String numberDefault, String dateDefault) {
    this.delimiter = delimiter;
    this.stringDefault = stringDefault;
    this.numberDefault = numberDefault;
    this.dateDefault = dateDefault;
  }

  public SafeStringBuilder append(Object value) {
    this.appendObjectWithDefault(value, this.stringDefault);
    return this;
  }

  public SafeStringBuilder append(String value) {
    this.appendObjectWithDefault(value, this.stringDefault);
    return this;
  }

  public SafeStringBuilder append(Integer value) {
    this.appendObjectWithDefault(value, this.numberDefault);
    return this;
  }

  public SafeStringBuilder append(Long value) {
    this.appendObjectWithDefault(value, this.numberDefault);
    return this;
  }

  public SafeStringBuilder append(Date value) {
    this.appendObjectWithDefault(value, this.dateDefault);
    return this;
  }

  public SafeStringBuilder appendAsString(Object value) {
    this.appendObjectWithDefault(value, this.stringDefault);
    return this;
  }

  public SafeStringBuilder appendAsNumber(Object value) {
    this.appendObjectWithDefault(value, this.numberDefault);
    return this;
  }

  public SafeStringBuilder appendAsDate(Object value) {
    this.appendObjectWithDefault(value, this.dateDefault);
    return this;
  }

  public SafeStringBuilder appendObjectWithDefault(Object value, String defaultValue) {
    if (value == null) {
      builder.append(defaultValue);
    } else {
      builder.append(value);
    }
    builder.append(this.delimiter);
    return this;
  }

  @Override
  public String toString() {
    builder.setLength(builder.length() - this.delimiter.length());
    return builder.toString();
  }
}