package c3 
 
import org.jcsp.lang.*

class Negator implements CSProcess {
  
  def ChannelInput inChannel
  def ChannelOutput outChannel
  
  void run () {
    while (true) {
      //output the negative of the input value
	  def i = inChannel.read() * -1;
	  outChannel.write(i)
    }
  }
}
