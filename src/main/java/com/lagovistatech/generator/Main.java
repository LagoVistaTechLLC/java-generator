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
import java.util.HashMap;
import java.util.LinkedList;

import com.lagovistatech.generator.config.Project;
import com.lagovistatech.generator.config.Template;

public class Main {
	public static void main(String[] args) {
		try {
			System.out.println("Postgres Database First Generator");
			System.out.println("(c) Lago Vista Technologies 2021");
			
			Path configFile = Paths.get("config.json");
			
			if(!Files.exists(configFile)) {
				System.out.println("No config file found - creating.");
				createConfig(configFile);
				System.out.println("The file '" + configFile.toString() + "' has been created.");
				return;
			}
			
			boolean showSets = true;
			for(String parm : args)
				if(parm.equals("no-sets"))
					showSets = false;
			
			Project prj = Project.load(configFile);
			Generator gen = new Generator(prj, showSets);
			gen.execute();
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}

	private static void createConfig(Path configFile) throws Exception {
		Project prj = new Project();
		prj.setTablePrefix(null);
		prj.setDbServer("localhost");
		prj.setDbName("lagovistatech");
		prj.setDbUser("postgres");
		prj.setDbPassword("Welcome123");
		prj.setDbPort(5432);

		prj.setTemplates(new LinkedList<Template>());
		
		Template temp = null;
	
		temp = new Template();
		temp.setFileName("entity.java");
		temp.setOneFilePerTable(true);
		temp.setOutputDirectory("target/generated");
		temp.setValues(new HashMap<String, String>());
		temp.getValues().put("package", "com.lagovistatech.authentication.service");
		
		prj.getTemplates().add(temp);
		
		prj.save(configFile);
	}
}
