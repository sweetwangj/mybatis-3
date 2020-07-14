/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;

/**
 * An actual SQL String got from an {@link SqlSource} after having processed any dynamic content.
 * The SQL may have SQL placeholders "?" and an list (ordered) of an parameter mappings
 * with the additional information for each parameter (at least the property name of the input object to read
 * the value from).
 * <p>
 * Can also have additional parameters that are created by the dynamic language (for loops, bind...).
 *
 * 建立Sql和参数的地方，三个常用的属性
 * @author Clinton Begin
 */
public class BoundSql {
  /**
   * 就是我们写在映射器里的一条sql
   */
  private final String sql;
  /**
   * ParameterMapping集合
   */
  private final List<ParameterMapping> parameterMappings;
  /**
   * 参数本身 可以传递简单对象，POJO ，Map，@Param注解多种形式的参数
   * 1.传递简单对象（int String，float，double等）会把参数转换为其对应的包装类，例如int 会转换为 Integer
   * 2.传递对象POJO 或者Map 时，这个parameterObject就是传递参数的本身
   * 3.当传递多个参数，并且没有使用@Param 注解时，mybatis会把参数转化为Map<String,Object>类型的，键值的关系是按照顺序来规划的
   * 类似的形式{“1”:p1,"2":p2,"3":p3,....,"param1":p1,"param2":p2,"param3":p3,...} 所以此时可以通过 #{param1} 或者#{1} 去引用第一个参数
   * 4.多参数，用@Param注解 @Param("key1") String p1,@Param("key2") String p2,@Param("key3") String p3 会把数字键值替换为@Param注解中的值
   * {“key1”:p1,"key2":p2,"key3":p3,....,"param1":p1,"param2":p2,"param3":p3,...}
   */
  private final Object parameterObject;
  private final Map<String, Object> additionalParameters;
  private final MetaObject metaParameters;

  public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
    this.sql = sql;
    this.parameterMappings = parameterMappings;
    this.parameterObject = parameterObject;
    this.additionalParameters = new HashMap<>();
    this.metaParameters = configuration.newMetaObject(additionalParameters);
  }

  public String getSql() {
    return sql;
  }

  public List<ParameterMapping> getParameterMappings() {
    return parameterMappings;
  }

  public Object getParameterObject() {
    return parameterObject;
  }

  public boolean hasAdditionalParameter(String name) {
    String paramName = new PropertyTokenizer(name).getName();
    return additionalParameters.containsKey(paramName);
  }

  public void setAdditionalParameter(String name, Object value) {
    metaParameters.setValue(name, value);
  }

  public Object getAdditionalParameter(String name) {
    return metaParameters.getValue(name);
  }
}
