/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2014, Arnaud Roques
 *
 * Project Info:  http://plantuml.sourceforge.net
 * 
 * This file is part of PlantUML.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Original Author:  Arnaud Roques
 */
package net.sourceforge.plantuml.cucadiagram.dot;

import java.io.File;

class GraphvizLinux extends AbstractGraphviz {

	GraphvizLinux(String dotString, String... type) {
		super(dotString, type);
	}

	@Override
	protected File specificDotExe() {
		final File usrLocalBinDot = new File("/usr/local/bin/dot");

		if (usrLocalBinDot.exists()) {
			return usrLocalBinDot;
		}
		final File usrBinDot = new File("/usr/bin/dot");
		return usrBinDot;
	}

}