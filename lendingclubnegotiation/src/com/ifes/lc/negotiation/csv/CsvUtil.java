package com.ifes.lc.negotiation.csv;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class CsvUtil {

	public static void writeCsvFromBean(Path path, List<CsvBean> list) throws Exception {
	    Writer writer  = new FileWriter(path.toString(), false);
	 
	    StatefulBeanToCsv<CsvBean> sbc = new StatefulBeanToCsvBuilder<CsvBean>(writer)
										       .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
										       .withApplyQuotesToAll(false)
										       .build();
	    
	    list.forEach(System.out::println);
	    
	    sbc.write(list);
	    writer.close();
	}
	
}
