package c2

import org.jcsp.lang.*

class ListToStream implements CSProcess{
	
	def ChannelInput inChannel
	def ChannelOutput outChannel
	
	void run (){
		def inList = inChannel.read()
		while (inList[0] != -1) {
			// Hint: output	list elements as single integers
			for (i in 0 ..< inList.size) {
				outChannel.write(inList[i])
			}
			inList = inChannel.read()
		}
		outChannel.write(-1)
	}
}