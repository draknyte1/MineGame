package com.alkalus.game.assets.loaders;

import java.beans.XMLDecoder;
import java.io.InputStream;

import com.alkalus.game.core.engine.objects.Logger;


public class XmlDecoderDebug extends XMLDecoder {

	public XmlDecoderDebug(InputStream in) {
		super(in);
	}

	/**
	 * Reads the next object from the underlying input stream.
	 *
	 * @return the next object read
	 *
	 * @throws ArrayIndexOutOfBoundsException if the stream contains no objects
	 *         (or no more objects)
	 *
	 * @see XMLEncoder#writeObject
	 */
	@Override
	public Object readObject() {
		try {
			Logger.INFO("Trying to Read XML Object.");
			return super.readObject();
		}
		catch (Throwable t){
			return null;
		}
	}

}
