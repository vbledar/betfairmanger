<g:render template="marketTypesList" model="[marketTypes: marketTypes]"/>

<div class="row">
    <div class="col-xs-6">

    </div>

    <div class="col-xs-6 text-right">
        <div class="dataTables_paginate paging_bootstrap">
            <util:remotePaginate controller="marketTypes" action="manageMarketTypesFiltered" total="${marketTypes.getTotalCount()}"
                                 update="persistedMarketTypesList"
                                 onLoading="addLoadingStateInElement('marketTypesBoxContainer')"
                                 onLoaded="removeLoadingStateFromElement('marketTypesBoxContainer')"
                                 onComplete="updateCheckboxesLayout()"
                                 max="15"/>
        </div>
    </div>
</div>