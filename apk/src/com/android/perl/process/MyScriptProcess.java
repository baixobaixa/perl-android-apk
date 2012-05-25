package com.android.perl.process;

import com.android.perl.config.GlobalConstants;
import com.googlecode.android_scripting.AndroidProxy;
import com.googlecode.android_scripting.interpreter.InterpreterConfiguration;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MyScriptProcess extends PerlScriptProcess {

	  private String workingDirectory;
	  private String sdcardPackageDirectory;

	  private MyScriptProcess(File paramFile, InterpreterConfiguration paramInterpreterConfiguration, AndroidProxy paramAndroidProxy, String workingDir, String sdcardPackageDirectory) {
	    super(paramFile, paramInterpreterConfiguration, paramAndroidProxy);
	    this.workingDirectory = workingDir;
	    this.sdcardPackageDirectory = sdcardPackageDirectory;
	  }

	  public static MyScriptProcess launchScript(File script, InterpreterConfiguration configuration, final AndroidProxy proxy, Runnable shutdownHook, String workingDir, String sdcardPackageDirectory, List<String> args, Map<String, String> envVars, File binary) {
	    if (!script.exists()) {
	        throw new RuntimeException("No such script to launch.");
	      }

	    MyScriptProcess localScriptProcess = new MyScriptProcess(script, configuration, proxy, workingDir, sdcardPackageDirectory);
    	localScriptProcess.putAllEnvironmentVariables(envVars); // set our perl env
    	localScriptProcess.setBinary(binary);
	    
	    if (shutdownHook == null) {
	    	localScriptProcess.start(new Runnable() {
	          @Override
	          public void run() {
	            proxy.shutdown();
	          }
	        }, args);
	      } else {
	    	  localScriptProcess.start(shutdownHook, args);
	      }
	      return localScriptProcess;
	    }
	    
	  @Override
	  public String getWorkingDirectory() {
	    return workingDirectory;
	  }

	  @Override
	  public String getSdcardPackageDirectory() {
	    return sdcardPackageDirectory;
	  }
	  
	  
}