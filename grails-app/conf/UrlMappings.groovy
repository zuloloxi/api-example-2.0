class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
		"500"(controller: 'error', action: 'status500')
		"403"(controller: 'error', action: 'status403')
	}
}
