/**
 * BetonQuest Editor - advanced quest creating tool for BetonQuest
 * Copyright (C) 2015  Jakub "Co0sh" Sapalski
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

import javafx.beans.property.StringProperty;
import pl.betoncraft.betonquest.editor.model.TranslatableText;

public interface Translatable extends Validatable {
	
	/**
	 * @return the TranslatableText in this object.
	 */
	public TranslatableText getText();
	
	/**
	 * @return translated name of this object, ready to display to the user
	 */
	public String getType();
	
	/** 
	 * Translatable object is valid if it has text in package's default language.
	 */
	public default boolean isValid() {
		StringProperty text = getText().get();
		return text != null && text.get() != null && !text.get().isEmpty();
	}

}
