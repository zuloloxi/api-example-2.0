package	net.nosegrind;

import java.util.*

@grails.validation.Validateable
class TestCommand {
	
	static transients = ['name','description','receives','returns']
	
	String name = 'Show Test Data'
	String description = 'Show Test Data based upon given ID'
	List receives = [id]
	List returns = [id,testdata]
	
	Long id
    String testdata

// set script to ignore version
//Long version

    static constraints = {
		// import validation from Domain
		importFrom Test
		
		id blank:false
    }
}