package c9

import org.jcsp.lang.*
import org.jcsp.groovy.*

class MissedEventCheck implements CSProcess {

	def ChannelInput inChannel
	def ChannelOutput outChannel
	def int previousData = -1
	def EventData currentData
	
	void run() {
		while (true) {
			currentData = inChannel.read()
			if (previousData > -1) {
				if ((previousData + currentData.missed + 1) == currentData.data)
					println "Number of missed events for the following is correct:"
				else
					println "Number of missed events for the following is NOT correct:"
			}
			previousData = currentData.data	
			outChannel.write(currentData)
		}	
	}

}
