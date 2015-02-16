package betfairmanger

class MenuTagLib {

    static namespace = "menu"

//    static defaultEncodeAs = [taglib:'html']

    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def navMenu = { attrs, body ->
        String controller = attrs.controller
        String action = attrs.action
        String name = attrs.name
        String icon = attrs.icon

        String url = createLink(controller: controller, action: action, absolute: true)

        String active = controllerName.equalsIgnoreCase(controller) && actionName.equalsIgnoreCase(action) ? 'active' : ''

        StringBuilder builder = new StringBuilder()
        builder.append("<li class='${active}'>")
        builder.append("<a href='" + url + "'>")
        builder.append("<i class='${icon}'></i>")
        builder.append("<span>${name}</span>")
        builder.append("</a>")
        builder.append("</li>")

        out << body() << builder.toString()
    }
}
