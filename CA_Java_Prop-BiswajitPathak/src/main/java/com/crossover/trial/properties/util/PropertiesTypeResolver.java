package com.crossover.trial.properties.util;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class PropertiesTypeResolver {

	public static Object getType(String findType) {
		//could be implemented as COR DESIGN pattern, but since we set our own set of Types doing it 
		findType = findType.trim();
		return checkIfAmazonRegion(findType);
	}

	public static Object checkIfAmazonRegion(String findType) {
		Region s3Region = null;
		try {
			s3Region = Region.getRegion(Regions.valueOf(findType.toUpperCase()));
		}catch(IllegalArgumentException arg) {
			try{
				s3Region = Region.getRegion(Regions.valueOf(findType.toUpperCase().replaceAll("-", "_")));
				return s3Region;
			} catch (IllegalArgumentException i) {
				return checkIfDouble(findType);
			}
			//return checkIfDouble(findType);
		}
		return s3Region;
	}

	public static Object checkIfBoolean(String findType) {
		Boolean binaryFlag = null;
		try {
			if("true".equalsIgnoreCase(findType) || "false".equalsIgnoreCase(findType)) {
				return Boolean.valueOf(findType);
			} else {
				return checkIfLong(findType);
			}
		} catch (Exception ex) {
			return checkIfLong(findType);
		}
	}

	public static Object checkIfDouble(String findType) {
		if(!findType.contains(".")) return checkIfBoolean(findType);
		Double doubleObj = null;
		try { 
			doubleObj = Double.parseDouble(findType);
		} catch (Exception ex) {
			return checkIfBoolean(findType);
		}
		return doubleObj;
	}

	public static Object checkIfLong(String findType) {
		Long longNum = null;
		
		try{
			longNum = Long.parseLong(findType);
		} catch (Exception ex) {
			return findType;
		}
		return longNum;
	}

	public static void main(String[] args) {
		//checkIfAmazonRegion("US_wEST_1");
		//String toTest = "us_east_1";
		//String toTest = "0.9";
		//String toTest = "98";
		//String toTest = "98..";
		String toTest = "Us-West_1";
		System.out.println(getType(toTest).getClass());
	}
}