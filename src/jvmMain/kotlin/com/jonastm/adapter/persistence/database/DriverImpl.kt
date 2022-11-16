package com.jonastm.adapter.persistence.database

import com.jonastm.Env
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

actual fun createDriver(config: Env.Sqlite): SqlDriver {
    return JdbcSqliteDriver("jdbc:sqlite:/Users/jonastm/Private/ktor-multi-target-server/test.sqlite")
}
