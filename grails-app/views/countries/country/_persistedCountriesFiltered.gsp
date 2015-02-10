<%@ page import="com.tipster.betfair.Country" %>
<%@ page import="com.tipster.betfair.CountryInformation" %>


<g:render template="country/countriesList" model="[countries: countries, showActions: Boolean.TRUE]"/>

<util:remotePaginate controller="events" action="manageCountriesFiltered" total="${Country.count()}"
                     update="persistedCountriesList"
                     max="20"
                     pageSizes="[10: '10 Per Page', 20: '20 Per Page', 50: '50 Per Page', 100: '100 Per Page']"/>