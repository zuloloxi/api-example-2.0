package	net.nosegrind.apitoolkit;

import grails.validation.Validateable
import java.util.*

@Validateable
class HookRoleCommand{
	
	Date dateCreated
Long hook_id
Long id
Date lastModified
Long role_id
Long version


    static constraints = {
		// import validation from Domain
		importFrom HookRole
		
		id blank:false
    }
}