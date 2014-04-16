package	net.nosegrind.apitoolkit;

import grails.validation.Validateable
import java.util.*

@Validateable
class RoleCommand{
	
	String authority
Long id
Long version


    static constraints = {
		// import validation from Domain
		importFrom Role
		
		id blank:false
    }
}