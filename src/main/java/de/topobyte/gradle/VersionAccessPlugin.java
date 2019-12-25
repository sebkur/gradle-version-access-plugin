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

import java.nio.file.Path;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;

public class VersionAccessPlugin implements Plugin<Project>
{

	@Override
	public void apply(final Project project)
	{
		Logger logger = project.getLogger();
		logger.info("applying version access plugin");

		if (!project.getPlugins().hasPlugin(JavaPlugin.class)
				&& !project.getPlugins().hasPlugin(WarPlugin.class)) {
			logger.error("Please enable java or war plugin.");
			throw new IllegalStateException("No java or war plugin detected.");
		}

		VersionAccessPluginExtension extension = project.getExtensions().create(
				"generateVersionAccessSource",
				VersionAccessPluginExtension.class);

		GenerateVersionAccessSourceTask task = project.getTasks().create(
				"generateVersionAccessSource",
				GenerateVersionAccessSourceTask.class);
		task.setConfiguration(extension);

		project.getTasks().findByName("compileJava").dependsOn(task);

		if (project.getPlugins().hasPlugin(EclipsePlugin.class)) {
			project.getTasks().findByName("eclipse").dependsOn(task);
			project.getTasks().findByName("eclipseClasspath").dependsOn(task);
			project.getTasks().findByName("eclipseProject").dependsOn(task);
		}

		Path pathBuildDir = project.getBuildDir().toPath();
		Path source = Util.getSourceDir(pathBuildDir);

		SourceSet sourceSets = project.getConvention()
				.getPlugin(JavaPluginConvention.class).getSourceSets()
				.findByName("main");
		sourceSets.java(sourceSet -> {
			sourceSet.srcDir(source);
		});
	}

}
