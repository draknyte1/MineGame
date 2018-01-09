package com.alkalus.config.interfaces;

import com.alkalus.config.Property.IGuiListEntry;

/**
 * Provides an interface for defining GuiPropertyList.listEntry objects.
 */
public interface IConfigEntry<T> extends IGuiListEntry {

	/**
	 * Gets the name of the ConfigElement owned by this entry.
	 */
	public String getName();

	/**
	 * Gets the current value of this entry as a String.
	 */
	public T getCurrentValue();

	/**
	 * Gets the current values of this list entry as a String[].
	 */
	public T[] getCurrentValues();

	/**
	 * Is this list entry enabled?
	 *
	 * @return true if this entry's controls should be enabled, false otherwise.
	 */
	public boolean enabled();

	/**
	 * Handles user keystrokes for any GuiTextField objects in this entry. Call {@code GuiTextField.keyTyped()} for any GuiTextField
	 * objects that should receive the input provided.
	 */
	public void keyTyped(char eventChar, int eventKey);

	/**
	 * Call {@code GuiTextField.updateCursorCounter()} for any GuiTextField objects in this entry.
	 */
	public void updateCursorCounter();

	/**
	 * Call {@code GuiTextField.mouseClicked()} for and GuiTextField objects in this entry.
	 */
	public void mouseClicked(int x, int y, int mouseEvent);

	/**
	 * Is this entry's value equal to the default value? Generally true should be returned if this entry is not a property or category
	 * entry.
	 *
	 * @return true if this entry's value is equal to this entry's default value.
	 */
	public boolean isDefault();

	/**
	 * Sets this entry's value to the default value.
	 */
	public void setToDefault();

	/**
	 * Handles reverting any changes that have occurred to this entry.
	 */
	public void undoChanges();

	/**
	 * Has the value of this entry changed?
	 *
	 * @return true if changes have been made to this entry's value, false otherwise.
	 */
	public boolean isChanged();

	/**
	 * Handles saving any changes that have been made to this entry back to the underlying object. It is a good practice to check
	 * isChanged() before performing the save action. This method should return true if the element has changed AND REQUIRES A RESTART.
	 */
	public boolean saveConfigElement();

	/**
	 * Handles drawing any tooltips that apply to this entry. This method is called after all other GUI elements have been drawn to the
	 * screen, so it could also be used to draw any GUI element that needs to be drawn after all entries have had drawEntry() called.
	 */
	public void drawToolTip(int mouseX, int mouseY);

	/**
	 * Gets this entry's label width.
	 */
	public int getLabelWidth();

	/**
	 * Gets this entry's right-hand x boundary. This value is used to control where the scroll bar is placed.
	 */
	public int getEntryRightBound();

	/**
	 * This method is called when the parent GUI is closed. Most handlers won't need this; it is provided for special cases.
	 */
	public void onGuiClosed();
}