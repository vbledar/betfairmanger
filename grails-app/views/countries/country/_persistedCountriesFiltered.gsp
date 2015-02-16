<%@ page import="com.tipster.betfair.Country" %>
<%@ page import="com.tipster.betfair.CountryInformation" %>

<g:render template="country/countriesList" model="[countries: countries, showActions: Boolean.TRUE]"/>

<div class="row">
    <div class="col-xs-6">

    </div>

    <div class="col-xs-6 text-right">
        <div class="dataTables_paginate paging_bootstrap">
            <util:remotePaginate controller="countries" action="manageCountriesFiltered" total="${Country.count()}"
                                 update="persistedCountriesList"
                                 onLoading="addLoadingStateInElement('countriesBoxContainer')"
                                 onLoaded="removeLoadingStateFromElement('countriesBoxContainer')"
                                 max="15"/>
        </div>
    </div>
</div>