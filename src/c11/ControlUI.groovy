package c11

import org.jcsp.awt.*
import org.jcsp.groovy.*
import org.jcsp.lang.*
import java.awt.*

class ControlUI implements CSProcess {

	def ChannelInput scaleValueReset
	def ChannelInput displayScaledData
	def ChannelOutput suspendButtonEvent
	def ChannelOutput injectValueEvent
	def ChannelInput suspendButtonConfig
	
	
	void run()
	{
		def root = new ActiveClosingFrame("Scaling")
		def mainFrame = root.getActiveFrame()
		
		def consoleOutput = new ActiveTextArea(displayScaledData, null, "")
		
		def suspendButton = new ActiveButton(suspendButtonConfig, suspendButtonEvent, "Suspend")
		
		def scaleLabel = new Label("Scale by:")
		scaleLabel.setAlignment(Label.RIGHT)
		def scaleValue = new ActiveTextEnterField(scaleValueReset, injectValueEvent, "")
		
		def container = new Container()
		container.setLayout(new GridLayout(1,3))
		container.add(suspendButton)
		container.add(scaleLabel)
		container.add(scaleValue.getActiveTextField())
		
		mainFrame.setLayout(new BorderLayout())
		mainFrame.add(consoleOutput, BorderLayout.CENTER)
		mainFrame.add(container, BorderLayout.SOUTH)
		
		mainFrame.pack()
		mainFrame.setVisible(true)
		def network = [root, consoleOutput, scaleValue, suspendButton]
		new PAR (network).run()
	}
	
}
