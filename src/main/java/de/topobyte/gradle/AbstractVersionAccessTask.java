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

import org.gradle.api.internal.ConventionTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Nested;

public abstract class AbstractVersionAccessTask extends ConventionTask
{

	protected final Logger logger = getLogger();

	@Nested
	protected VersionAccessPluginExtension configuration;

	public VersionAccessPluginExtension getConfiguration()
	{
		return configuration;
	}

	public void setConfiguration(VersionAccessPluginExtension configuration)
	{
		this.configuration = configuration;
	}

}