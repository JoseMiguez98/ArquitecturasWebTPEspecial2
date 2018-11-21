package converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;

public class ListToStringConverter implements AttributeConverter<List<String>,String> {

	@Override
	public String convertToDatabaseColumn(List<String> list) {
		return String.join(",", list);
	}

	@Override
	public ArrayList<String> convertToEntityAttribute(String varchar) {
		return new ArrayList<String>(Arrays.asList(varchar.split(",")));
	}
}
