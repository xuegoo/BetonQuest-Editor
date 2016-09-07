/**
 * BetonQuest Editor - advanced quest creating tool for BetonQuest
 * Copyright (C) 2016  Jakub "Co0sh" Sapalski
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.betoncraft.betonquest.editor.data;

import pl.betoncraft.betonquest.editor.model.Condition;
import pl.betoncraft.betonquest.editor.model.QuestPackage;

/**
 * Wraps a condition object so it can be negated independently of the original.
 *
 * @author Jakub Sapalski
 */
public class ConditionWrapper extends IdWrapper<Condition> {
	
	private boolean negated = false;

	public ConditionWrapper(QuestPackage pack, Condition object) {
		super(pack, object);
	}
	
	/**
	 * @return current negated state of this condition
	 */
	public boolean getNegated() {
		return negated;
	}
	
	/**
	 * @param negated new negated state of this condition
	 */
	public void setNegated(boolean negated) {
		this.negated = negated;
	}
	
	/**
	 * Returns relative name of this condition, prefixed with negation character if needed.
	 */
	public String getRelativeNegatedName(QuestPackage pack) {
		return (negated ? "!" : "") + getRelativeName(pack);
	}

}
