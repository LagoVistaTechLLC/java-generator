/*

	Copyright (C) 2021 Lago Vista Technologies LLC

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
	
*/
package com.lagovistatech.generator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.lagovistatech.Guid;
import com.lagovistatech.Naming;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.ConnectionFactory;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.StatefulRow;
import com.lagovistatech.database.StatefulRowFactory;
import com.lagovistatech.database.Table;
import com.lagovistatech.database.internal.PostgresAdapter;
import com.lagovistatech.generator.config.Project;
import com.lagovistatech.generator.config.Template;
import com.lagovistatech.template.Document;
import com.lagovistatech.template.DocumentFactory;
import com.lagovistatech.template.SyntaxFactory;
import com.lagovistatech.template.SyntaxFactory.Styles;

public class Generator {
	private Project project;
	private Connection conn;
	private Map<String, Document> templates;
	private boolean showSets = false;
	
	public Generator(Project prj, boolean showSets) {
		project = prj;
		this.showSets = showSets;
	}

	public void execute() throws Exception {
		conn = ConnectionFactory.instanciate();
		conn.setServer(project.getDbServer());
		conn.setPort(project.getDbPort());
		conn.setDatabase(project.getDbName());
		conn.setUser(project.getDbUser());
		conn.setPassword(project.getDbPassword());
		conn.open();
		
		String sql = new String(this.getClass().getResourceAsStream("/tables.sql").readAllBytes());
		Table<StatefulRow> tables = conn.fill(StatefulRowFactory.instance, sql);
		for(StatefulRow tableRow : tables)
			processTable(tableRow);
		
		parseTemplates(null);
	}

