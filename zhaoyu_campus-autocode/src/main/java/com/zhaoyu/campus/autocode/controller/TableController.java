package com.zhaoyu.campus.autocode.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.zhaoyu.campus.autocode.entity.TableEntity;
import com.zhaoyu.campus.autocode.param.TableParam;
import com.zhaoyu.campus.autocode.service.TableService;
import com.zhaoyu.campus.common.utils.ServiceResult;

import freemarker.template.Configuration;
import freemarker.template.Template;

@RestController
public class TableController {
	
	@Autowired
	TableService tableService;
	
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	
	@RequestMapping("/table/getByTableSchema")
	public ServiceResult getByTableSchema(String tableSchema){
		List<TableEntity> list = tableService.getByTableSchema(tableSchema);
		return ServiceResult.ok(list);
	}
	
	@RequestMapping("/table/getAllTableSchema")
	public ServiceResult getAllTableSchema(){
		List<TableEntity> res = tableService.getAllTableSchema();
		return ServiceResult.ok(res);
	}
	
	@RequestMapping("/table/getTableNameByTableSchema")
	public ServiceResult getTableNameByTableSchema(String tableSchema){
		List<TableEntity> res = tableService.getTableNameByTableSchema(tableSchema);
		return ServiceResult.ok(res);
	}
	
	@RequestMapping("/table/getByTableSchemaAndTableNames")
	public ServiceResult getByTableSchemaAndTableNames(String tableSchema,@RequestParam("tableNames[]") List<String> tableNames){
		List<TableEntity> list = tableService.getByTableSchemaAndTableNames(tableSchema,tableNames);
		for(TableEntity obj : list){
			obj.setColumnNameTransformed(this.stringTransform(obj.getColumnName()));
		}
		String path="D:/code";
		String packageName="com.meicai.springboot.autocode";
		this.outEntity(path,packageName,list);
		this.outBaseDao(path, packageName);
		this.outDao(path, packageName, list);
		this.outBaseMapper(path, packageName, list);
		this.outMapper(path, packageName, list);
		this.outManager(path, packageName, list);
		this.outBaseManager(path, packageName);
		this.outBaseManagerImpl(path, packageName);
		this.outManagerImpl(path, packageName, list);
		this.outController(path, packageName, list);
		return ServiceResult.ok(list);
	}
	
	@RequestMapping("/table/autoCode")
	public ServiceResult autoCode(@RequestBody TableParam param){
		String path = param.getPath();
		this.mkdir(path);
		String packageName = param.getPackageName();
		List<TableEntity> list = tableService.getByTableSchemaAndTableNames(param.getTableSchema(),param.getTableNames());
		Map<String, List<TableEntity>> map = new HashMap<>();
		for(TableEntity obj : list){
			obj.setColumnNameTransformed(this.stringTransform(obj.getColumnName()));
			if(map.containsKey(obj.getTableName())){
				map.get(obj.getTableName()).add(obj);
			}else{
				List<TableEntity> item = new ArrayList<>();
				item.add(obj);
				map.put(obj.getTableName(), item);
			}
		}
		this.outBaseDao(path, packageName);
		this.outBaseManager(path, packageName);
		this.outBaseManagerImpl(path, packageName);
		for(List<TableEntity> obj : map.values()){
			this.outEntity(path,packageName,obj);
			this.outDao(path, packageName, obj);
			this.outBaseMapper(path, packageName, obj);
			this.outMapper(path, packageName, obj);
			this.outManager(path, packageName, obj);
			this.outManagerImpl(path, packageName, obj);
			this.outController(path, packageName, obj);
		}
		
		return ServiceResult.ok();
	}
	
	private void outEntity(String path,String packageName,List<TableEntity> list){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/entity.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String name = this.stringTransform(list.get(0).getTableName());
			String dir=path+"/entity";
			name = name.substring(0, 1).toUpperCase() + name.substring(1)+"Entity";
			data.put("name", name);
			data.put("list", list);
			data.put("package", packageName);
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/"+name+".java");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void outBaseDao(String path,String packageName){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/BaseDao.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String dir=path+"/dao";
			data.put("package", packageName);
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/BaseDao.java");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void outDao(String path,String packageName,List<TableEntity> list){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/dao.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String name = this.stringTransform(list.get(0).getTableName());
			String dir=path+"/dao";
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			data.put("name", name);
			data.put("package", packageName);
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/"+name+"Dao.java");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void outBaseMapper(String path,String packageName,List<TableEntity> list){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/BaseMapper.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String name = this.stringTransform(list.get(0).getTableName());
			String dir=path+"/mapper/base";
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			data.put("name", name);
			data.put("list", list);
			data.put("package", packageName);
			data.put("tableName", list.get(0).getTableName());
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/"+name+"BaseMapper.xml");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void outMapper(String path,String packageName,List<TableEntity> list){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/Mapper.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String name = this.stringTransform(list.get(0).getTableName());
			String dir=path+"/mapper";
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			data.put("name", name);
			data.put("package", packageName);
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/"+name+"Mapper.xml");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void outManager(String path,String packageName,List<TableEntity> list){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/Manager.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String name = this.stringTransform(list.get(0).getTableName());
			String dir=path+"/manager";
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			data.put("name", name);
			data.put("package", packageName);
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/"+name+"Manager.java");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void outBaseManager(String path,String packageName){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/BaseManager.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String dir=path+"/manager";
			data.put("package", packageName);
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/BaseManager.java");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void outManagerImpl(String path,String packageName,List<TableEntity> list){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/ManagerImpl.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String name = this.stringTransform(list.get(0).getTableName());
			String dir=path+"/manager/impl";
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			data.put("name", name);
			data.put("package", packageName);
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/"+name+"ManagerImpl.java");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void outBaseManagerImpl(String path,String packageName){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/BaseManagerImpl.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String dir=path+"/manager/impl";
			data.put("package", packageName);
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/BaseManagerImpl.java");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void outController(String path,String packageName,List<TableEntity> list){
		try {
			Configuration configuration = freeMarkerConfig.getConfiguration();
			Template template = configuration.getTemplate("/autocode/Controller.html");
			//创建一个输出流，指定输出文件的目录及文件名。
			Map<String, Object> data = new HashMap<>();
			String name = this.stringTransform(list.get(0).getTableName());
			String dir=path+"/controller";
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			data.put("name", name);
			data.put("package", packageName);
			this.mkdir(dir);
			Writer out = new FileWriter(dir+"/"+name+"Controller.java");		
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String stringTransform(String filed){
		 Pattern p = Pattern.compile("_[a-z]");
		 Matcher m = p.matcher(filed);
		 StringBuffer sb = new StringBuffer();
		 while (m.find()) {
			 String firstChar =  m.group().substring(1,2);
		     m.appendReplacement(sb, firstChar.toUpperCase());
		 }
		 m.appendTail(sb);
		 return sb.toString();
	}
	
	private void mkdir(String dir){
		File file=new File(dir); 
		if  (!file.exists()  && !file.isDirectory()){ 
		    file.mkdirs();    
		}
	}
}
