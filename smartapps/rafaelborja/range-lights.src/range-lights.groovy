/**
 *  Range Lights
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
    name: "Range Lights",
    namespace: "rafaelborja",
    author: "Rafael Borja",
    description: "Range lights automation",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
	section("Title") {
		// TODO: put inputs here
	}
    section("Turn on when motion detected:") {
        input "themotion", "capability.refresh", required: true, title: "Where?"
    }
    section("Turn on this light") {
        input "theswitch", "capability.switch", required: true
    }
}





def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	
    def mySwitchCaps = themotion.capabilities

	// log each capability supported by the "mySwitch" device, along
	// with all its supported attributes
	mySwitchCaps.each {cap ->
   	 log.debug "Capability name: ${cap.name}"
  	  cap.attributes.each {attr ->
  	      log.debug "-- Attribute name; ${attr.name}"
 	   }
       cap.commands.each {comm ->
       log.debug "-- Command name: ${comm.name}"
      }
    }
    
    def mySwitchEvents = themotion.events()
    mySwitchCaps.each {event ->
   	 log.debug "Event name: ${event.name}"
  	  event.attributes.each {attr ->
  	      log.debug "-- Attribute name; ${attr.name}"
 	   }
	}
    
    def theCommands = themotion.supportedCommands
theCommands.each {com ->
    log.debug "Supported Command: ${com.name}"
}
   


	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(themotion, "operationStateCooktop.Ready", cooktopDetectedHandler)
    
    def switchCommands = themotion.supportedCommands
log.debug "themotion: $switchCommands"

// Iterate through the supported capabilities, log all suported commands:
// commands property available via the Capability object
def caps = themotion.capabilities
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