	private void processTable(StatefulRow tableRow) throws Exception {
		loadTemplates();

		System.out.println("Table: " + tableRow.get("table_name"));
				
		for(String column : tableRow.getTable().getSchema().keySet())
			callSet(column, tableRow.get(column).toString());

		Table<StatefulRow> metadataTable = processColumns(tableRow);
		processUniques(tableRow, metadataTable);				
		processFkChildren(tableRow);
		processFkParents(tableRow);		
				
		callTouch("table");
		callParse("table");	
		
		parseTemplates(tableRow.get("table_name").toString());
	}
	private void processFkParents(StatefulRow row) throws Exception {
		String sql = new String(this.getClass().getResourceAsStream("/fk.sql").readAllBytes());
		sql = sql + " WHERE child_table=@Table";
		Parameters params = new Parameters();
		params.put("@Table", row.get("table_name"));
		Table<StatefulRow> parents = conn.fill(StatefulRowFactory.instance, sql, params);
		for(StatefulRow parent : parents) {
			System.out.println("\tParent Relationship: " + parent.get("foreign_key").toString());
			System.out.println("\t	Column: " + parent.get("child_column").toString());
			System.out.println("\t	Parent Table: " + parent.get("parent_table").toString());
			System.out.println("\t	Parent Column: " + parent.get("parent_column").toString());
			
			for(String column : parents.getSchema().keySet())
				if(parent.get(column).toString().contains("<")) {
					callSet("parent_" + column, parent.get(column).toString().replace("<", "One To Many"));
					String[] parts = parent.get(column).toString().split(Pattern.quote("<"));
					callSet("relation_one", parts[0].trim());
					callSet("relation_many", parts[1].trim());
				} else if(parent.get(column).toString().contains(">")) {
					callSet("parent_" + column, parent.get(column).toString().replace(">", "Many To One"));
					String[] parts = parent.get(column).toString().split(Pattern.quote(">"));
					callSet("relation_many", parts[0].trim());
					callSet("relation_one", parts[1].trim());
				} else
					callSet("parent_" + column, parent.get(column).toString());
						
			callTouch("parents");
			callParse("parents");
		}
	}
	private void processFkChildren(StatefulRow row) throws Exception {
		String sql = new String(this.getClass().getResourceAsStream("/fk.sql").readAllBytes());
		sql = sql + " WHERE parent_table=@Table";
		Parameters params = new Parameters();
		params.put("@Table", row.get("table_name"));
		Table<StatefulRow> children = conn.fill(StatefulRowFactory.instance, sql, params);
		for(StatefulRow child : children) {
			System.out.println("\tChild Relationship: " + child.get("foreign_key").toString());
			System.out.println("\t	Column: " + child.get("parent_column").toString());
			System.out.println("\t	Child Table: " + child.get("child_table").toString());
			System.out.println("\t	Child Column: " + child.get("child_column").toString());			
	
			for(String column : children.getSchema().keySet())
				if(child.get(column).toString().contains("<")) {
					callSet("child_" + column, child.get(column).toString().replace("<", "One To Many"));
					String[] parts = child.get(column).toString().split(Pattern.quote("<"));
					callSet("relation_one", parts[0].trim());
					callSet("relation_many", parts[1].trim());
				} else if(child.get(column).toString().contains(">")) {
					callSet("child_" + column, child.get(column).toString().replace(">", "Many To One"));
					String[] parts = child.get(column).toString().split(Pattern.quote(">"));
					callSet("relation_many", parts[0].trim());
					callSet("relation_one", parts[1].trim());
				} else
					callSet("child_" + column, child.get(column).toString());
			
			callTouch("children");
			callParse("children");
		}
	}
	private void processUniques(StatefulRow row, Table<StatefulRow> metadataTable) throws Exception {
		String sql = new String(this.getClass().getResourceAsStream("/unique.sql").readAllBytes());
		Parameters params = new Parameters();
		params.put("@Table", row.get("table_name"));
		Table<StatefulRow> uniques = conn.fill(StatefulRowFactory.instance, sql, params);
		for(StatefulRow unique : uniques) {
			System.out.println("\tUnique: " + unique.get("column_name").toString() + " - " + unique.get("constraint_type").toString());

			for(String column : uniques.getSchema().keySet())
				callSet("unique_" + column, unique.get(column).toString());
			
			String column_name = unique.get("column_name").toString();
			String java_type = PostgresAdapter.sqlToJavaMap.get(metadataTable.getSchema().get(column_name));
			callSet("unique_java_type", java_type);
			
			callTouch("uniques");
			callParse("uniques");
		}
	}
	private Table<StatefulRow> processColumns(StatefulRow row) throws Exception {
		String sql = "SELECT * FROM " + conn.getAdapter().quoteIdentifier(row.get("table_name").toString()) + " WHERE 1<>1";
		Table<StatefulRow> metadata = conn.fill(StatefulRowFactory.instance, sql);
		for(String column : metadata.getSchema().keySet()) {
			System.out.println("\tColumn: " + column);
			System.out.println("\t\tDB Type: " + metadata.getSchema().get(column));
			System.out.println("\t\tJava Type: " + PostgresAdapter.sqlToJavaMap.get(metadata.getSchema().get(column)));

			callSet("column_name", column);
			callSet("column_db_type", metadata.getSchema().get(column));
			callSet("column_java_type", PostgresAdapter.sqlToJavaMap.get(metadata.getSchema().get(column)));
			
			callTouch("columns");
			callParse("columns");
		}
		
		return metadata;
	}

	private void loadTemplates() throws Exception {
		if(templates == null)
			templates = new HashMap<String, Document>();
		
		for(Template temp : project.getTemplates()) {
			if(!templates.containsKey(temp.getFileName())) {
				loadTemplate(temp);
			}
		}
	}
	private void loadTemplate(Template temp) throws Exception {
		Document doc = DocumentFactory.instanciate();
		Path filePath = Paths.get("templates", temp.getFileName());
		doc.load(filePath, SyntaxFactory.Instantiate(Styles.C));
		templates.put(temp.getFileName(), doc);
		
		for(String key : temp.getValues().keySet()) {
			if(this.showSets)
				System.out.println("\t\tSetting");
			
			printAndSet(doc, key, temp.getValues().get(key));
		}
	}
	
