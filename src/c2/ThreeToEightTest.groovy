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
		
		def expected = [[1, 2, 3, 4, 5, 6, 7, 8], [9, 10, 11, 12, 13, 14, 15, 16], [17, 18, 19, 20, 21, 22, 23, 24]]
		def actual = setsOfEight_Test.actual
		assertTrue(expected == actual)
	}
	
}
