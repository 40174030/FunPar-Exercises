package c11

import org.jcsp.awt.*
import org.jcsp.groovy.*
import org.jcsp.lang.*
import java.awt.*

class ControlUI implements CSProcess {

	def ActiveCanvas canvas
	def ChannelInput suspendButtonConfig
	def ChannelInput scaleValueReset
	def ChannelOutput scaleValueConfig
	def ChannelOutput buttonEvent
	
	void run()
	{
		def root = new ActiveClosingFrame("Scaling")
		def mainFrame = root.getActiveFrame()
		mainFrame.setSize(100, 500)
		
		def suspendButton = new ActiveButton(suspendButtonConfig, buttonEvent, "Suspend")
		def scaleLabel = new Label("Scale by:")
		def scaleValue = new ActiveTextEnterField(scaleValueReset, scaleValueConfig, "")
		def injectButton = new ActiveButton(null, buttonEvent, "Inject")
		
		def container = new Container()
		container.setLayout(new GridLayout(1,5))
		
		mainFrame.setLayout(new BorderLayout())
		mainFrame.add(canvas, BorderLayout.CENTER)
		mainFrame.add(container, BorderLayout.SOUTH)
		
		mainFrame.pack()
		mainFrame.setVisible(true)
		def network = [root, canvas, scaleValue, suspendButton, injectButton]
		new PAR (network).run()
	}
	
}
