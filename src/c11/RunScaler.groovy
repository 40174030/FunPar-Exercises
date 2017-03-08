package c11
 
import org.jcsp.lang.*
import org.jcsp.awt.ActiveCanvas
import org.jcsp.groovy.*
import org.jcsp.groovy.plugAndPlay.* 
import c05.Controller   
import c05.ScaledData   
  
def data = Channel.createOne2One()
def timedData = Channel.createOne2One()

def scaledData = Channel.createOne2One()
def oldScale = Channel.createOne2One()
def newScale = Channel.createOne2One()
def pause = Channel.createOne2One()
def buttonSwitch = Channel.createOne2One()

def network = [ new GNumbers ( outChannel: data.out() ),
                new GFixedDelay ( delay: 1000, 
                		          inChannel: data.in(), 
                		          outChannel: timedData.out() ),
                new Scale ( inChannel: timedData.in(),
                            outChannel: scaledData.out(),
                            factor: oldScale.out(),
                            suspend: pause.in(),
                            injector: newScale.in(),
                            scaling: 2,
							multiplier: 2,
							buttonMode: buttonSwitch.out() ),
                new ControlUI ( scaleValueReset: oldScale.in(),
								injectValueEvent: newScale.out(),
								suspendButtonEvent: pause.out(),
								displayScaledData: scaledData.in(),
								suspendButtonConfig: buttonSwitch.in() )
              ]
new PAR ( network ).run()                                                            
