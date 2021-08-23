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

import java.util.Map;

public class Template {
	private Map<String, String> values;
	public Map<String, String> getValues() { return values; }
	public void setValues(Map<String, String> values) { this.values = values; }
	
	private String fileName;
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	
	private String outputFileName;
	public String getOutputFileName() { return outputFileName; }
	public void setOutputFileName(String value) { outputFileName = value; }
	
	private String outputDirectory;
	public String getOutputDirectory() { return outputDirectory; }
	public void setOutputDirectory(String outputDirectory) { this.outputDirectory = outputDirectory; }
	
	private boolean oneFilePerTable;
	public boolean isOneFilePerTable() { return oneFilePerTable; }
	public void setOneFilePerTable(boolean oneFilePerTable) { this.oneFilePerTable = oneFilePerTable; }
}
