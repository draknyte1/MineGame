package com.alkalus.game.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.alkalus.game.core.Constants;
import com.alkalus.game.core.engine.objects.Logger;
import com.alkalus.game.util.reflect.ReflectionUtils;

public class BenchmarkUtils {

	public static long pollMethod(LocalTime instant) {	
		return pollMethod(instant, null);
	}

	public static long pollMethod(LocalTime instant, Object or) {
		if (Constants.BENCHMARKING){
			LocalDate today = LocalDate.now();
			String startTimeStrT = today + " " + instant.toString();
			String endTimeStrT = today + " " + LocalTime.now();

			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm:ss");

			try {	 
				LocalDateTime startTime = LocalDateTime.parse(startTimeStrT,
						formatter);
				LocalDateTime endTime = LocalDateTime.parse(endTimeStrT, formatter);	 
				Duration d = Duration.between(startTime, endTime);
				Logger.INFO("It took "+(or == null ? ReflectionUtils.getMethodName(0) : or.toString())+" "+d.getSeconds()+"s.");
				return d.getSeconds();

			} catch (DateTimeParseException e) {
				System.out.println("Invalid Input" + e.getMessage());
				return -1;
			}	
		}
		return 0;
	}


}
