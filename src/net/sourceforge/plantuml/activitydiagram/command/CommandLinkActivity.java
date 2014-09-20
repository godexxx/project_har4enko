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
package net.sourceforge.plantuml.activitydiagram.command;

import net.sourceforge.plantuml.Direction;
import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.UrlBuilder;
import net.sourceforge.plantuml.UrlBuilder.ModeUrl;
import net.sourceforge.plantuml.activitydiagram.ActivityDiagram;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexOptional;
import net.sourceforge.plantuml.command.regex.RegexOr;
import net.sourceforge.plantuml.command.regex.RegexPartialMatch;
import net.sourceforge.plantuml.command.regex.RegexResult;
import net.sourceforge.plantuml.cucadiagram.Code;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.cucadiagram.GroupType;
import net.sourceforge.plantuml.cucadiagram.IEntity;
import net.sourceforge.plantuml.cucadiagram.LeafType;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;
import net.sourceforge.plantuml.cucadiagram.LinkType;
import net.sourceforge.plantuml.cucadiagram.Stereotype;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.utils.StringUtils;

public class CommandLinkActivity extends SingleLineCommand2<ActivityDiagram> {

	public CommandLinkActivity() {
		super(getRegexConcat());
	}

	static RegexConcat getRegexConcat() {
		return new RegexConcat(new RegexLeaf("^"), //
				new RegexOptional(//
						new RegexOr("FIRST", //
								new RegexLeaf("STAR", "(\\(\\*(top)?\\))"), //
								new RegexLeaf("CODE", "([\\p{L}0-9][\\p{L}0-9_.]*)"), //
								new RegexLeaf("BAR", "(?:==+)[%s]*([\\p{L}0-9_.]+)[%s]*(?:==+)"), //
								new RegexLeaf("QUOTED", "[%g]([^%g]+)[%g](?:[%s]+as[%s]+([\\p{L}0-9_.]+))?"))), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("STEREOTYPE", "(\\<\\<.*\\>\\>)?"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("BACKCOLOR", "(" + HtmlColorUtils.COLOR_REGEXP + ")?"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("URL", "(" + UrlBuilder.getRegexp() + ")?"), //
				new RegexLeaf("ARROW", "([-=.]+(?:\\*|left|right|up|down|le?|ri?|up?|do?)?[-=.]*\\>)"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("BRACKET", "(?:\\[([^\\]*]+[^\\]]*)\\])?"), //
				new RegexLeaf("[%s]*"), //
				new RegexOr("FIRST2", //
						new RegexLeaf("STAR2", "(\\(\\*(top)?\\))"), //
						new RegexLeaf("OPENBRACKET2", "(\\{)"), //
						new RegexLeaf("CODE2", "([\\p{L}0-9][\\p{L}0-9_.]*)"), //
						new RegexLeaf("BAR2", "(?:==+)[%s]*([\\p{L}0-9_.]+)[%s]*(?:==+)"), //
						new RegexLeaf("QUOTED2", "[%g]([^%g]+)[%g](?:[%s]+as[%s]+([\\p{L}0-9][\\p{L}0-9_.]*))?"), //
						new RegexLeaf("QUOTED_INVISIBLE2", "(\\w.*?)")), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("STEREOTYPE2", "(\\<\\<.*\\>\\>)?"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("PARTITION2", "(?:in[%s]+([%g][^%g]+[%g]|\\S+))?"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("BACKCOLOR2", "(" + HtmlColorUtils.COLOR_REGEXP + ")?"), //
				new RegexLeaf("$"));
	}

	@Override
	protected CommandExecutionResult executeArg(ActivityDiagram diagram, RegexResult arg) {
		final IEntity entity1 = getEntity(diagram, arg, true);
		if (entity1 == null) {
			return CommandExecutionResult.error("No such activity");
		}
		if (arg.get("STEREOTYPE", 0) != null) {
			entity1.setStereotype(new Stereotype(arg.get("STEREOTYPE", 0)));
		}
		if (arg.get("BACKCOLOR", 0) != null) {
			entity1.setSpecificBackcolor(diagram.getSkinParam().getIHtmlColorSet().getColorIfValid(arg.get("BACKCOLOR", 0)));
		}

		final IEntity entity2 = getEntity(diagram, arg, false);
		if (entity2 == null) {
			return CommandExecutionResult.error("No such activity");
		}
		if (arg.get("BACKCOLOR2", 0) != null) {
			entity2.setSpecificBackcolor(diagram.getSkinParam().getIHtmlColorSet().getColorIfValid(arg.get("BACKCOLOR2", 0)));
		}
		if (arg.get("STEREOTYPE2", 0) != null) {
			entity2.setStereotype(new Stereotype(arg.get("STEREOTYPE2", 0)));
		}

		final Display linkLabel = Display.getWithNewlines(arg.get("BRACKET", 0));

		final String arrow = StringUtils.manageArrowForCuca(arg.get("ARROW", 0));
		int lenght = arrow.length() - 1;
		if (arg.get("ARROW", 0).contains("*")) {
			lenght = 2;
		}

		LinkType type = new LinkType(LinkDecor.ARROW, LinkDecor.NONE);
		if (arg.get("ARROW", 0).contains(".")) {
			type = type.getDotted();
		}

		Link link = new Link(entity1, entity2, type, linkLabel, lenght);
		if (arg.get("ARROW", 0).contains("*")) {
			link.setConstraint(false);
		}
		final Direction direction = StringUtils.getArrowDirection(arg.get("ARROW", 0));
		if (direction == Direction.LEFT || direction == Direction.UP) {
			link = link.getInv();
		}
		if (arg.get("URL", 0) != null) {
			final UrlBuilder urlBuilder = new UrlBuilder(diagram.getSkinParam().getValue("topurl"), ModeUrl.STRICT);
			final Url urlLink = urlBuilder.getUrl(arg.get("URL", 0));
			link.setUrl(urlLink);
		}

		diagram.addLink(link);

		return CommandExecutionResult.ok();

	}

	static IEntity getEntity(ActivityDiagram system, RegexResult arg, final boolean start) {
		final String suf = start ? "" : "2";

		final String openBracket2 = arg.get("OPENBRACKET" + suf, 0);
		if (openBracket2 != null) {
			return system.createInnerActivity();
		}
		if (arg.get("STAR" + suf, 0) != null) {
			if (start) {
				if (arg.get("STAR" + suf, 1) != null) {
					system.getStart().setTop(true);
				}
				return system.getStart();
			}
			return system.getEnd();
		}
		String partition = arg.get("PARTITION" + suf, 0);
		if (partition != null) {
			partition = StringUtils.eventuallyRemoveStartingAndEndingDoubleQuote(partition);
		}
		final Code code = Code.of(arg.get("CODE" + suf, 0));
		if (code != null) {
			if (partition != null) {
				system.getOrCreateGroup(Code.of(partition), Display.getWithNewlines(partition), null,
						GroupType.PACKAGE, system.getRootGroup());
			}
			final IEntity result = system.getOrCreate(code, Display.getWithNewlines(code),
					getTypeIfExisting(system, code));
			if (partition != null) {
				system.endGroup();
			}
			return result;
		}
		final String bar = arg.get("BAR" + suf, 0);
		if (bar != null) {
			return system.getOrCreate(Code.of(bar), Display.getWithNewlines(bar), LeafType.SYNCHRO_BAR);
		}
		final RegexPartialMatch quoted = arg.get("QUOTED" + suf);
		if (quoted.get(0) != null) {
			final Code quotedCode = Code.of(quoted.get(1) == null ? quoted.get(0) : quoted.get(1));
			if (partition != null) {
				system.getOrCreateGroup(Code.of(partition), Display.getWithNewlines(partition), null,
						GroupType.PACKAGE, system.getRootGroup());
			}
			final IEntity result = system.getOrCreate(quotedCode, Display.getWithNewlines(quoted.get(0)),
					getTypeIfExisting(system, quotedCode));
			if (partition != null) {
				system.endGroup();
			}
			return result;
		}
		final Code quotedInvisible = Code.of(arg.get("QUOTED_INVISIBLE" + suf, 0));
		if (quotedInvisible != null) {
			if (partition != null) {
				system.getOrCreateGroup(Code.of(partition), Display.getWithNewlines(partition), null,
						GroupType.PACKAGE, system.getRootGroup());
			}
			final IEntity result = system.getOrCreate(quotedInvisible, Display.getWithNewlines(quotedInvisible),
					LeafType.ACTIVITY);
			if (partition != null) {
				system.endGroup();
			}
			return result;
		}
		final String first = arg.get("FIRST" + suf, 0);
		if (first == null) {
			return system.getLastEntityConsulted();
		}

		return null;
	}

	static LeafType getTypeIfExisting(ActivityDiagram system, Code code) {
		if (system.leafExist(code)) {
			final IEntity ent = system.getLeafsget(code);
			if (ent.getEntityType() == LeafType.BRANCH) {
				return LeafType.BRANCH;
			}
		}
		return LeafType.ACTIVITY;
	}

	static LeafType getTypeFromString(String type, final LeafType circle) {
		if (type == null) {
			return LeafType.ACTIVITY;
		}
		if (type.equals("*")) {
			return circle;
		}
		if (type.startsWith("=")) {
			return LeafType.SYNCHRO_BAR;
		}
		throw new IllegalArgumentException();
	}

}