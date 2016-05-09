package com.crossover.trial.properties.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class Logger {

		private static org.apache.log4j.Logger m_catDebug = org.apache.log4j.Logger.getLogger("PropertiesDebugLogger");
		private static org.apache.log4j.Logger m_catInfo = org.apache.log4j.Logger.getLogger("PropertiesInfoLogger");
		private static org.apache.log4j.Logger m_catError = org.apache.log4j.Logger.getLogger("PropertiesErrorLogger");
		private static String ostrDebug;
		private static String ostrInfo;
		private static String ostrError;

		public Logger() {
		}

		public static void writeDebugLog(String msg) {
			if (m_catDebug.isDebugEnabled() && ostrDebug.equals("ON"))
				m_catDebug.debug(msg);
		}

		public static void writeDebugLog(String className, String methodName,
				String Message) {
			if (ostrDebug.equals("ON")) {
				StringBuffer obuffData = new StringBuffer("  ");
				obuffData.append("[ Class : ");
				obuffData.append(className);
				obuffData.append(" ]\t[ Method : ");
				obuffData.append(methodName);
				obuffData.append(" ]\t[ Message : ");
				obuffData.append(Message);
				obuffData.append("]");
				m_catDebug.debug(obuffData.toString());
			}
		}

		public static void writeInfoLog(String msg) {
			if (ostrInfo.equals("ON"))
				m_catInfo.info(msg);
		}

		public static void writeInfoLog(String className, String methodName,
				String Message) {
			if (ostrInfo.equals("ON")) {
				StringBuffer obuffData = new StringBuffer("  ");
				obuffData.append("[ Class : ");
				obuffData.append(className);
				obuffData.append(" ]\t[ Method : ");
				obuffData.append(methodName);
				obuffData.append(" ]\t[ Message : ");
				obuffData.append(Message);
				obuffData.append("]");
				m_catInfo.debug(obuffData.toString());
			}
		}

		public static void writeErrorLog(String msg) {
			if (ostrError.equals("ON"))
				m_catError.error(msg);
		}

		static {
			Properties p = new Properties();
			PropertyConfigurator.configure(p);
			ostrDebug = "ON";
			ostrInfo = "ON";
			ostrError = "ON";
		}

		private static String getStackTrace(Throwable t) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw, true);
			t.printStackTrace(pw);
			pw.flush();
			sw.flush();
			return sw.toString();
		}

		public static void writeErrorStackTrace(Throwable e) {
			Logger.writeErrorLog(getStackTrace(e));
		}
}
