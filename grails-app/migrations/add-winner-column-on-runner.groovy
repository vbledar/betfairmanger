databaseChangeLog = {

    changeSet(author: "vbledar (generated)", id: "1424383143579-1") {
        addColumn(tableName: "runner") {
            column(name: "winner", type: "bit")
        }
    }

//    changeSet(author: "vbledar (generated)", id: "1424383143579-2") {
//        modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424383143579-3") {
//        addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424383143579-4") {
//        modifyDataType(columnName: "date_created", newDataType: "datetime", schemaName: "dbo", tableName: "market")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424383143579-5") {
//        addNotNullConstraint(columnDataType: "datetime", columnName: "date_created", schemaName: "dbo", tableName: "market")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424383143579-6") {
//        modifyDataType(columnName: "last_updated", newDataType: "datetime", schemaName: "dbo", tableName: "market")
//    }

//    changeSet(author: "vbledar (generated)", id: "1424383143579-7") {
//        addNotNullConstraint(columnDataType: "datetime", columnName: "last_updated", schemaName: "dbo", tableName: "market")
//    }
}
