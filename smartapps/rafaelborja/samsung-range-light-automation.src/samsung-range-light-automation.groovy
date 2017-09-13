/**
 *  Range Lights/Fan automation based on Samsung connected ranges event triggers.
 *
 *  Copyright 2017 Rafael Borja
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Samsung Range Light Automation",
    namespace: "rafaelborja",
    author: "Rafael Borja",
    description: "Lights automation based on Samsung range events",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
    section("Select your range") {
        input "therange", "capability.setOperationReady", required: true
    }
    section("Turn on the following lights light") {
        input "theswitch", "capability.switch", required: true
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	
    // For dev debugin only. Show range capabilities
    def myRangeCaps = therange.capabilities

	// log each capability supported by the "mySwitch" device, along
	// with all its supported attributes
	myRangeCaps.each {cap ->
    	log.debug "Capability name: ${cap.name}"
        cap.attributes.each {attr ->
        	log.debug "-- Attribute name; ${attr.name}"
        }
        cap.commands.each {comm ->
        	log.debug "-- Command name: ${comm.name}"
        }
    }
     
    // For dev debugin only. Show range events
    def myRangeEvents = therange.events()
    myRangeCaps.each {event ->
    	log.debug "Event name: ${event.name}"
        event.attributes.each {attr ->
        	log.debug "-- Attribute name; ${attr.name}"
        }
	}
    
    def rangeCommands = therange.supportedCommands
    rangeCommands.each {com ->
    	log.debug "Supported Command: ${com.name}"
	}
    
	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(therange, "operationStateCooktop.Run", cooktopDetectedHandler)
    
    def rangeCommands = therange.supportedCommands
	log.debug "therange: $rangeCommands"

	// Iterate through the supported capabilities, log all suported commands:
	// commands property available via the Capability object
	def caps = therange.capabilities
	caps.commands.each {comm ->
	    log.debug "-- Command name: ${comm.name}"
	}
}

// TODO: implement event handlers

def cooktopDetectedHandler (evt) {
    log.debug "cooktopDetectedHandler called: $evt"

	// TODO: implement event handlers
	theswitch.on()
}