package com.jonastm.adapter.persistence.database

import com.jonastm.Env
import com.jonastm.orm.sqldelight.DB
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver


actual fun createDriver(config: Env.Sqlite): SqlDriver {
    return NativeSqliteDriver(DB.Schema, config.filePath)
}


