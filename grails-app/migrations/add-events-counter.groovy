databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1424122542510-1") {
        addColumn(tableName: "competition") {
            column(name: "events_counter", type: "int")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1424122542510-2") {
        addColumn(tableName: "event") {
            column(name: "markets_counter", type: "int")
        }
    }

//    changeSet(author: "vbledar (generated)", id: "1424122542510-3") {
//        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424122542510-4") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }
}
