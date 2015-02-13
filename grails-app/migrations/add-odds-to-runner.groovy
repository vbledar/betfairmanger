databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1423835979779-1") {
        addColumn(tableName: "runner") {
            column(name: "runner_odd", type: "double precision") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "vbledar (generated)", id: "1423835979779-2") {
        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
    }

//    changeSet(author: "vbledar (generated)", id: "1423835979779-3") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }
}