	private void callSet(String name, String value) throws NoSuchAlgorithmException {
		boolean bShow = this.showSets;
		
		for(Document doc : templates.values()) {
			String singular = Naming.toSingular(value);
			String plural = Naming.toPlural(value);
			
			if(this.showSets)
				System.out.println("\t\tSetting: Name = '" + name + "'; Value = '" + value +"'");
			
			printAndSet(doc, name + "_md5", Guid.computeMd5String(value));
		
			printAndSet(doc, name, value);
			printAndSet(doc, name + "_camel", Naming.toCamelCase(value));
			printAndSet(doc, name + "_constant", Naming.toConstant(value));
			printAndSet(doc, name + "_lower_camel", Naming.toLowerCamel(value));
			printAndSet(doc, name + "_lower", Naming.toLowerCase(value));

			printAndSet(doc, name + "_camel_singular", Naming.toCamelCase(singular));
			printAndSet(doc, name + "_constant_singular", Naming.toConstant(singular));
			printAndSet(doc, name + "_lower_camel_singular", Naming.toLowerCamel(singular));
			printAndSet(doc, name + "_lower_singular", Naming.toLowerCase(singular));

			printAndSet(doc, name + "_camel_plural", Naming.toCamelCase(plural));
			printAndSet(doc, name + "_constant_plural", Naming.toConstant(plural));
			printAndSet(doc, name + "_lower_camel_plural", Naming.toLowerCamel(plural));
			printAndSet(doc, name + "_lower_plural", Naming.toLowerCase(plural));
			
			this.showSets = false;
		}
		
		this.showSets = bShow;
	}
	private void printAndSet(Document doc, String name, String value) {
		if(this.showSets)
			System.out.println("\t\t\t__" + name + "__ -> " + value);
		
		doc.set(name, value);
	}
	private void callTouch(String blockName) {
		for(Document doc : templates.values())
			doc.touch(blockName);
	}
	private void callParse(String blockName) throws Exception {
		for(Document doc : templates.values())
			doc.parse(blockName);
	}
	
	private void parseTemplates(String tableName) throws Exception {
		if(tableName == null || tableName.length() < 1)
			return;
		
		for(Template temp : project.getTemplates()) {
			if(temp.isOneFilePerTable() || tableName == null) {
				if(tableName == null)
					tableName = "";
				
				String singular = Naming.toSingular(tableName);
				String plural = Naming.toPlural(tableName);
				
				String fileName = temp.getOutputFileName();
			
				fileName = fileName.replace("__table_name_camel__", Naming.toCamelCase(tableName));
				fileName = fileName.replace("__table_name_constant__", Naming.toConstant(tableName));
				fileName = fileName.replace("__table_name_lower_camel__", Naming.toLowerCamel(tableName));
				fileName = fileName.replace("__table_name_lower__", Naming.toLowerCase(tableName));

				fileName = fileName.replace("__table_name_camel_singular__", Naming.toCamelCase(singular));
				fileName = fileName.replace("__table_name_constant_singular__", Naming.toConstant(singular));
				fileName = fileName.replace("__table_name_lower_camel_singular__", Naming.toLowerCamel(singular));
				fileName = fileName.replace("__table_name_lower_singular__", Naming.toLowerCase(singular));

				fileName = fileName.replace("__table_name_camel_plural__", Naming.toCamelCase(plural));
				fileName = fileName.replace("__table_name_constant_plural__", Naming.toConstant(plural));
				fileName = fileName.replace("__table_name_lower_camel_plural__", Naming.toLowerCamel(plural));
				fileName = fileName.replace("__table_name_lower_plural__", Naming.toLowerCase(plural));

				fileName = fileName.replace("__table_name__", tableName);
	
				Path outputDirPath = Paths.get(temp.getOutputDirectory()); 
				if(!Files.exists(outputDirPath))
					Files.createDirectories(outputDirPath);
				
				Path filePath = Paths.get(outputDirPath.toString(), fileName);
				if(Files.exists(filePath))
					Files.delete(filePath);
				
				Document doc = templates.get(temp.getFileName());
				String contents = doc.generate();
				Files.writeString(filePath, contents);
				
				loadTemplate(temp);			
			}
		}
	}
}
