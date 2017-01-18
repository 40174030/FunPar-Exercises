package c2

import org.jcsp.lang.*
 
class Multiplier implements CSProcess {
  
  def ChannelOutput outChannel
  def ChannelInput inChannel
  def int factor;
  
  void run() {
    def i = inChannel.read()
    while (i > 0) {
      i *= factor
	  outChannel.write(i)
	  i = inChannel.read()
    }
    outChannel.write(i)
  }
}

    
