import net.nosegrind.apitoolkit.Role
import net.nosegrind.apitoolkit.Person
import net.nosegrind.apitoolkit.PersonRole

import net.nosegrind.apitoolkit.ApiDescriptor
import net.nosegrind.apitoolkit.ParamsDescriptor
import net.nosegrind.apitoolkit.ErrorCodeDescriptor
import net.nosegrind.apitoolkit.ApiStatuses
import net.nosegrind.apitoolkit.*
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
		environments {
			production {}
			development {}
			test{}
		}

    }
    def destroy = {
    }
}
