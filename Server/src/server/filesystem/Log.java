package server.filesystem;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class Log {

	private static volatile Log global = null;
	private static final String globalFileName = "global.log";
	private static final String logDir = "logs";
	
	private String fileName;
	private FileWriter out;
	private String eol;
	
	public Log(String fileName)
	{
		eol = System.getProperty("line.separator");
		this.fileName = fileName.endsWith(".log") ? fileName : fileName+".log";
		File f = new File (logDir);
		if (!f.exists() || !f.isDirectory())
			f.mkdir();
		try
		{
			this.out = new FileWriter(logDir + "/" + this.fileName);
		}
		catch(Exception e)
		{
			synchronized(global)
			{
				if (global == null)
					System.err.println("Unable to start logging: "+e);
				else
					global.log("Unable to start logging for "+ fileName + ": " + e);
			}
		}
	}
	
	public static synchronized Log getGlobal()
	{
		if (global == null)
			global = new Log(globalFileName);
		return global;
	}
	
	private void log(String str)
	{
		Calendar calc = Calendar.getInstance();
		try
		{
			this.out.write(calc.get(Calendar.DAY_OF_MONTH) + "-" + calc.get(Calendar.MONTH) + 1 + "-" + calc.get(Calendar.YEAR)
					+ " " + calc.get(Calendar.HOUR_OF_DAY) + ":" + calc.get(Calendar.MINUTE) + ":" + calc.get(Calendar.SECOND)
					+ "\t" + str + eol);
			out.flush();
		}
		catch(Exception e){}
	}
	
	public void error(String str)
	{
		log("ERROR: " + str);
	}
	
	public void event(String str)
	{
		log("EVENT: " + str);
	}
}
