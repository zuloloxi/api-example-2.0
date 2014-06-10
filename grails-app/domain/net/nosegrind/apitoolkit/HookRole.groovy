package net.nosegrind.apitoolkit

import java.io.Serializable
import java.util.Date

class HookRole implements Serializable {
	
	Hook hook
	Role role
	Date dateCreated
	Date lastModified = new Date()

	static HookRole create(Hook hook, Role role, boolean flush = true) {
		new HookRole(hook: hook, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(Hook hook, Role role, boolean flush = false) {
		HookRole instance = HookRole.findByHookAndRole(hook, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}
	

	static mapping = {
		datasource 'user'
	}

}
