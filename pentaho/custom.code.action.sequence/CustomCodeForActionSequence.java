package com.github.mfgaspar.pentaho.xaction;

import org.pentaho.platform.api.engine.IAcceptsRuntimeInputs;
import org.pentaho.platform.api.engine.IActionSequenceResource;
import org.pentaho.platform.api.engine.IProducesRuntimeOutputs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

// This small sample accepts one name to whom we are sending back a message saying Hello 
@SuppressWarnings( { "all" } )
public class CustomCodeForActionSequence implements IAcceptsRuntimeInputs, IProducesRuntimeOutputs {

	private static final Log logger = LogFactory.getLog( CustomCodeForActionSequence.class );
	
	private Map<String, Object> inputs = new HashMap<String, Object>();
	private Map<String, Object> outputs = new HashMap<String, Object>();
	private Map<String, IActionSequenceResource> resources;

	// Here you need to write the custom code you need to execute
	public boolean execute() {
		// Get the name from the inputs 
		String username = (String) inputs.get( "name" );
		// Prepend "Hello, " to the name 
		String outputMessage =  "Hello, " + username;
		// Set the hello message in the outputs to be returned to the xaction 
		this.outputs.put("output", outputMessage);
		// Log the message to the console, don't forget to include the class and/or change the log level
		logger.error(outputMessage);
		// Return the status of execution  
		return true;
	}

	// Set the resources set in the xaction
	public void setResources( Map<String, IActionSequenceResource> resources) {
		this.resources = resources;
	 }
	
	// Set the inputs coming from the xaction  
	public void setInputs( Map<String, Object> inputs ) {
		this.inputs = inputs;
	}

	// Return outputs to the xaction
	public Map<String, Object> getOutputs() {
		return outputs;
	}

}
