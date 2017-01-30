package c7

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.*
import org.jcsp.groovy.*


class Server implements CSProcess{
	  
  def ChannelInput clientRequest
  def ChannelInput otherServerRequest
  def ChannelInput thisServerReceive
  
  def ChannelOutput otherServerSend
  def ChannelOutput clientSend  
  def ChannelOutput thisServerRequest
  
  def dataMap = [ : ]    
         
  def serverNumber
  
  void run () {
    def CLIENT = 0
    def OTHER_REQUEST = 1
    def THIS_RECEIVE = 2
    def serverAlt = new ALT ([clientRequest, 
		                      otherServerRequest, 
							  thisServerReceive])
    while (true) {
      def index = serverAlt.select()
	  
      switch (index) {	
		  	  
        case CLIENT :
          def key = clientRequest.read()
		  println "Server ${serverNumber} receieved CLIENT request for ${key}"
          if ( dataMap.containsKey(key) ) {
            clientSend.write(dataMap[key])  
			println "Server ${serverNumber} sent client ${key}" 
          }       
          else {
            thisServerRequest.write(key)
          }
          //end if 
          break
        case OTHER_REQUEST :
          def key = otherServerRequest.read()
		  println "Server ${serverNumber} receieved SERVER request for ${key}"
          if ( dataMap.containsKey(key) ) {
            otherServerSend.write(dataMap[key]) 
			println "Server ${serverNumber} sent other server ${key}" 
          }
          else 
            otherServerSend.write(-1)
          //end if 
          break
        case THIS_RECEIVE :
          clientSend.write(thisServerReceive.read() )
		  println "Server ${serverNumber} sent client a value USING OTHER SERVER"
          break
      } // end switch              
    } //end while   
  } //end run
}
