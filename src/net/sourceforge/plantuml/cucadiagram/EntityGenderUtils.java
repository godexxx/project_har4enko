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
package net.sourceforge.plantuml.cucadiagram;

public class EntityGenderUtils {

	static public EntityGender byEntityType(final LeafType type) {
		return new EntityGender() {
			public boolean contains(IEntity test) {
				return test.getEntityType() == type;
			}
		};
	}

	static public EntityGender byEntityAlone(final IEntity entity) {
		return new EntityGender() {
			public boolean contains(IEntity test) {
				return test.getUid().equals(entity.getUid());
			}
		};
	}

	static public EntityGender byStereotype(final String stereotype) {
		return new EntityGender() {
			public boolean contains(IEntity test) {
				if (test.getStereotype() == null) {
					return false;
				}
				return stereotype.equals(test.getStereotype().getLabel());
			}
		};
	}

	static public EntityGender byPackage(final IGroup group) {
		if (EntityUtils.groupRoot(group)) {
			throw new IllegalArgumentException();
		}
		return new EntityGender() {
			public boolean contains(IEntity test) {
				if (EntityUtils.groupRoot(test.getParentContainer())) {
					return false;
				}
				if (group == test.getParentContainer()) {
					return true;
				}
				return false;
			}
		};
	}

	static public EntityGender and(final EntityGender g1, final EntityGender g2) {
		return new EntityGender() {
			public boolean contains(IEntity test) {
				return g1.contains(test) && g2.contains(test);
			}
		};
	}


	static public EntityGender all() {
		return new EntityGender() {
			public boolean contains(IEntity test) {
				return true;
			}
		};
	}

	static public EntityGender emptyMethods() {
		return new EntityGender() {
			public boolean contains(IEntity test) {
				return test.getMethodsToDisplay().size()==0;
			}
		};
	}

	static public EntityGender emptyFields() {
		return new EntityGender() {
			public boolean contains(IEntity test) {
				return test.getFieldsToDisplay().size()==0;
			}
		};
	}

	static public EntityGender emptyMembers() {
		return new EntityGender() {
			public boolean contains(IEntity test) {
				return test.getMethodsToDisplay().size()==0 && test.getFieldsToDisplay().size()==0;
			}
		};
	}

}