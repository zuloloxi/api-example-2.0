package	net.nosegrind.apitoolkit;

import grails.validation.Validateable
import java.util.*

@Validateable
class PersonCommand{
	
	boolean accountExpired
boolean accountLocked
Set authorities
String email
boolean enabled
Long id
String password
boolean passwordExpired
String username
Long version


    static constraints = {
		// import validation from Domain
		importFrom Person
		
		id blank:false
    }
}