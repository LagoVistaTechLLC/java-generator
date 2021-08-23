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
package com.lagovistatech.generator.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Project {
	private String dbServer;
	public String getDbServer() { return dbServer; }
	public void setDbServer(String dbServer) { this.dbServer = dbServer; }

	private String dbName;
	public String getDbName() { return dbName; }
	public void setDbName(String dbName) { this.dbName = dbName; }

	private String dbUser;
	public String getDbUser() { return dbUser; }
	public void setDbUser(String dbUser) { this.dbUser = dbUser; }

	private String dbPassword;
	public String getDbPassword() { return dbPassword; }
	public void setDbPassword(String dbPassword) { this.dbPassword = dbPassword; }

	private int dbPort;
	public int getDbPort() { return dbPort; }
	public void setDbPort(int dbPort) { this.dbPort = dbPort; }

	private String tablePrefix;
	public String getTablePrefix() { return tablePrefix; }
	public void setTablePrefix(String tablePrefix) { this.tablePrefix = tablePrefix; }

	private List<Template> templates;
	public List<Template> getTemplates() { return templates; }
	public void setTemplates(List<Template> templates) { this.templates = templates; }
	
	public void save(Path fileName) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(this);
		Files.writeString(fileName, json);
	}
	public static Project load(Path fileName) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = Files.readString(fileName);
		return mapper.readValue(json, Project.class);		
	}
}
