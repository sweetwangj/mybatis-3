/**
 *    Copyright 2009-2015 the original author or authors.
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

/**
 *  STATEMENT：直接操作sql，不进行预编译，获取数据：$—Statement
 *  PREPARED:预处理，参数，进行预编译，获取数据：#—–PreparedStatement:默认 默认 默认
 *  CALLABLE:执行存储过程————CallableStatement
 *  在配置文件中使用方式：
 *  <update id="update4" statementType="STATEMENT">
 *     update tb_car set price=${price} where id=${id}
 *  </update>
 *
 *  <update id="update5" statementType="PREPARED">
 *     update tb_car set xh=#{xh} where id=#{id}
 *  </update>
 *  注意：不同类型，对应的取值方式也不相同
 *  如果只为STATEMENT，那么sql就是直接进行的字符串拼接
 *  如果为PREPARED，是使用的参数替换，也就是索引占位符，我们的#会转换为?再设置对应的参数的值。
 * @author Clinton Begin
 */
public enum StatementType {
  STATEMENT, PREPARED, CALLABLE
}
