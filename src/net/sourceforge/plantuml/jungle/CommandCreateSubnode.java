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
package net.sourceforge.plantuml.jungle;

import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexResult;
import net.sourceforge.plantuml.cucadiagram.Display;

public class CommandCreateSubnode extends SingleLineCommand2<PSystemTree> {

	public CommandCreateSubnode() {
		super(getRegexConcat());
	}

	static RegexConcat getRegexConcat() {
		return new RegexConcat(new RegexLeaf("^"), //
				new RegexLeaf("subnode"), //
				new RegexLeaf("[%s]+"), //
				new RegexLeaf("CHILD", "([\\p{L}0-9_.@]+)"), //
				new RegexLeaf("[%s]+"), //
				new RegexLeaf("of"), //
				new RegexLeaf("[%s]+"), //
				new RegexLeaf("PARENT", "([\\p{L}0-9_.@]+)"), //
				new RegexLeaf("$"));
	}

	@Override
	protected CommandExecutionResult executeArg(PSystemTree diagram, RegexResult arg) {
		final String child = arg.get("CHILD", 0);
		final String parent = arg.get("PARENT", 0);
		final GNode parentNode = diagram.getNode(parent);
		parentNode.addChild(child, Display.create(child));
		return CommandExecutionResult.ok();
	}

}