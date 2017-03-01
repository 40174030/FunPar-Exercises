package c11
     
import org.jcsp.lang.*
import org.jcsp.groovy.*
import c05.ScaledData     
   
class Scale implements CSProcess {
	
  def int scaling
  def int multiplier
  
  def ChannelOutput outChannel
  def ChannelOutput factor
  def ChannelInput inChannel
  def ChannelInput suspend
  def ChannelInput injector
  
  void run () {
    def SECOND = 1000
    def DOUBLE_INTERVAL = 5 * SECOND
    def SUSPEND  = 0
    def INJECT   = 1
    def TIMER    = 2
    def INPUT    = 3
    
    def timer = new CSTimer()
    def scaleAlt = new ALT ( [ suspend, injector, timer, inChannel ] )
    
    def preCon = new boolean [4]
    preCon[SUSPEND] = true
    preCon[INJECT] = false
    preCon[TIMER] = true
    preCon[INPUT] = true
                                                                    
    def timeout = timer.read() + DOUBLE_INTERVAL
    timer.setAlarm ( timeout )
    
    while (true) {
      switch ( scaleAlt.priSelect(preCon) ) {
		  
        case SUSPEND :
          //  deal with suspend input
		  suspend.read()
		  factor.write(scaling)
		  println "Suspended"
		  preCon[SUSPEND] = false
		  preCon[TIMER] = false
		  preCon[INJECT] = true       
          break
        case INJECT:
          //  deal with inject input
		  scaling = injector.read()
		  println "Injected scaling is ${scaling}"
		  timeout = timer.read() + DOUBLE_INTERVAL
		  timer.setAlarm(timeout)
		  preCon[INJECT] = false
		  preCon[SUSPEND] = true
		  preCon[TIMER] = true
          break
        case TIMER:
          //  deal with Timer input
		  timeout = timer.read() + DOUBLE_INTERVAL
		  timer.setAlarm(timeout)
		  scaling = scaling * multiplier
          println "Normal Timer: new scaling is ${scaling}"
          break
        case INPUT:
          //   deal with Input channel 
		  def inValue = inChannel.read()
		  def result = new ScaledData()
		  if (preCon[SUSPEND]) {
		  	result.original = inValue
			result.scaled = inValue * scaling
		  }
		  else {
			result.original = inValue
		    result.scaled = inValue
		  }
		  outChannel.write(result)
          break
      } //end-switch
    } //end-while
  } //end-run
}