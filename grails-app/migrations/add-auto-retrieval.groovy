databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1424071207827-1") {
        addColumn(tableName: "competition") {
            column(name: "automatic_retrieval", type: "bit")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1424071207827-2") {
        addColumn(tableName: "country") {
            column(name: "automatic_retrieval", type: "bit")
        }
    }

    changeSet(author: "vbledar (generated)", id: "1424071207827-3") {
        addColumn(tableName: "market_type") {
            column(name: "automatic_retrieval", type: "bit")
        }
    }

//    changeSet(author: "vbledar (generated)", id: "1424071207827-4") {
//        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424071207827-5") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }
}
