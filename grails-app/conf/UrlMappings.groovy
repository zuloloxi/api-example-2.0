class UrlMappings {

	static mappings = {

        "/"(view:"/index")
		
		"304"(controller: 'error', action: 'error')
		"400"(controller: 'error', action: 'error')
		"403"(controller: 'error', action: 'error')
		"404"(controller: 'error', action: 'error')
		"405"(controller: 'error', action: 'error')
		"409"(controller: 'error', action: 'error')
		"412"(controller: 'error', action: 'error')
		"413"(controller: 'error', action: 'error')
		"416"(controller: 'error', action: 'error')
		"500"(controller: 'error', action: 'error')
		"503"(controller: 'error', action: 'error')
	}
}
