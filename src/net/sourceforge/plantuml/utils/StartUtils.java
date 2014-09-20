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
package net.sourceforge.plantuml.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.plantuml.command.regex.MyPattern;

public class StartUtils {

	public static boolean isArobaseStartDiagram(String s) {
		s = s.trim();
		return s.startsWith("@start");
	}

	public static boolean isArobaseEndDiagram(String s) {
		s = s.trim();
		return s.startsWith("@end");
	}

	public static boolean isArobasePauseDiagram(String s) {
		s = s.trim();
		return s.startsWith("@pause");
	}

	public static boolean isArobaseUnpauseDiagram(String s) {
		s = s.trim();
		return s.startsWith("@unpause");
	}

	private static final Pattern append = MyPattern.cmpile("^\\W*@append");

	public static String getPossibleAppend(String s) {
		final Matcher m = append.matcher(s);
		if (m.find()) {
			return s.substring(m.group(0).length()).trim();
		}
		return null;
	}

}