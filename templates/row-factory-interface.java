package __package__.generated;

import com.lagovistatech.Factory;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Table;

public interface __table_name_camel_singular__RowFactory<R extends __table_name_camel_singular__Row> extends Factory<R> {
	/* UNIQUES */
	// start uniques
	public static <R extends __table_name_camel_singular__Row> R loadBy__unique_column_name_camel__(Connection conn, Table<R> table, __unique_java_type__ value) throws Exception {
		return __table_name_camel_singular__Row.loadBy__unique_column_name_camel__(conn, table, value);
	}
	// end uniques
}