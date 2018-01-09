package com.alkalus.game.core.engine.objects;

public class Logger {

	// Logging Functions
	//public static final org.apache.logging.log4j.Logger modLogger = Logger.makeLogger();

	// Generate GT++ Logger
	/*public static org.apache.logging.log4j.Logger makeLogger() {
		final org.apache.logging.log4j.Logger gtPlusPlusLogger = LogManager.getLogger("MineGame");
		return gtPlusPlusLogger;
	}*/

	/*public static final org.apache.logging.log4j.Logger getLogger(){
		return modLogger;
	}*/

	// Non-Dev Comments
	public static void INFO(final String s) {
		System.out.println(s);
	}



	// Developer Comments
	public static void WARNING(final String s) {
		//if (CORE.DEBUG) {
		System.out.println(s);
		//}
	}

	// Errors
	public static void ERROR(final String s) {
		//if (CORE.DEBUG) {
		System.out.println(s);
		//}
	}	

	/**
	 * Special Logger for reflection related content
	 */
	public static void REFLECTION(final String s) {
		System.out.println("[Reflection] "+s);
	}


}
