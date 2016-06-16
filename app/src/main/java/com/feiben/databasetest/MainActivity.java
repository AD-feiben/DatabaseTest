package com.feiben.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabaseHelper databaseHelper;
    private Button createDatabase;
    private Button insertData;
    private Button updateData;
    private Button deleteData;
    private Button queryData;
    private SQLiteDatabase db;
    private ContentValues values = new ContentValues();
    private String Tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        createDatabase = (Button) findViewById(R.id.create_database);
        insertData = (Button) findViewById(R.id.insert_data);
        updateData = (Button) findViewById(R.id.update_data);
        deleteData = (Button) findViewById(R.id.delete_data);
        queryData = (Button) findViewById(R.id.query_data);

        createDatabase.setOnClickListener(this);
        insertData.setOnClickListener(this);
        updateData.setOnClickListener(this);
        deleteData.setOnClickListener(this);
        queryData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_database:
                db = databaseHelper.getWritableDatabase();
                break;
            case R.id.insert_data:
                db = databaseHelper.getWritableDatabase();
                //第一条数据
                values.clear();
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);
                values.clear();
                //第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values);
                break;
            case R.id.update_data:
                db = databaseHelper.getWritableDatabase();
                values.clear();
                values.put("price", 10.99);
                db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});
                break;
            case R.id.delete_data:
                db = databaseHelper.getWritableDatabase();
                db.delete("Book", "pages>?", new String[]{"500"});
                break;
            case R.id.query_data:
                db = databaseHelper.getWritableDatabase();
                /**
                 * 查询指定表，返回一个Cursor对象。
                 * @param table     要查询的表名。
                 * @param columns   要查询的列名，如果传入null将会查询所有的列
                 * @param selection 指定Where的约束条件，用于约束查询某一列或某几列的数据，传入空将会返回所有列。
                 * @param selectionArgs 将selectionArgs值逐个替换selection中的"?"占位符。
                 * @param groupBy 控制列分组，传入null就不进行分组
                 * @param having 对groupBy之后的数据进一步过滤，传入null则不进行过滤
                 * @param orderBy 对查询后的数据进行排序，传入null就会默认排序，也可能是无序的。
                 * @return 返回一个Cursor对象
                 */
//                Cursor cursor = db.query("Book", null, null, null, null, null, null);

                /**
                 * @param distinct 传入true就会去除重复的数据，传去false则反之。
                 * @param table The table name to compile the query against.
                 * @param columns A list of which columns to return. Passing null will
                 *            return all columns, which is discouraged to prevent reading
                 *            data from storage that isn't going to be used.
                 * @param selection A filter declaring which rows to return, formatted as an
                 *            SQL WHERE clause (excluding the WHERE itself). Passing null
                 *            will return all rows for the given table.
                 * @param selectionArgs You may include ?s in selection, which will be
                 *         replaced by the values from selectionArgs, in order that they
                 *         appear in the selection. The values will be bound as Strings.
                 * @param groupBy A filter declaring how to group rows, formatted as an SQL
                 *            GROUP BY clause (excluding the GROUP BY itself). Passing null
                 *            will cause the rows to not be grouped.
                 * @param having A filter declare which row groups to include in the cursor,
                 *            if row grouping is being used, formatted as an SQL HAVING
                 *            clause (excluding the HAVING itself). Passing null will cause
                 *            all row groups to be included, and is required when row
                 *            grouping is not being used.
                 * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
                 *            (excluding the ORDER BY itself). Passing null will use the
                 *            default sort order, which may be unordered.
                 * @param limit 指明返回的rows的数量。
                 * @return A {@link Cursor} object, which is positioned before the first entry. Note that
                 * {@link Cursor}s are not synchronized, see the documentation for more details.
                 * @see Cursor
                 */
                Cursor cursor = db.query(true, "Book", new String[]{"name", "author", "price"}, "price<?", new String[]{"20"}, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
//                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(Tag, "book name is " + name);
                        Log.d(Tag, "book author is " + author);
//                        Log.d(Tag, "book pages is " + pages);
                        Log.d(Tag, "book price is " + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                break;
            default:
                break;
        }
    }
}
