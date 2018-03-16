package com.util;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * map转json
 * @author dream
 *
 */
public class MapSerializer implements JsonSerializer<Map>{

	public JsonElement serialize(Map src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject json=new JsonObject();
		Set keySet=src.keySet();
		Iterator keySetIterator=keySet.iterator();
		while(keySetIterator.hasNext())
		{
			String key=keySetIterator.next().toString();
			JsonElement element=context.serialize(src.get(key));
			json.add(key,element);
		}
	    return json;
	}
}
