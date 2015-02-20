<%--
  Created by IntelliJ IDEA.
  User: vbledar
  Date: 2/19/15
  Time: 14:06
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main"/>

    <title>
        <g:message code="results.management.title"/>
    </title>
</head>

<body>

<div class="row">
    <section class="col-sm-6">
        <div class="box box-solid bg-navy">
            <div class="box-header">
                <i class="fa fa-rss"></i>

                <h3 class="box-title">
                    <g:message code="results.management.synchronize.results.betfair.specific.market"/>
                </h3>
            </div>


            <div class="box-body">

                <p>
                    <g:message code="results.management.synchronize.results.betfair.specified.market"/>
                </p>
                %{--<g:link controller="resultsRss" action="retrieveResultsRss" class="btn btn-lg btn-flat bg-orange">--}%
                    %{--<i class="fa fa-rss-square"></i>--}%
                    %{--<g:message code="results.management.button.retrieve.results.betfair"/>--}%
                %{--</g:link>--}%
            </div>
        </div>
    </section>
    <section class="col-sm-6">
        <div class="box box-solid bg-navy">
            <div class="box-header">
                <i class="fa fa-rss"></i>

                <h3 class="box-title">
                    <g:message code="results.management.synchronize.results.betfair.full"/>
                </h3>
            </div>


            <div class="box-body">

                <p>
                    <g:message code="results.management.synchronize.results.betfair.all.unsettled.markets"/>
                </p>
                <g:link controller="resultsRss" action="retrieveResultsRss" class="btn btn-lg btn-flat bg-orange">
                    <i class="fa fa-rss-square"></i>
                    <g:message code="results.management.button.retrieve.results.betfair"/>
                </g:link>
            </div>
        </div>
    </section>
</div>


<script type="application/javascript">

</script>
</body>
</html>