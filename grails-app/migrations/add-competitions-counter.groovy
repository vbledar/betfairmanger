databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1424117991757-1") {
        addColumn(tableName: "country") {
            column(name: "competitions_counter", type: "int")
        }
    }

//    changeSet(author: "vbledar (generated)", id: "1424117991757-2") {
//        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
//    }
//
//    changeSet(author: "vbledar (generated)", id: "1424117991757-3") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }
}
