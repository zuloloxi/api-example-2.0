class UrlMappings {

	static mappings = {

        "/"(view:"/index")
		"500"(controller: 'error', action: 'status500')
		"403"(controller: 'error', action: 'status403')
	}
}
