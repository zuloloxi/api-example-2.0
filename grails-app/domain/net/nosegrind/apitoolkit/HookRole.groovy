package net.nosegrind.apitoolkit

import java.io.Serializable
import java.util.Date
import net.nosegrind.apitoolkit.Hook
import net.nosegrind.apitoolkit.Role

class HookRole implements Serializable {
	
	Hook hook
	Role role
	Date dateCreated
	Date lastModified = new Date()
	

	static mapping = {
		datasource 'user'
	}

}
