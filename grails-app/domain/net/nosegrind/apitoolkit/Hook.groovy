package net.nosegrind.apitoolkit

import java.io.Serializable
import java.util.Date
import net.nosegrind.apitoolkit.Person

class Hook implements Serializable {

	static transactional = true
	static hasMany = [roles:HookRole]
	
	Person user
	String callback
	String format = 'JSON'
	String service
	Long attempts = 0
	Date dateCreated
	Date lastModified = new Date()

	static constraints = {
		user()
		callback()
		format()
		service()
		attempts()
	}
	
	static mapping = {
		datasource 'user'
	}
}
