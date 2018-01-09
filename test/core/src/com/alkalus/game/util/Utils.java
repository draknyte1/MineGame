package com.alkalus.game.util;

import java.awt.Color;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

	/**
	 *
	 * @param colourStr
	 *            e.g. "#FFFFFF"
	 * @return String - formatted "rgb(0,0,0)"
	 */
	public static String hex2RgbFormatted(final String hexString) {
		final Color c = new Color(Integer.valueOf(hexString.substring(1, 3), 16),
				Integer.valueOf(hexString.substring(3, 5), 16), Integer.valueOf(hexString.substring(5, 7), 16));

		final StringBuffer sb = new StringBuffer();
		sb.append("rgb(");
		sb.append(c.getRed());
		sb.append(",");
		sb.append(c.getGreen());
		sb.append(",");
		sb.append(c.getBlue());
		sb.append(")");
		return sb.toString();
	}

	/**
	 *
	 * @param colourStr
	 *            e.g. "#FFFFFF"
	 * @return
	 */
	public static Color hex2Rgb(final String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	/**
	 *
	 * @param colourInt
	 *            e.g. 0XFFFFFF
	 * @return Colour
	 */
	public static Color hex2Rgb(final int colourInt) {
		return Color.decode(String.valueOf(colourInt));
	}

	/**
	 *
	 * @param colourInt
	 *            e.g. 0XFFFFFF
	 * @return short[]
	 */
	public static short[] hex2RgbShort(final int colourInt) {
		final Color rgb = Color.decode(String.valueOf(colourInt));
		final short[] rgba = { (short) rgb.getRed(), (short) rgb.getGreen(), (short) rgb.getBlue(),
				(short) rgb.getAlpha() };
		return rgba;
	}

	public static String byteToHex(final byte b) {
		final int i = b & 0xFF;
		return Integer.toHexString(i);
	}

	public static Object[] convertListToArray(final List<Object> sourceList) {
		final Object[] targetArray = sourceList.toArray(new Object[sourceList.size()]);
		return targetArray;
	}

	public static List<Object> convertArrayToFixedSizeList(final Object[] sourceArray) {
		final List<Object> targetList = Arrays.asList(sourceArray);
		return targetList;
	}

	public static List<Object> convertArrayToList(final Object[] sourceArray) {
		final List<Object> targetList = new ArrayList<>(Arrays.asList(sourceArray));
		return targetList;
	}

	public static List<Object> convertArrayListToList(final ArrayList<Object> sourceArray) {
		final List<Object> targetList = new ArrayList<Object>(Arrays.asList(sourceArray));
		return targetList;
	}

	public static int rgbtoHexValue(final int r, final int g, final int b) {
		if ((r > 255) || (g > 255) || (b > 255) || (r < 0) || (g < 0) || (b < 0)) {
			return 0;
		}
		final Color c = new Color(r, g, b);
		String temp = Integer.toHexString(c.getRGB() & 0xFFFFFF).toUpperCase();

		// System.out.println( "hex: " + Integer.toHexString( c.getRGB() &
		// 0xFFFFFF ) + " hex value:"+temp);
		temp = Utils.appenedHexNotationToString(String.valueOf(temp));
		//Logger.WARNING("Made " + temp + " - Hopefully it's not a mess.");
		//Logger.WARNING("It will decode into " + Integer.decode(temp) + ".");
		return Integer.decode(temp);
	}

	/*
	 * http://javadevnotes.com/java-left-pad-string-with-zeros-examples
	 */
	public static String leftPadWithZeroes(final String originalString, final int length) {
		final StringBuilder sb = new StringBuilder();
		while ((sb.length() + originalString.length()) < length) {
			sb.append('0');
		}
		sb.append(originalString);
		final String paddedString = sb.toString();
		return paddedString;
	}

	/*
	 * Original Code by Chandana Napagoda -
	 * https://cnapagoda.blogspot.com.au/2011/03/java-hex-color-code-generator.
	 * html
	 */
	public static Map<Integer, String> hexColourGenerator(final int colorCount) {
		final int maxColorValue = 16777215;
		// this is decimal value of the "FFFFFF"
		final int devidedvalue = maxColorValue / colorCount;
		int countValue = 0;
		final HashMap<Integer, String> hexColorMap = new HashMap<>();
		for (int a = 0; (a < colorCount) && (maxColorValue >= countValue); a++) {
			if (a != 0) {
				countValue += devidedvalue;
				hexColorMap.put(a, Integer.toHexString(0x10000 | countValue).substring(1).toUpperCase());
			} else {
				hexColorMap.put(a, Integer.toHexString(0x10000 | countValue).substring(1).toUpperCase());
			}
		}
		return hexColorMap;
	}

	/*
	 * Original Code by Chandana Napagoda -
	 * https://cnapagoda.blogspot.com.au/2011/03/java-hex-color-code-generator.
	 * html
	 */
	public static Map<Integer, String> hexColourGeneratorRandom(final int colorCount) {
		final HashMap<Integer, String> hexColorMap = new HashMap<>();
		for (int a = 0; a < colorCount; a++) {
			String code = "" + (int) (Math.random() * 256);
			code = code + code + code;
			final int i = Integer.parseInt(code);
			hexColorMap.put(a, Integer.toHexString(0x1000000 | i).substring(1).toUpperCase());
			//Logger.WARNING("" + Integer.toHexString(0x1000000 | i).substring(1).toUpperCase());
		}
		return hexColorMap;
	}

	public static String appenedHexNotationToString(final Object hexAsStringOrInt) {
		final String hexChar = "0x";
		String result;
		if (hexAsStringOrInt.getClass() == String.class) {

			if (((String) hexAsStringOrInt).length() != 6) {
				final String temp = leftPadWithZeroes((String) hexAsStringOrInt, 6);
				result = temp;
			}
			result = hexChar + hexAsStringOrInt;
			return result;
		} else if (hexAsStringOrInt.getClass() == Integer.class) {
			if (((String) hexAsStringOrInt).length() != 6) {
				final String temp = leftPadWithZeroes((String) hexAsStringOrInt, 6);
				result = temp;
			}
			result = hexChar + String.valueOf(hexAsStringOrInt);
			return result;
		} else {
			return null;
		}
	}

	public static Integer appenedHexNotationToInteger(final int hexAsStringOrInt) {
		final String hexChar = "0x";
		String result;
		//Logger.WARNING(String.valueOf(hexAsStringOrInt));
		result = hexChar + String.valueOf(hexAsStringOrInt);
		return Integer.getInteger(result);
	}

	public static boolean invertBoolean(final boolean booleans) {
		if (booleans == true) {
			return false;
		}
		return true;
	}

	public static String sanitizeString(final String input) {
		String temp;
		String output;

		temp = input.replace(" ", "");
		temp = temp.replace("-", "");
		temp = temp.replace("_", "");
		temp = temp.replace("?", "");
		temp = temp.replace("!", "");
		temp = temp.replace("@", "");
		temp = temp.replace("#", "");
		temp = temp.replace("(", "");
		temp = temp.replace(")", "");
		temp = temp.replace("{", "");
		temp = temp.replace("}", "");
		temp = temp.replace("[", "");
		temp = temp.replace("]", "");
		temp = temp.replace(" ", "");
		output = temp;
		return output;

	}

	public static String sanitizeStringKeepBrackets(final String input) {
		String temp;
		String output;

		temp = input.replace(" ", "");
		temp = temp.replace("-", "");
		temp = temp.replace("_", "");
		temp = temp.replace("?", "");
		temp = temp.replace("!", "");
		temp = temp.replace("@", "");
		temp = temp.replace("#", "");
		temp = temp.replace(" ", "");
		output = temp;
		return output;

	}

	public static int calculateVoltageTier(final int Voltage) {
		int V;
		if (Voltage == 8) {
			V = 0;
		} else if (Voltage == 32) {
			V = 1;
		} else if (Voltage == 128) {
			V = 2;
		} else if (Voltage == 512) {
			V = 3;
		} else if (Voltage == 2048) {
			V = 4;
		} else if (Voltage == 8196) {
			V = 5;
		} else if (Voltage == 32768) {
			V = 6;
		} else if (Voltage == 131072) {
			V = 7;
		} else if (Voltage == 524288) {
			V = 8;
		} else if (Voltage == Integer.MAX_VALUE) {
			V = 9;
		} else {
			V = -1;
		}
		return V;
	}
	
	public static SecureRandom generateSecureRandom(){
		SecureRandom secRan;
		String secRanType;		
		
		if (SystemUtils.isWindows()){
			secRanType = "Windows-PRNG";
		}
		else {
			secRanType = "NativePRNG";
		}		
		try {
			secRan = SecureRandom.getInstance(secRanType);
			// Default constructor would have returned insecure SHA1PRNG algorithm, so make an explicit call.
			byte[] b = new byte[64] ;
			secRan.nextBytes(b);
			return secRan;
		}
		catch (NoSuchAlgorithmException e) {
			return null;
		} 
	}	
	

}
