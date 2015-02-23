package com.tipster.wikipedia

import grails.transaction.Transactional
import info.bliki.api.Page
import info.bliki.api.PageInfo
import info.bliki.api.User
import info.bliki.wiki.model.WikiModel

@Transactional
class WikiPediaService {

    def grailsApplication

    def retrieveFootballClubInformation(String footballClubName) {
        def endpoint = grailsApplication.config.wikipedia.endpoint

        User user = new User("", "", endpoint)
        user.login()

        String[] pagesTitles = { "Web service" }
        List<Page> pages = user.queryContent(pagesTitles)
        for (Page page : pages) {
            log.debug "Page image: " + page.getImageUrl()
            log.debug "Page image thumbnail: " + page.getImageThumbUrl()
            log.debug "Page id: " + page.getPageid()
            log.debug "Page title: " + page.getTitle()

            for (int i=0; i<page.sizeOfCategoryList(); i++) {
                PageInfo pageInfo = page.getCategory(i)
                log.debug "Page Info ${i}: " + pageInfo.toString()
            }

            WikiModel wikiModel = new WikiModel('${image}', '${title}');
            String html = wikiModel.render(page.toString());
            System.out.println(html);
        }
    }
}
