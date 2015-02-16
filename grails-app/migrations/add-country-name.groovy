databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1424127309119-1") {
        addColumn(tableName: "country") {
            column(name: "country_name", type: "varchar(255)")
        }
    }

//    changeSet(author: "vbledar (generated)", id: "1424127309119-2") {
//        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
//    }
//
//    changeSet(author: "vbledar (generated)", id: "1424127309119-3") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }
}
