package com.guoyun.jdbc.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseQuerySQLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql="select sqoop_source_datas.id as foo_id, sqoop_2.id as bar_id from sqoop_source_datas ,sqoop_2  WHERE  (1 = 1) ) AS t1";
		Pattern pattern=null;
		Matcher matcher=null;
		
		
		pattern=Pattern.compile("(?is)(select .*?[,|\\s+]?sqoop_source_datas\\.id[\\s]+as[\\s]+([\\w]+)[,|\\s+]?.*?[\\s]+from[\\s]+)");
		matcher=pattern.matcher(sql);
		
		while(matcher.find()){
			if(matcher.start(2)>0){
				System.out.println(matcher.group(2));
			}
		}
	}

}
