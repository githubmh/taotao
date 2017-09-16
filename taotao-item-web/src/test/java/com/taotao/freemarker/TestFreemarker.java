package com.taotao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreemarker {
	@Test
	public void testFreemarker() throws Exception{
		//1.创建一个模板文件
		//2.创建一个configuration对象
		Configuration configuration =new Configuration(Configuration.getVersion());
		//3.社会模板所在的路径
		configuration.setDirectoryForTemplateLoading(new File("D:/eclipseWorkSpace/taotao-item-web/src/main/webapp/WEB-INF/ftl"));
		//4.设置模板的字符集，一般utf-8
		configuration.setDefaultEncoding("utf-8");
		//5.使用configuration对象加载一个模板文件，需要指定模板文件的文件名
		Template template = configuration.getTemplate("student.ftl");
		//6.创建一个数据集，可以是pojo也可以是map，推荐使用map
		Map data = new HashMap<>();
		data.put("hello", "hello freemarker");
		Student student = new Student(1, "小米", 11, "北京天安门");
		data.put("student", student);
		//7.创建一个writer对象，指定输出文件的路径及文件名
		Writer out =new FileWriter(new File("D:/temp/out/student.html"));
		//8.使用模板对象的process方法输出文件
		template.process(data, out);
		//9.关闭流
		out.close();
	}
}
