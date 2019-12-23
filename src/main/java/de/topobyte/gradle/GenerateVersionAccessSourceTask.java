// Copyright 2019 Sebastian Kuerten
//
// This file is part of gradle-version-access-plugin.
//
// gradle-version-access-plugin is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// gradle-version-access-plugin is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with gradle-version-access-plugin. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.gradle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

public class GenerateVersionAccessSourceTask extends AbstractVersionAccessTask
{

	public GenerateVersionAccessSourceTask()
	{
		setGroup("build");
	}

	@TaskAction
	protected void generateSource() throws IOException
	{
		Project project = getProject();

		String packageName = configuration.getPackageName();
		String className = configuration.getClassName();
		if (className == null) {
			className = "Version";
		}

		String version = project.getVersion().toString();

		if (packageName == null) {
			throw new IllegalStateException("Please specify 'packageName'.");
		}

		String[] parts = packageName.split("\\.");

		Path pathBuildDir = project.getBuildDir().toPath();
		Path source = Util.getSourceDir(pathBuildDir);

		Path path = source;
		for (int i = 0; i < parts.length; i++) {
			path = path.resolve(parts[i]);
		}

		Files.createDirectories(path);
		Path file = path.resolve(className + ".java");

		StringBuilder buffer = createSource(packageName, className, version);

		InputStream in = new ByteArrayInputStream(
				buffer.toString().getBytes(StandardCharsets.UTF_8));
		Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
	}

	private StringBuilder createSource(String packageName, String className,
			String version)
	{
		String nl = System.getProperty("line.separator");

		StringBuilder buffer = new StringBuilder();
		buffer.append("// this file is generated, do not edit");
		buffer.append(nl);
		buffer.append("package " + packageName + ";");
		buffer.append(nl);
		buffer.append(nl);
		buffer.append("public class " + className);
		buffer.append(nl);
		buffer.append("{");
		buffer.append(nl);
		buffer.append(nl);
		buffer.append("\tpublic static String getVersion()");
		buffer.append(nl);
		buffer.append("\t{");
		buffer.append(nl);
		buffer.append("\t\treturn \"" + version + "\";");
		buffer.append(nl);
		buffer.append("\t}");
		buffer.append(nl);
		buffer.append(nl);
		buffer.append("}");
		buffer.append(nl);

		return buffer;
	}

}
