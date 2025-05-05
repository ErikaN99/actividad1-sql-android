import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TipDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "tips.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE tip_history (id INTEGER PRIMARY KEY AUTOINCREMENT, tip REAL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tip_history")
        onCreate(db)
    }

    fun insertTip(tip: Double) {
        writableDatabase.execSQL("INSERT INTO tip_history (tip) VALUES (?)", arrayOf(tip))
    }

    fun readAllTips(): List<Double> {
        val tips = mutableListOf<Double>()
        val cursor = readableDatabase.rawQuery("SELECT tip FROM tip_history", null)
        while (cursor.moveToNext()) {
            tips.add(cursor.getDouble(0))
        }
        cursor.close()
        return tips
    }
}
