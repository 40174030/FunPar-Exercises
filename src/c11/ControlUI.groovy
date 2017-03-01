package c11

import org.jcsp.awt.*
import org.jcsp.lang.*
import java.awt.*

class ControlUI implements CSProcess {

	def DisplayList thing
	def int canvasSize
	def ChannelInput x
	def ChannelInput y
	def ChannelOutput buttonEvent
	
	void run()
	{
		def root = new ActiveClosingFrame("Scaling")
		def mainFrame = root.getActiveFrame()
		def canvas = new ActiveCanvas()
		
		
	}
	
}
