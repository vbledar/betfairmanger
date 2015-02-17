databaseChangeLog = {

	changeSet(author: "vbledar (generated)", id: "1424131522438-1") {
		createTable(tableName: "betfair_session") {
			column(autoIncrement: "true", name: "id", type: "numeric(19,0)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "betfair_sessiPK")
			}

			column(name: "version", type: "numeric(19,0)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "session", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

//	changeSet(author: "vbledar (generated)", id: "1424131522438-2") {
//		modifyDataType(columnName: "country_information_id", newDataType: "numeric(19,0)", schemaName: "dbo", tableName: "country")
//	}
//
//	changeSet(author: "vbledar (generated)", id: "1424131522438-3") {
//		addNotNullConstraint(columnDataType: "numeric(19,0)", columnName: "country_information_id", schemaName: "dbo", tableName: "country")
//	}
}
