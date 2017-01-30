package c2

import org.jcsp.lang.*

class CreateSetsOfEight implements CSProcess{
	
	def ChannelInput inChannel
	def int outNum = 8
	def actual = []

	void run(){
		def outList = []
		def v = inChannel.read()
		while (v != -1){
			outList = []
			for ( i in 0 .. outNum - 1 ) {
				// Put v into outList and read next input
				outList[i] = v
				v = inChannel.read()
			}
			println " Eight Object is ${outList}"
			actual = actual << outList
		}
		println "Finished: $actual"
	}
}