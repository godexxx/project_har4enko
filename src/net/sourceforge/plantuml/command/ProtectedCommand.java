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
package net.sourceforge.plantuml.command;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import net.sourceforge.plantuml.Log;
import net.sourceforge.plantuml.core.Diagram;
import net.sourceforge.plantuml.version.Version;

public class ProtectedCommand<S extends Diagram> implements Command<S> {

	private final Command<S> cmd;

	public ProtectedCommand(Command<S> cmd) {
		this.cmd = cmd;
	}

	public CommandExecutionResult execute(S system, List<String> lines) {
		try {
			final CommandExecutionResult result = cmd.execute(system, lines);
//			if (result.isOk()) {
//				// TRACECOMMAND
//				System.err.println("CMD = " + cmd.getClass());
//			}
			return result;
		} catch (Throwable t) {
			t.printStackTrace();
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final PrintWriter pw = new PrintWriter(baos);
			t.printStackTrace(pw);
			Log.error("Error " + t);
			String msg = "You should send a mail to plantuml@gmail.com with this log (V" + Version.versionString()
					+ ")";
			Log.error(msg);
			msg += " " + new String(baos.toByteArray());
			return CommandExecutionResult.error(msg);
		}
	}

	public CommandControl isValid(List<String> lines) {
		return cmd.isValid(lines);
	}

	public String[] getDescription() {
		return cmd.getDescription();
	}

}