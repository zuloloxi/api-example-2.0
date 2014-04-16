package	net.nosegrind.apitoolkit;

import grails.validation.Validateable
import java.util.*

@Validateable
class HookCommand{
	
	Long attempts
String callback
Date dateCreated
String format
Long id
Date lastModified
Set roles
String service
Long user_id
Long version

    static constraints = {
		// import validation from Domain
		importFrom Hook
		
		id blank:false
    }
}