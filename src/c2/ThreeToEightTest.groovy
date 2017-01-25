package c2

import groovy.util.GroovyTestCase;
import org.jcsp.lang.*
import org.jcsp.groovy.*

class ThreeToEightTest extends GroovyTestCase {
	
	void testSet() {
		One2OneChannel T2L = Channel.createOne2One()
		One2OneChannel S2E = Channel.createOne2One()
		
		def listToStream_Test = new ListToStream ( inChannel: T2L.in(), outChannel: S2E.out() )
		def setsOfEight_Test = new CreateSetsOfEight ( inChannel: S2E.in() )
		
		def processList = [ new GenerateSetsOfThree ( outChannel: T2L.out() ),
                    listToStream_Test, setsOfEight_Test ]
		
		new PAR (processList).run()
		
	}
	
}
